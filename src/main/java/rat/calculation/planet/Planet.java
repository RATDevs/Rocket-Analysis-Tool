package rat.calculation.planet;

import java.io.Serializable;
import java.util.Observable;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.atmosphere.AtmosphereModel;
import rat.calculation.planet.atmosphere.models.CIRAMean;
import rat.calculation.planet.gravitation.GravitationModel;
import rat.calculation.planet.gravitation.models.Newton;

/**
 * Planets for flight path calculations. Contains the transformation of the
 * different coordinate systems, atmosphere and gravitation models.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class Planet extends Observable implements Serializable {

	public final String name;

	public AtmosphereModel atmosphereModel;
	public GravitationModel gravitationModel;

	private boolean rotation = true;

	// WGS - World Geodetic System
	// Angular speed of earth's rotation [rad/s]
	private final double omega;
	// semi-major axis
	public final double a;
	// semi-minor axis
	public final double b;
	// earth flattening
	public final double f;
	// first eccentricity
	public final double e;
	// Geocentric gravitational Constant
	public final double GM;
	// linear eccentricity
	public final double E;
	// standard grav. acceleration
	public final double g0;

	// Transformation auto-variables
	public final double e2;
	private double aSq, bSq, E2, eSq;

	/** Default constructor which sets the variables for the earth. */
	public Planet() {
		name = "Earth";
		a = 6378137.0d;
		b = 6356752.3142d;
		f = (a - b) / a;
		e = Math.sqrt(f * (2 - f));
		GM = 3986004.418E8;
		E = 5.2185400842339E5;
		omega = 7.292115E-5;
		g0 = 9.80665; // SI-standards
		e2 = e * e;
		atmosphereModel = new CIRAMean();
		// atmosphereModel = new CIRALow();
		// atmosphereModel = new CIRAHigh();
		// atmosphereModel = new BMTC();
		gravitationModel = new Newton();
		// gravitationModel = new WGS84Grav();
		calculateECEFConstants();
	}

	/**
	 * Custom constructor for variable planets
	 * 
	 * @param a
	 *            semi-major axis
	 * @param b
	 *            semi-minor axis
	 * @param name
	 *            Name of the planet
	 */
	public Planet(double a, double b, String name, double omega, double GM,
			double g0) {
		this.a = a;
		this.b = b;
		f = (a - b) / a;
		e = Math.sqrt(f * (2 - f));
		this.GM = GM;
		this.E = Math.sqrt(DoubleMath.square(a) - DoubleMath.square(b));
		e2 = e * e;
		this.name = name;
		atmosphereModel = new CIRAMean();
		gravitationModel = new Newton();
		calculateECEFConstants();
		this.omega = omega;
		this.g0 = g0;
	}

	private void calculateECEFConstants() {
		aSq = DoubleMath.square(a);
		bSq = DoubleMath.square(b);
		E2 = aSq - bSq;
		eSq = DoubleMath.square(e);
	}

	/**
	 * ECEF (Earth-Centered, Earth-Fixed) to WGS (World Geodetic
	 * System)function.
	 * 
	 * Working with an input reference to prevent garbage collector overhead.
	 */
	public void ECEF_to_WGS(DoubleVector3 ECEF, DoubleVector3 WGS) {
		// WGS(lambda, phi, height);
		double x = ECEF.x;
		double y = ECEF.y;
		double zSq = ECEF.z;
		// lambda
		double r = Math.sqrt(DoubleMath.square(x) + DoubleMath.square(y));
		double rSq = r * r;
		WGS.x = Math.atan2(y, x);
		// phi
		double F = 54 * bSq * zSq;
		double G = rSq + (1 - eSq) * zSq - eSq * E2;
		double c = (DoubleMath.square(eSq) * F * rSq) / Math.pow(G, 3);
		double s = Math.pow((1 + c + Math.sqrt(DoubleMath.square(c) + 2 * c)),
				(1.0d / 3.0d));
		double P = F
				/ (3 * DoubleMath.square((s + 1 / s + 1)) * DoubleMath
						.square(G));
		double Q = Math.sqrt(1 + 2 * DoubleMath.square(eSq) * P);
		double r0 = Math.sqrt(aSq / 2 * (1 + 1 / Q) - (P * (1 - eSq) * zSq)
				/ (Q * (1 + Q)) - P * rSq / 2)
				- P * eSq * r / (1 + Q);
		double U = Math.sqrt(DoubleMath.square(r - eSq * r0) + zSq);
		double V = Math.sqrt(DoubleMath.square(r - eSq * r0) + (1 - eSq) * zSq);
		double z0 = bSq * ECEF.z / (a * V);
		WGS.y = Math.atan2(ECEF.z + e2 * z0, r);
		// height
		WGS.z = U * (1 - bSq / (a * V));
	}

	public void setGravityModel(GravitationModel m) {
		this.gravitationModel = m;

		this.setChanged();
		this.notifyObservers();
	}

	public void setAtmosphereModel(AtmosphereModel m) {
		this.atmosphereModel = m;

		this.setChanged();
		this.notifyObservers();
	}

	public double getMajorAxis() {
		return this.a;
	}

	public double getMinorAxis() {
		return this.b;
	}

	public double getFlattering() {
		return this.f;
	}

	public double getGravitationalConstant() {
		return this.GM;
	}

	public double getLinearEccentricity() {
		return this.E;
	}

	public double getFirstEccentricity() {
		return this.e;
	}

	public void setRotationEnabled(boolean shouldRotate) {
		this.rotation = shouldRotate;
	}

	public double getOmega() {
		if (rotation)
			return this.omega;
		return 0;
	}
}
