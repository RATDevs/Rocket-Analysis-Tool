package rat.calculation.calculator.calculators;

import java.util.Iterator;

import rat.calculation.ProjectData;
import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleMatrix3x3;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.Planet;
import rat.calculation.planet.atmosphere.AtmosphereData;
import rat.calculation.planet.gravitation.GravitationData;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.aero.AeroData;
import rat.calculation.rocket.theta.ThetaList;

@SuppressWarnings("serial")
public class ForwardEuler extends TrajectoryCalculator {

	// Calculation time in [s].
	public double tn;

	private DoubleVector3 tempVector1, tempVector2, tempVector3;
	private DoubleVector3 WGS84_O, WGS84dot_O, r_E, r_O, r_I, rS_E, rS_E_alt;
	private DoubleVector3 V_K_K, V_K_O, F_P_B, F_P_K, F_G_O, F_G_K, F_A_K,
			F_T_K, A_K_K;
	private DoubleVector3 omegaIE_E, omegaIE_O, omegaEO_O, omegaEO_K;

	private double alpha, p0, gamma, theta, chi, groundDistance, machNumber,
			timeGamma, startChi, fluelMassFlow;

	// control variables
	private long iterCount = 0;
	private double nextOutputTime = 0;

	/** Transformation matrix B -> K */
	private DoubleMatrix3x3 tM_KB;
	/** Transformation matrix K -> O */
	private DoubleMatrix3x3 tM_KO;
	/** Transformation matrix E -> O */
	private DoubleMatrix3x3 tM_OE;
	/** Transformation matrix E -> I */
	private DoubleMatrix3x3 tM_IE;

