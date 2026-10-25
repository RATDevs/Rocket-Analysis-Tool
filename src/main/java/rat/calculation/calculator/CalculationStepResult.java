package rat.calculation.calculator;

import java.io.Serializable;
import java.text.DecimalFormat;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleVector2;
import rat.calculation.math.DoubleVector3;

/**
 * Class for storing the desired output values for each calculation step.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class CalculationStepResult implements Serializable {

	/** Output timestamp */
	public final double time;

	/** Rocket mass */
	public final double mass;

	/** Phi angle of the rocket. */
	public final double theta;

	/** Alpha angle of the rocket. */
	public final double alpha;
	public final double gamma;

	/** Speed in K-system. */
	public final DoubleVector3 speed_K;

	/** Rocket position-vector E-system. */
	public final DoubleVector3 r_E;
	public final DoubleVector3 r_I;

	/** Surface Position vector in E-system. */
	public final DoubleVector3 surfPosiVector;

	/** Rocket position vector in WGS84 */
	public final DoubleVector3 WGS84_O;

	/** Flown ground distance */
	public final double groundDistance;

	/** Thrust in the B-system. */
	public final double thrust_B;

	/** Mach number of the missle. */
	public final double machNumber;

	public final double fluelMassFlow;

	public CalculationStepResult(double time, double mass, double phi,
			double alpha, double gamma, DoubleVector3 V_K_K, DoubleVector3 r_E,
			DoubleVector3 r_I, DoubleVector3 surfPosition,
			double groundDistance, DoubleVector3 WGS84_O, double thrust_B,
			double mach, double fluelMassFlow) {
		this.time = time;
		this.mass = mass;
		this.theta = phi;
		this.alpha = alpha;
		this.gamma = gamma;
		this.speed_K = new DoubleVector3(V_K_K);
		this.r_E = new DoubleVector3(r_E);
		this.r_I = new DoubleVector3(r_I);
		this.surfPosiVector = new DoubleVector3(surfPosition);
		this.groundDistance = groundDistance;
		this.WGS84_O = new DoubleVector3(DoubleMath.normalizeAngle(Math
				.toDegrees(WGS84_O.x)), DoubleMath.normalizeAngle(Math
				.toDegrees(WGS84_O.y)), WGS84_O.z);
		this.thrust_B = thrust_B;
		this.machNumber = mach;
		this.fluelMassFlow = fluelMassFlow;

		// Consol output format
		// System.out.println(this.toString());
		// System.out.println(getFormatedStringWithUnits());
	}

	public String toString() {
		return time + ";" + mass + ";" + groundDistance + ";" + WGS84_O.z + ";"
				+ WGS84_O.x + ";" + WGS84_O.y + ";" + thrust_B + ";"
				+ machNumber + ";" + Math.toDegrees(theta) + ";"
				+ Math.toDegrees(alpha) + ";" + Math.toDegrees(gamma) + ";"
				+ speed_K.toStringSemicolon() + ";" + r_E.toStringSemicolon()
				+ ";" + r_I.toStringSemicolon() + ";"
				+ surfPosiVector.toStringSemicolon() + ";";
	}

	public String toCommaString() {
		DecimalFormat format = new DecimalFormat(
				"0.0000###########################################");
		return format.format(time) + ";" + format.format(mass) + ";"
				+ format.format(groundDistance) + ";"
				+ format.format(WGS84_O.z) + ";" + format.format(WGS84_O.x)
				+ ";" + format.format(WGS84_O.y) + ";"
				+ format.format(thrust_B) + ";" + format.format(machNumber)
				+ ";" + format.format(Math.toDegrees(theta)) + ";"
				+ format.format(Math.toDegrees(alpha)) + ";"
				+ format.format(Math.toDegrees(gamma)) + ";"
				+ format.format(speed_K.x) + ";" + format.format(speed_K.y)
				+ ";" + format.format(speed_K.z) + ";" + format.format(r_E.x)
				+ ";" + format.format(r_E.y) + ";" + format.format(r_E.z) + ";"
				+ format.format(r_I.x) + ";" + format.format(r_I.y) + ";"
				+ format.format(r_I.z) + ";" + format.format(surfPosiVector.x)
				+ ";" + format.format(surfPosiVector.y) + ";"
				+ format.format(surfPosiVector.z) + ";";
	}

	public static String toStringRowDescription() {
		return "time[s];mass[kg];groundDistance[m];WGS84-height[m];WGS84-long;"
				+ "WGS84-lat;thrust[N];mach-speed;"
				+ "theta[�];alpha[�];gamma[�];"
				+ "Speed_K.x[m/s];Speed_K.y[m/s];Speed_K.z[m/s];"
				+ "r_E.x[m];r_E.y[m];r_E.z[m];"
				+ "r_I.x[m];r_I.x[m];r_I.x[m];"
				+ "surfacePositionVector.x;surfacePositionVector.y;surfacePositionVector.z;";
	}

	public static String toFormatedStringRowDescription() {
		return "Time; Distance; Height; Velocity; Theta; Gamma; Alpha; Thrust; Mass; FluelFlow";
	}

	/**
	 * Returns the important values of the result with formatted double values.
	 * 
	 * @return String
	 */
	public String getFormatedString() {
		return String
				.format("%7.2f; %6.4f; %6.4f; %6.2f; %4.2f; %4.2f; %4.2f; %7.3f; %6.2f; %6.2f;",
						time, groundDistance / 1000, WGS84_O.z / 1000,
						speed_K.x, Math.toDegrees(theta),
						Math.toDegrees(gamma), Math.toDegrees(alpha), thrust_B,
						mass, fluelMassFlow);
	}

	/**
	 * Returns the important values of the result as a formated String with
	 * units.
	 * 
	 * @return String
	 */
	public String getFormatedStringWithUnits() {
		return String
				.format("%6.2f s; %6.3f km; %6.3f km; %6.2f m/s; %4.2f; %4.2f; %4.2f; %7.3f kN; %6.2f kg; %6.2f kg/s;",
						time, groundDistance / 1000, WGS84_O.z / 1000,
						speed_K.x, Math.toDegrees(theta),
						Math.toDegrees(gamma), Math.toDegrees(alpha),
						thrust_B / 1000.0f, mass, fluelMassFlow);
	}

	public double getMachNumber() {
		return this.machNumber;
	}

	public double getGroundDistance() {
		return this.groundDistance;
	}

	public double getTime() {
		return this.time;
	}

	public double getHeight() {
		return this.WGS84_O.z;
	}

	public double getLatitude() {
		return this.WGS84_O.y;
	}

	public double getLongitude() {
		return this.WGS84_O.x;
	}

	public DoubleVector2 getLatitudeLongitude() {
		return new DoubleVector2(WGS84_O.x, WGS84_O.y);
	}

	public double getSpeed() {
		return this.speed_K.x;
	}

	public double getThetaDegree() {
		return Math.toDegrees(this.theta);
	}

	public double getGammaDegree() {
		return Math.toDegrees(this.gamma);
	}

	public double getAlphaDegree() {
		return Math.toDegrees(this.alpha);
	}

	public double getThrust() {
		return this.thrust_B;
	}

	public double getMass() {
		return this.mass;
	}

	public String getKMLString() {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append(WGS84_O.x).append(",").append(WGS84_O.y).append(",")
				.append(WGS84_O.z).append(" ");
		return buffer.toString();
	}

}
