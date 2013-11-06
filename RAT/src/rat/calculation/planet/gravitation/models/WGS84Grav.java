package rat.calculation.planet.gravitation.models;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleMatrix3x3;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.Planet;
import rat.calculation.planet.gravitation.GravitationData;
import rat.calculation.planet.gravitation.GravitationModel;

/**
 * Implementation of the WGS84-Gravity model on earth.
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class WGS84Grav extends GravitationModel {

	private double k = 0.00193185265241d;
	private double g_e = 9.7803253359d;

	public WGS84Grav() {
		super("WGS84 gravitation model");
	}

	@Override
	public GravitationData calculateOutput(DoubleVector3 WGS84,
			DoubleVector3 r_E, Planet planet, DoubleMatrix3x3 tM_OE) {
		// initialize variables
		double E2, u2, beta, w, q, q0, q1, g_u, g_beta, tempvar, gSurf;
		DoubleMatrix3x3 tM_R1 = new DoubleMatrix3x3();
		DoubleVector3 g_Ell = new DoubleVector3();
		DoubleVector3 g_E = new DoubleVector3();
		DoubleVector3 g_O = new DoubleVector3();

		// square of linear eccentricity
		E2 = DoubleMath.square(planet.E);

		// square of ellipsoidal coordinate u
		tempvar = DoubleMath.square(r_E.x) + DoubleMath.square(r_E.y)
				+ DoubleMath.square(r_E.z) - E2;
		u2 = 0.5
				* tempvar
				* (1.0 + Math.sqrt(1 + 4.0 * E2 * DoubleMath.square(r_E.z)
						/ DoubleMath.square(tempvar)));

		// ellipsoidal coordinate beta
		beta = Math.atan((r_E.z * Math.sqrt(E2 + u2))
				/ (Math.sqrt(u2) * Math.sqrt(DoubleMath.square(r_E.x)
						+ DoubleMath.square(r_E.y))));

		// variable w
		w = Math.sqrt((u2 + E2 * DoubleMath.square(Math.sin(beta))) / (u2 + E2));

		// introduces abbreviations: q, q0, q1
		q = 0.5 * ((1.0 + 3.0 * u2 / E2) * Math.atan(planet.E / Math.sqrt(u2)) - 3.0
				* Math.sqrt(u2) / planet.E);
		q0 = 0.5 * ((1.0 + 3.0 * DoubleMath.square(planet.b) / E2)
				* Math.atan(planet.E / planet.b) - 3.0 * planet.b / planet.E);
		q1 = 3.0
				* (1.0 + u2 / E2)
				* (1.0 - Math.sqrt(u2) / planet.E
						* Math.atan(planet.E / Math.sqrt(u2))) - 1.0;

		// gravitational constants in ellipsoidal coordinates
		g_u = -1.0
				/ w
				* (planet.GM / (u2 + E2) + DoubleMath.square(planet.getOmega())
						* DoubleMath.square(planet.a) * planet.E / (u2 + E2)
						* q1 / q0
						* (0.5 * DoubleMath.square(Math.sin(beta)) - 1.0 / 6.0))
				+ DoubleMath.square(planet.getOmega()) * Math.sqrt(u2)
				* DoubleMath.square(Math.cos(beta)) / w;
		g_beta = 1.0 / w * DoubleMath.square(planet.getOmega())
				* DoubleMath.square(planet.a) / Math.sqrt(u2 + E2) * q / q0
				* Math.sin(beta) * Math.cos(beta) - 1.0 / w
				* DoubleMath.square(planet.getOmega()) * Math.sqrt(u2 + E2)
				* Math.sin(beta) * Math.cos(beta);

		// Transformation matrix
		tM_R1.setR1Matrix(u2, beta, w, E2, WGS84.x);

		// gravitational vector in ellipsoid-system (u, beta, lambda)
		g_Ell.setVector(g_u, g_beta, 0.0);
		// transformation into ECEF-system: E
		g_E.setMatrixTimesVector(tM_R1, g_Ell);
		// transformation into NED-system: O
		g_O.setMatrixTimesVector(tM_OE, g_E);

		// gravitational acceleration on ellipsoid (surface)
		tempvar = DoubleMath.square(Math.sin(WGS84.y));
		gSurf = g_e * (1 + k * tempvar) / Math.sqrt(1.0 - planet.e2 * tempvar);

		return new GravitationData(gSurf, g_O);
	}
}
