package rat.calculation.math;

import java.io.Serializable;

/**
 * Three component vector class with double. Implements typical vector math.
 * Also implements set special vector functions.
 * 
 * Warning: Do not use set methods the vector itself!!!
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class DoubleVector3 implements Serializable {

	public double x, y, z;

	/** Creates a double vector with (0, 0, 0). */
	public DoubleVector3() {
		x = y = z = 0.0d;
	}

	/**
	 * Creates a vector with (x, y, z).
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param z
	 *            double
	 */
	public DoubleVector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new vector with the same values.
	 * 
	 * @param vector
	 *            - DoubleVector3
	 */
	public DoubleVector3(DoubleVector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	/**
	 * Returns the vector values separated by semicolons. 55.0;33.9;-10.33
	 * 
	 * @Return String
	 */
	public String toStringSemicolon() {
		return x + ";" + y + ";" + z;
	}

	public void setVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setVector(DoubleVector3 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
	}

	/** Zero setter for lazy people :). */
	public void setZero() {
		x = y = z = 0.0d;
	}

	public void normalize() {
		double length = this.length();
		x = x / length;
		y = y / length;
		z = z / length;
	}

	public double length() {
		return Math.sqrt(DoubleMath.square(x) + DoubleMath.square(y)
				+ DoubleMath.square(z));
	}

	public void scaleVector(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	public void addVector(DoubleVector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
	}

	public void subtractVector(DoubleVector3 subtrahend) {
		x = x - subtrahend.x;
		y = y - subtrahend.y;
		z = z - subtrahend.z;
	}

	public void setSubtract(DoubleVector3 minuend, DoubleVector3 subtrahend) {
		x = minuend.x - subtrahend.x;
		y = minuend.y - subtrahend.y;
		z = minuend.z - subtrahend.z;
	}

	public static DoubleVector3 subtractNew(DoubleVector3 minuend,
			DoubleVector3 subtrahend) {
		DoubleVector3 result = new DoubleVector3();
		result.x = minuend.x - subtrahend.x;
		result.y = minuend.y - subtrahend.y;
		result.z = minuend.z - subtrahend.z;
		return result;
	}

	public void setCrossProduct(DoubleVector3 a, DoubleVector3 b) {
		x = a.y * b.z - a.z * b.y;
		y = a.z * b.x - a.x * b.z;
		z = a.x * b.y - a.y * b.x;
	}

	public void setMatrixTimesVector(DoubleMatrix3x3 m, DoubleVector3 v) {
		x = m.a11 * v.x + m.a12 * v.y + m.a13 * v.z;
		y = m.a21 * v.x + m.a22 * v.y + m.a23 * v.z;
		z = m.a31 * v.x + m.a32 * v.y + m.a33 * v.z;
	}

	public void setMatrixTransposedTimesVector(DoubleMatrix3x3 m,
			DoubleVector3 v) {
		x = m.a11 * v.x + m.a21 * v.y + m.a31 * v.z;
		y = m.a12 * v.x + m.a22 * v.y + m.a32 * v.z;
		z = m.a13 * v.x + m.a23 * v.y + m.a33 * v.z;
	}

	// -------------------------------------------------------------------------------------------
	// Set special stuff
	// -------------------------------------------------------------------------------------------

	/**
	 * Sets the vector so S/C vector in ECEF coordinates.
	 * 
	 * @param N_phi
	 *            double
	 * @param WGS84
	 *            DoubleVector3 - WGS postion
	 * @param e2
	 *            double - first planet excentricity square
	 */
	public void setSCVector(double N_phi, DoubleVector3 WGS84, double e2) {

		x = (N_phi + WGS84.z) * Math.cos(WGS84.y);
		y = x * Math.sin(WGS84.x);
		x *= Math.cos(WGS84.x);
		z = (N_phi * (1 - e2) + WGS84.z) * Math.sin(WGS84.y);
	}

	/**
	 * Sets the surface vector.
	 * 
	 * @param N_phi
	 *            double
	 * @param WGS84
	 *            DoubleVector3 - WGS postion
	 * @param e2
	 *            double - first planet excentricity square
	 * @param h0_E
	 *            double - surface level
	 */

	public void setSurfaceVector(double N_phi, DoubleVector3 WGS84,
			double h0_E, double e2) {

		x = (N_phi + h0_E) * Math.cos(WGS84.y);
		y = x * Math.sin(WGS84.x);
		x *= Math.cos(WGS84.x);
		z = (N_phi * (1 - e2) + h0_E) * Math.sin(WGS84.y);
	}

	public void setWGS84Dot_O(DoubleVector3 WGS84_O, DoubleVector3 V_K_O,
			double N_phi, double M_phi) {
		x = V_K_O.y / ((N_phi + WGS84_O.z) * Math.cos(WGS84_O.y));
		y = V_K_O.x / (M_phi + WGS84_O.z);
		z = -V_K_O.z;
	}

	public void setOmegaEO_O(DoubleVector3 WGS84O, DoubleVector3 WGS84dotO) {
		this.x = WGS84dotO.x * Math.cos(WGS84O.y);
		this.y = -WGS84dotO.y;
		this.z = -WGS84dotO.x * Math.sin(WGS84O.y);
	}
}