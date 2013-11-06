package rat.calculation.calculator.calculators;

import java.util.Iterator;

import rat.calculation.ProjectData;
import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.Planet;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.theta.ThetaList;

/**
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class CalculatorTemplate extends TrajectoryCalculator {

	// ----------Calculation variables-----------//
	// Calculation time in [s].
	public double tn;
	private DoubleVector3 WGS84_O, WGS84dot_O, r_E, r_O, r_I, rS_E, rS_E_alt;
	private DoubleVector3 V_K_K, V_K_O, F_P_B, F_P_K, F_G_O, F_G_K, F_A_K,
			F_T_K, A_K_K;
	private double alpha, p0, gamma, theta, chi, groundDistance, machNumber;

	// control variables
	private long iterCount = 0;
	private double nextOutputTime = 0;

	public CalculatorTemplate(Rocket rocket, Planet planet, ProjectData data) {
		// Super constructor
		super(rocket, planet, data, "Calculator Template");
		tn = inputData.getT0();

		// Initialize calculation variables
		WGS84_O = new DoubleVector3();
		WGS84dot_O = new DoubleVector3();
		V_K_K = new DoubleVector3();
		V_K_O = new DoubleVector3();
		F_P_B = new DoubleVector3();
		F_P_K = new DoubleVector3();
		F_G_K = new DoubleVector3();
		F_G_O = new DoubleVector3();
		F_A_K = new DoubleVector3();
		F_T_K = new DoubleVector3();
		A_K_K = new DoubleVector3();
		r_E = new DoubleVector3();
		r_O = new DoubleVector3();
		r_I = new DoubleVector3();
		rS_E = new DoubleVector3();
		rS_E_alt = new DoubleVector3();

		// init objects here
	}

	@Override
	public void run() {
		this.finished = false;
		long start = System.currentTimeMillis(); // for run-time
		// create new result list
		calculationResult = new CalculationResult();

		// non global iteration variables
		RocketStage stage;
		ThetaList thetaList = rocket.getThetaList();
		rocket.initStageSeparationParameters();

		// init specific impulse models
		Iterator<RocketStage> stageIter = rocket.getStages().iterator();
		while (stageIter.hasNext()) {
			RocketStage specInitStage = stageIter.next();
			specInitStage.getSpecificImpulseModel().init(specInitStage, p0,
					planet.g0);
		}

		// Initialize other variables
		// HERE

		// calculation loop
		while (tn < inputData.getCancelTime() && WGS84_O.z >= 0.0) {
			stage = rocket.updateRocketStages(tn, inputData.getDelta_t());
			// Do calculation
			// HERE

			// Generate output
			if (tn >= nextOutputTime) {
				calculationResult.addCalculationStep(new CalculationStepResult(
						tn, rocket.getRocketMass(), theta, alpha, gamma, V_K_K,
						r_E, r_I, rS_E, groundDistance, WGS84_O, F_P_B.x,
						machNumber));
				nextOutputTime += inputData.getOutIterationTimeStep(tn);
			}

			// increase time and iterCount
			tn += inputData.getDelta_t();
			iterCount++;
		}

		// add last iteration results
		calculationResult.addCalculationStep(new CalculationStepResult(tn,
				rocket.getRocketMass(), theta, alpha, gamma, V_K_K, r_E, r_I,
				rS_E, groundDistance, WGS84_O, F_P_B.x, machNumber));
		// System.out.println("BUMMMMM!"); // What sound makes a rocket?
		long end = System.currentTimeMillis();
		System.out.println(super.name + " calculation time: " + (end - start)
				+ " ms");
		this.finished = true;
	}
}
