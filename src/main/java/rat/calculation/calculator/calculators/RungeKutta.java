package rat.calculation.calculator.calculators;

import java.util.Iterator;

import rat.calculation.ProjectData;
import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.calculator.calculators.rungekutta.DotVec;
import rat.calculation.calculator.calculators.rungekutta.Forces;
import rat.calculation.math.DoubleVector3;
import rat.calculation.math.DoubleVectorN;
import rat.calculation.planet.Planet;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.theta.ThetaList;

/**
 * Model for Classical Runge-Kutta 4-order ODE-Solver.
 * 
 * @author Michael Sams, Gerhard Mesch
 */

@SuppressWarnings("serial")
public class RungeKutta extends TrajectoryCalculator {

	// ----------Calculation variables-----------//
	// Calculation time in [s].
	public double tn;
	private DoubleVector3 WGS84_O, r_E, r_I, rS_E;
	private DoubleVector3 V_K_K;
	private double p0, gamma0, theta0, tn2, tn4, timeGamma, startChi,
			fluelMassFlow;
	private Forces force, force2, force3, force4;
	private DoubleVectorN InpVec, inpVec1, inpVec2, inpVec3, SysVec1, SysVec2,
			SysVec3;
	private DotVec dotVec1, dotVec2, dotVec3, dotVec4;

	// control variables
	private long iterCount = 0;
	private double nextOutputTime = 0;

	public RungeKutta(Rocket rocket, Planet planet, ProjectData data) {
		// Super constructor
		super(rocket, planet, data, "Runge-Kutta 4th-order");
		tn = inputData.getT0();

		// Initialize calculation variables
		WGS84_O = new DoubleVector3();
		V_K_K = new DoubleVector3();
		r_E = new DoubleVector3();
		r_I = new DoubleVector3();
		rS_E = new DoubleVector3();
		InpVec = new DoubleVectorN(7);
		inpVec1 = new DoubleVectorN(7);
		inpVec2 = new DoubleVectorN(7);
		inpVec3 = new DoubleVectorN(7);
		force = new Forces();
		force2 = new Forces();
		force3 = new Forces();
		force4 = new Forces();
		dotVec1 = new DotVec();
		dotVec2 = new DotVec();
		dotVec3 = new DotVec();
		dotVec4 = new DotVec();
		SysVec1 = new DoubleVectorN(7);
		SysVec2 = new DoubleVectorN(7);
		SysVec3 = new DoubleVectorN(7);

		fluelMassFlow = 0.0;
	}