	public ForwardEuler(Rocket rocket, Planet planet, ProjectData data) {
		// Trajectory constructor
		super(rocket, planet, data, "Forward-Euler calculator");

		// init transformation matrices
		tM_KB = new DoubleMatrix3x3();
		tM_KO = new DoubleMatrix3x3();
		tM_OE = new DoubleMatrix3x3();
		tM_IE = new DoubleMatrix3x3();

		// init vector variables for calculation
		tempVector1 = new DoubleVector3();
		tempVector2 = new DoubleVector3();
		tempVector3 = new DoubleVector3();
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
		omegaIE_E = new DoubleVector3();
		omegaIE_O = new DoubleVector3();
		omegaEO_O = new DoubleVector3();
		omegaEO_K = new DoubleVector3();
		r_E = new DoubleVector3();
		r_O = new DoubleVector3();
		r_I = new DoubleVector3();
		rS_E = new DoubleVector3();
		rS_E_alt = new DoubleVector3();
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

		// ***********************************************
		// Set initial values ml 106
		// ***********************************************
		tn = inputData.getT0();
		nextOutputTime = 0 + inputData.getOutIterationTimeStep(tn);
		/* --> next two values are necessary to avoid numerical roundoff errors
		 * during first view seconds of calculation for gamma & chi angles! */
		// start time for gamma-angle calculation
		timeGamma = rocket.getThetaModel().getFirstThetaNon90DegTime(thetaList);
		// start angle for chi-angle calculation
		startChi = Math.toRadians(85.0);

		alpha = inputData.getAlpha0();
		chi = inputData.getChi0();

		gamma = rocket.getThetaModel().calculateOutput(thetaList,
				inputData.getT0(), 90.0d);
		theta = rocket.getThetaModel().calculateOutput(thetaList, tn, gamma);

		V_K_K.setVector(inputData.getV0_K(), 0, 0);
		A_K_K.setZero();

		rS_E.setZero();
		r_E.setZero();
		r_I.setZero();

		WGS84_O.setVector(inputData.getLambda0(), inputData.getPhi0(),
				inputData.getH0_E() + inputData.getH_O());
		WGS84dot_O.setZero();

		F_P_B.setZero();
		groundDistance = 0;
		iterCount = 0;
		machNumber = 0;

		// other constants
		p0 = planet.atmosphereModel.calculateOutput(0).pressure;

		// init specific impulse models
		Iterator<RocketStage> stageIter = rocket.getStages().iterator();
		while (stageIter.hasNext()) {
			RocketStage specInitStage = stageIter.next();
			specInitStage.getSpecificImpulseModel().init(specInitStage, p0,
					planet.g0);
		}

		fluelMassFlow = 0.0;
		// write initial (zero) step
		calculationResult.addCalculationStep(new CalculationStepResult(tn,
				rocket.getRocketMass(), theta, alpha, gamma, V_K_K, r_E, r_I,
				rS_E, groundDistance, WGS84_O, F_P_B.x, machNumber,
				fluelMassFlow));
		// abort conditions: hit ground or flight time to long
		while (tn < inputData.getCancelTime() && WGS84_O.z >= 0.0) {
			// theta and alpha
			theta = rocket.getThetaModel()
					.calculateOutput(thetaList, tn, gamma);
			alpha = theta - gamma;

			// ***********************************************
			// Transformation matrices matlab-line 167
			// ***********************************************
			tM_KB.setKBMatrix(alpha);
			tM_KO.setKOMatrix(chi, gamma);
			tM_OE.setOEMatrix(WGS84_O);
			tM_IE.setIEMatrix(tn, planet.getOmega());

			// ***********************************************
			// ECEF-coordinates from WGS ml 193
			// ***********************************************
			double sinWGSy = DoubleMath.square(Math.sin(WGS84_O.y));
			double N_phi = planet.a / Math.sqrt(1 - planet.e2 * sinWGSy);
			r_E.setSCVector(N_phi, WGS84_O, planet.e2);
			rS_E_alt.setVector(rS_E);
			rS_E.setSurfaceVector(N_phi, WGS84_O, inputData.getH0_E(),
					planet.e2);
			tempVector1.setSubtract(rS_E_alt, rS_E);
			// calculate flown ground distance
			if (iterCount > 0) {
				groundDistance += tempVector1.length();
			}
			// ***********************************************
			// Atmosphere + GravModel ml 187
			// ***********************************************
			AtmosphereData atmosphereData = planet.atmosphereModel
					.calculateOutput(WGS84_O.z);
			GravitationData gravitationData = planet.gravitationModel
					.calculateOutput(WGS84_O, r_E, planet, tM_OE);
			// System.out.println(WGS84_O.z + ", " + gravitationData.g0);
			// System.out.println(gravitationData.g0);
			// ***********************************************
			// Speed vector from WGS ml 214
			// ***********************************************
			V_K_O.setMatrixTransposedTimesVector(tM_KO, V_K_K);

			// ***********************************************
			// PosDGL - WGS84 ml 220
			// ***********************************************
			double M_phi = N_phi * (1 - planet.e2) / (1 - planet.e2 * sinWGSy);
			// O-System
			WGS84dot_O.setWGS84Dot_O(WGS84_O, V_K_O, N_phi, M_phi);
			// do forward euler
			// WGS84_O = WGS84_O + delta_t * WGS84dot_O;
			tempVector1.setVector(WGS84dot_O);
			tempVector1.scaleVector(inputData.getDelta_t());
			WGS84_O.addVector(tempVector1);

			// ***********************************************
			// Update forces ml 231
			// ***********************************************
			stage = rocket.updateRocketStages(tn, inputData.getDelta_t());
			fluelMassFlow = rocket.getCurrentFuelMassFlow();

			if (rocket.isEngineOn()) {
				// get specific impulse
				double specificImpulse = stage.getSpecificImpulseModel()
						.calculateOutput(stage, atmosphereData.pressure, p0,
								tn, planet.g0);
				// thrust
				F_P_B.setVector(
						specificImpulse * planet.g0 * stage.getFuelMassFlow(tn),
						0, 0);

			} else {
				F_P_B.setZero();
			}
			// thrust K-system
			F_P_K.setMatrixTimesVector(tM_KB, F_P_B);

			// gravitation O-system and K-system
			F_G_O.setVector(gravitationData.g_O);
			F_G_O.scaleVector(rocket.getRocketMass());
			F_G_K.setMatrixTimesVector(tM_KO, F_G_O);

			machNumber = V_K_K.x / atmosphereData.sonicSpeed;
			AeroData aeroData = rocket.getAeroModel().calculateOutput(
					stage.getAeroDataLists().get(stage.getAeroDataListIndex()),
					alpha, machNumber, rocket.isEngineOn(),
					stage.getAeroKFactor());

			// F_A_K = dens/2*V_K_K(1,1)^2*(inp(stage).dref^2*pi/4)*[-C_D; 0;
			// -C_L];
			F_A_K.setVector(-aeroData.CD, 0, -aeroData.CL);
			F_A_K.scaleVector(atmosphereData.density / 2
					* DoubleMath.square(V_K_K.x)
					* DoubleMath.square(stage.getRefeDiameter()) * 0.25d
					* Math.PI);

			// total force in K-System
			F_T_K.setVector(F_P_K);
			F_T_K.addVector(F_G_K);
			F_T_K.addVector(F_A_K);

			// ***********************************************
			// Rotation vectors between CSYS ml 291
			// ***********************************************
			omegaIE_E.setVector(0, 0, planet.getOmega());
			omegaIE_O.setMatrixTimesVector(tM_OE, omegaIE_E);
			omegaEO_O.setOmegaEO_O(WGS84_O, WGS84dot_O);
			omegaEO_K.setMatrixTimesVector(tM_KO, omegaEO_O);

			// ***********************************************
			// rocket gravity center point vector ml 303
			// ***********************************************
			r_O.setMatrixTimesVector(tM_OE, r_E);
			r_I.setMatrixTimesVector(tM_IE, r_E);

			// ***********************************************
			// movement PDE of the rocket ml 311
			// ***********************************************
			// acceleration k-system
			// A_K_K = 1/m*F_T_K - cross(omegaEO_K,V_K_K) --> 12.3sec!!
			// - M_KO*(2*cross(omegaIE_O,V_K_O) +
			// cross(omegaIE_O,cross(omegaIE_O,r_O)));
			A_K_K.setVector(F_T_K);
			A_K_K.scaleVector(1.0d / rocket.getRocketMass());
			tempVector1.setCrossProduct(omegaEO_K, V_K_K);
			A_K_K.subtractVector(tempVector1);
			tempVector1.setCrossProduct(omegaIE_O, V_K_O);
			tempVector1.scaleVector(2.0d);
			tempVector2.setCrossProduct(omegaIE_O, r_O);
			tempVector3.setCrossProduct(omegaIE_O, tempVector2);
			tempVector1.addVector(tempVector3);
			tempVector2.setMatrixTimesVector(tM_KO, tempVector1);
			A_K_K.subtractVector(tempVector2);

			// speed k-System
			// V_K_K = [V_K_K(1,1) + deltat * Vdot_K_K(1); 0; 0];
			V_K_K.setVector(V_K_K.x + inputData.getDelta_t() * A_K_K.x, 0, 0);

			// rocket angle gamma
			if (tn > timeGamma) {
				gamma += inputData.getDelta_t() * (-A_K_K.z / V_K_K.x);
			}
			// direction angle chi
			if (gamma < startChi) {
				chi += inputData.getDelta_t()
						* (A_K_K.y / (V_K_K.x * Math.cos(gamma)));
			}

			// Generate output
			if (tn >= nextOutputTime) {
				calculationResult.addCalculationStep(new CalculationStepResult(
						tn, rocket.getRocketMass(), theta, alpha, gamma, V_K_K,
						r_E, r_I, rS_E, groundDistance, WGS84_O, F_P_B.x,
						machNumber, fluelMassFlow));
				nextOutputTime += inputData.getOutIterationTimeStep(tn);
			}

			// increase time and iterCount
			tn += inputData.getDelta_t();
			iterCount++;
		}

		// add last iteration results
		calculationResult.addCalculationStep(new CalculationStepResult(tn,
				rocket.getRocketMass(), theta, alpha, gamma, V_K_K, r_E, r_I,
				rS_E, groundDistance, WGS84_O, F_P_B.x, machNumber,
				fluelMassFlow));

		// System.out.println("BUMMMMM!"); // What sound makes a rocket?
		long end = System.currentTimeMillis();
		System.out.println(super.name + " calculation time: " + (end - start)
				+ " ms");
		this.finished = true;
	}
}