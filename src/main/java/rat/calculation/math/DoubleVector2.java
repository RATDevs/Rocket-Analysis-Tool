package rat.calculation.math;

import java.io.Serializable;

/**
 * Three component vector class with double. *
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class DoubleVector2 implements Serializable {

	public double t, value;

	/** Creates a double vector with (0, 0, 0). */
	public DoubleVector2() {
		t = value = 0.0d;
	}

	/**
	 * Creates a vector with (t, value).
	 * 
	 * @param t
	 *            double
	 * @param value
	 *            double
	 */

	public DoubleVector2(double t, double value) {
		this.t = t;
		this.value = value;
	}

	public double getValue() {
		return this.value;
	}

	public double getTime() {
		return this.t;
	}

	/**
	 * Creates a new vector with the same values.
	 * 
	 * @param vector
	 *            - DoubleVector3
	 */
	public DoubleVector2(DoubleVector2 vector) {
		this.t = vector.t;
		this.value = vector.value;
	}

	public String toString() {
		return "(" + t + ", " + value + ")";
	}

	public void setVector(double t, double value) {
		this.t = t;
		this.value = value;
	}

	public void setVector(DoubleVector2 vector) {
		t = vector.t;
		value = vector.value;
	}

	/** Zero setter for lazy people :). */
	public void setZero() {
		t = value = 0.0d;
	}

	public void normalize() {
		double length = this.length();
		t = t / length;
		value = value / length;
	}

	public double length() {
		return Math.sqrt(DoubleMath.square(t) + DoubleMath.square(value));
	}

}