	@Override
	public void run() {
		this.finished = false;
		long start = System.currentTimeMillis(); // for run-time

		boolean notBreakingExit = true;

		// create new result list
		calculationResult = new CalculationResult();

		// non global iteration variables
		RocketStage stage;
		ThetaList thetaList = rocket.getThetaList();
		rocket.initStageSeparationParameters();

		// -- Set initial values
		tn = inputData.getT0();
		nextOutputTime = 0 + inputData.getOutIterationTimeStep(tn);
		// initial angles
		gamma0 = rocket.getThetaModel().calculateOutput(thetaList,
				inputData.getT0(), 90.0d);
		theta0 = rocket.getThetaModel().calculateOutput(thetaList, tn, gamma0);

		// initial velocity-vector
		V_K_K.setVector(inputData.getV0_K(), 0.0, 0.0);
		// initialize position vectors
		rS_E.setZero();
		r_E.setZero();
		r_I.setZero();
		WGS84_O.setVector(inputData.getLambda0(), inputData.getPhi0(),
				inputData.getH0_E() + inputData.getH_O());
		iterCount = 0;
		/* --> next two values are necessary to avoid numerical roundoff errors
		 * during first view seconds of calculation for gamma & chi angles! */
		// start time for gamma-angle calculation */
		timeGamma = rocket.getThetaModel().getFirstThetaNon90DegTime(thetaList);
		// start angle for chi-angle calculation
		startChi = Math.toRadians(85.0);

		// set InpVec
		double array[] = { WGS84_O.x, WGS84_O.y, WGS84_O.z,
				inputData.getV0_K(), inputData.getChi0(), gamma0, 0.0 };
		InpVec.setValues(array);

		// other constants
		p0 = planet.atmosphereModel.calculateOutput(0.0).pressure;

		// write initial (zero) step
		// calculate initial values for forces and angles
		stage = rocket.updateRocketStages(tn, inputData.getDelta_t());
		force.calcValues(InpVec, tn, rocket, planet, inputData, p0, stage);
		// calculationResult.addCalculationStep(new CalculationStepResult(tn,
		// rocket.getRocketMass(), theta0, inputData.getAlpha0(), gamma0,
		// V_K_K, r_E, r_I, rS_E, 0.0, WGS84_O, 0.0, 0.0));
		calculationResult.addCalculationStep(new CalculationStepResult(tn,
				rocket.getRocketMass(), theta0, inputData.getAlpha0(), InpVec
						.getValue(5), new DoubleVector3(InpVec.getValue(3), 0,
						0), force.getr_E(), force.getr_I(), force.getrS_E(),
				InpVec.getValue(6), new DoubleVector3(InpVec.getValue(0),
						InpVec.getValue(1), InpVec.getValue(2)), force
						.getF_P_B().x, force.getMachNumber(), fluelMassFlow));

		// init specific impulse models
		Iterator<RocketStage> stageIter = rocket.getStages().iterator();
		while (stageIter.hasNext()) {
			RocketStage specInitStage = stageIter.next();
			specInitStage.getSpecificImpulseModel().init(specInitStage, p0,
					planet.g0);
		}

		// calculation loop
		while (tn < inputData.getCancelTime() && InpVec.getValue(2) >= 0.0) {

			// -- STEP 1
			// Predictor: Forward-Euler
			//
			// update rocket stage
			stage = rocket.updateRocketStages(tn, inputData.getDelta_t());
			// calculate forces and angles
			force.calcValues(InpVec, tn, rocket, planet, inputData, p0, stage);
			// calculate derivatives of ODE-System
			dotVec1.CalcDots(InpVec, force.getF_T_K(), tn, planet,
					rocket.getRocketMass(), timeGamma, startChi);
			// save original SysVec1
			SysVec1.setVector(dotVec1.getSysVec());
			// modify SysVec in dotVec
			dotVec1.getSysVec().ScalarMult(inputData.getDelta_t() * 0.5d);
			inpVec1.setVector(InpVec);
			inpVec1.VecAdd(dotVec1.getSysVec());
			if (inpVec1.getValue(2) < 0.0)
				break;

			// -- STEP 2
			// Corrector: Backward-Euler
			//
			// new time: ( tn + 1/2 )
			tn2 = tn + inputData.getDelta_t() * 0.5d;
			// update rocket stage
			stage = rocket
.updateRocketStages(tn + inputData.getDelta_t(),
					inputData.getDelta_t() * -0.5d);
			// calculate forces and angles
			force2.calcValues(inpVec1, tn2, rocket, planet, inputData, p0,
					stage);
			// calculate derivatives of ODE-System
			dotVec2.CalcDots(inpVec1, force2.getF_T_K(), tn2, planet,
					rocket.getRocketMass(), timeGamma, startChi);
			// save original SysVec2
			SysVec2.setVector(dotVec2.getSysVec());
			// modify SysVec2 in dotVec2
			dotVec2.getSysVec().ScalarMult(inputData.getDelta_t() * 0.5d);
			inpVec2.setVector(InpVec);
			inpVec2.VecAdd(dotVec2.getSysVec());
			if (inpVec2.getValue(2) < 0.0)
				break;

			// -- STEP 3
			// Predictor: Midpoint-Rule
			//
			// calculate forces and angles
			force3.calcValues(inpVec2, tn2, rocket, planet, inputData, p0,
					stage);
			// calculate derivatives of ODE-System
			dotVec3.CalcDots(inpVec2, force3.getF_T_K(), tn2, planet,
					rocket.getRocketMass(), timeGamma, startChi);
			// save original SysVec3
			SysVec3.setVector(dotVec3.getSysVec());
			// modify SysVec3 in dotVec3
			dotVec3.getSysVec().ScalarMult(inputData.getDelta_t());
			inpVec3.setVector(inpVec2);
			inpVec3.VecAdd(dotVec3.getSysVec());
			if (inpVec3.getValue(2) < 0.0)
				break;

			// -- STEP 4
			// Final Corrector: Simpson's-Rule
			//
			// new time: ( tn + 1 )
			tn4 = tn + inputData.getDelta_t();
			// update rocket stage
			stage = rocket
					.updateRocketStages(tn2, inputData.getDelta_t() * 0.5);
			// calculate forces and angles
			force4.calcValues(inpVec3, tn4, rocket, planet, inputData, p0,
					stage);
			// calculate derivatives of ODE-System
			dotVec4.CalcDots(inpVec3, force4.getF_T_K(), tn4, planet,
					rocket.getRocketMass(), timeGamma, startChi);
			// FINAL: calculate correct Input-Vector
			SysVec2.ScalarMult(2.0d);
			SysVec3.ScalarMult(2.0d);

			SysVec1.VecAdd(SysVec2);
			SysVec1.VecAdd(SysVec3);
			SysVec1.VecAdd(dotVec4.getSysVec());
			SysVec1.ScalarMult(inputData.getDelta_t() / 6.0d);

			InpVec.VecAdd(SysVec1); // --> solution for next time step

			fluelMassFlow = rocket.getCurrentFuelMassFlow();
			// Generate output
			if (tn >= nextOutputTime) {
				calculationResult.addCalculationStep(new CalculationStepResult(
						tn, rocket.getRocketMass(), force.getTheta(), force
								.getAlpha(), InpVec.getValue(5),
						new DoubleVector3(InpVec.getValue(3), 0, 0), force
								.getr_E(), force.getr_I(), force.getrS_E(),
						InpVec.getValue(6), new DoubleVector3(InpVec
								.getValue(0), InpVec.getValue(1), InpVec
								.getValue(2)), force.getF_P_B().x, force
								.getMachNumber(), fluelMassFlow));

				nextOutputTime += inputData.getOutIterationTimeStep(tn);
			}

			// increase time and iterCount
			tn += inputData.getDelta_t();
			iterCount++;
		}

		if (notBreakingExit) {
			// add last iteration results
			calculationResult.addCalculationStep(new CalculationStepResult(tn,
					rocket.getRocketMass(), force.getTheta(), force.getAlpha(),
					InpVec.getValue(5), new DoubleVector3(InpVec.getValue(3),
							0, 0), force.getr_E(), force.getr_I(), force
							.getrS_E(), InpVec.getValue(6), new DoubleVector3(
							InpVec.getValue(0), InpVec.getValue(1), InpVec
									.getValue(2)), force.getF_P_B().x, force
							.getMachNumber(), fluelMassFlow));
		}

		// System.out.println("BUMMMMM!"); // What sound makes a rocket?
		// for run-time
		long end = System.currentTimeMillis();
		System.out.println(super.name + " calculation time: " + (end - start)
				+ " ms");
		this.finished = true;
	}

}
