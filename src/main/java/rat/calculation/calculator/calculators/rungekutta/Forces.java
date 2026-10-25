package rat.calculation.calculator.calculators.rungekutta;

import java.io.Serializable;

import rat.calculation.ProjectData;
import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleMatrix3x3;
import rat.calculation.math.DoubleVector3;
import rat.calculation.math.DoubleVectorN;
import rat.calculation.planet.Planet;
import rat.calculation.planet.atmosphere.AtmosphereData;
import rat.calculation.planet.gravitation.GravitationData;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.aero.AeroData;

/**
 * Helper class for simulation.
 * 
 * @author Michael Sams
 * 
 */

@SuppressWarnings("serial")
public class Forces implements Serializable {

	private double theta, alpha, N_phi, machNumber;
	private DoubleMatrix3x3 tM_KB, tM_KO, tM_OE, tM_IE;
	private DoubleVector3 WGS84_O, WGS84S_O, r_E, r_I, rS_E, V_K_O, F_P_B,
			F_P_K, F_G_O, F_G_K, F_A_K, F_T_K;
	private AtmosphereData atmosphereData;
	private GravitationData gravitationData;
	private AeroData aeroData;

	public Forces() {
		tM_KB = new DoubleMatrix3x3();
		tM_KO = new DoubleMatrix3x3();
		tM_OE = new DoubleMatrix3x3();
		tM_IE = new DoubleMatrix3x3();
		WGS84_O = new DoubleVector3();
		WGS84S_O = new DoubleVector3();
		r_E = new DoubleVector3();
		r_I = new DoubleVector3();
		rS_E = new DoubleVector3();
		V_K_O = new DoubleVector3();
		F_P_B = new DoubleVector3();
		F_P_K = new DoubleVector3();
		F_G_O = new DoubleVector3();
		F_G_K = new DoubleVector3();
		F_A_K = new DoubleVector3();
		F_T_K = new DoubleVector3();
	}

	public void calcValues(DoubleVectorN InpVec, double tn, Rocket rocket,
			Planet planet, ProjectData inputData, double p0, RocketStage stage) {
		// theta and alpha
		theta = rocket.getThetaModel().calculateOutput(rocket.getThetaList(),
				tn, InpVec.getValue(5));
		alpha = theta - InpVec.getValue(5);

		// set WGS-84 vector in O-system
		WGS84_O.setVector(InpVec.getValue(0), InpVec.getValue(1),
				InpVec.getValue(2));
		// surface position vector --> flightpath projected on surface
		WGS84S_O.setVector(InpVec.getValue(0), InpVec.getValue(1),
				inputData.getH0_E());

		// Transformation matrices
		tM_KB.setKBMatrix(alpha);
		tM_KO.setKOMatrix(InpVec.getValue(4), InpVec.getValue(5));
		tM_OE.setOEMatrix(WGS84_O);
		tM_IE.setIEMatrix(tn, planet.getOmega());

		// ECEF-coordinates from WGS ml 193
		N_phi = planet.a
				/ Math.sqrt(1 - planet.e2
						* DoubleMath.square(Math.sin(WGS84_O.y)));
		r_E.setSCVector(N_phi, WGS84_O, planet.e2);
		// S/C position vector in I-system
		r_I.setMatrixTimesVector(tM_IE, r_E);
		// surface position vector
		rS_E.setSCVector(N_phi, WGS84S_O, planet.e2);

		// Atmosphere + GravModel ml 187
		atmosphereData = planet.atmosphereModel.calculateOutput(InpVec
				.getValue(2));
		gravitationData = planet.gravitationModel.calculateOutput(WGS84_O, r_E,
				planet, tM_OE);

		// Speed vector from WGS
		V_K_O.setMatrixTransposedTimesVector(tM_KO,
				new DoubleVector3(InpVec.getValue(3), 0.0, 0.0));

		// calculate thrust in B-System
		if (rocket.isEngineOn()) {
			// get specific impulse
			double specificImpulse = stage.getSpecificImpulseModel()
					.calculateOutput(stage, atmosphereData.pressure, p0, tn,
							planet.g0);
			// thrust
			F_P_B.setVector(
					specificImpulse * planet.g0 * stage.getFuelMassFlow(tn), 0,
					0);
		} else {
			F_P_B.setZero();
		}
		// thrust K-system
		F_P_K.setMatrixTimesVector(tM_KB, F_P_B);

		// gravitation O-system and K-system
		F_G_O.setVector(gravitationData.g_O);
		F_G_O.scaleVector(rocket.getRocketMass());
		F_G_K.setMatrixTimesVector(tM_KO, F_G_O);

		machNumber = InpVec.getValue(3) / atmosphereData.sonicSpeed;
		aeroData = rocket.getAeroModel().calculateOutput(
				stage.getAeroDataLists().get(stage.getAeroDataListIndex()),
				alpha, machNumber, rocket.isEngineOn(), stage.getAeroKFactor());
		F_A_K.setVector(-aeroData.CD, 0, -aeroData.CL);
		F_A_K.scaleVector(atmosphereData.density / 2
				* DoubleMath.square(InpVec.getValue(3))
				* DoubleMath.square(stage.getRefeDiameter()) * 0.25d * Math.PI);

		// total force in K-System
		F_T_K.setVector(F_P_K);
		F_T_K.addVector(F_G_K);
		F_T_K.addVector(F_A_K);
	}

	public DoubleVector3 getF_T_K() {
		return F_T_K;
	}

	public double getTheta() {
		return theta;
	}

	public double getAlpha() {
		return alpha;
	}

	public DoubleVector3 getF_P_B() {
		return F_P_B;
	}

	public double getMachNumber() {
		return machNumber;
	}

	public DoubleVector3 getr_E() {
		return r_E;
	}

	public DoubleVector3 getrS_E() {
		return rS_E;
	}

	public DoubleVector3 getr_I() {
		return r_I;
	}

	public AeroData getAeroData() {
		return new AeroData(aeroData.CD, aeroData.CL);
	}
}