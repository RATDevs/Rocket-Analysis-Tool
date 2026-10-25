package rat.calculation.math;

/** @author Gerhard Mesch */
public class DoubleMath {

	/**
	 * Fast square function for double.
	 * 
	 * @param a
	 *            double
	 * @return double
	 */
	public static double square(double a) {
		return a * a;
	}

	public static double normalizeAngle(double angle) {
		double newAngle = angle;
		while (newAngle <= -180)
			newAngle += 360;
		while (newAngle > 180)
			newAngle -= 360;
		return newAngle;
	}
}
