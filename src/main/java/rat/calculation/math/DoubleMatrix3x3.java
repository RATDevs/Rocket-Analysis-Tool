package rat.calculation.math;

import java.io.Serializable;

/**
 * 3x3 matrix using doubles. Also includes method to calculate the required
 * transformation matrices.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class DoubleMatrix3x3 implements Serializable {

	double a11, a12, a13;
	double a21, a22, a23;
	double a31, a32, a33;

	public DoubleMatrix3x3() {
		a11 = a12 = a13 = 0.0d;
		a21 = a22 = a23 = 0.0d;
		a31 = a32 = a33 = 0.0d;
	}

	public String toSring() {
		return "(" + a11 + "  " + a12 + "  " + a13 + ")\n" + "(" + a21 + "  "
				+ a22 + "  " + a23 + ")\n" + "(" + a31 + "  " + a32 + "  "
				+ a33 + ")\n";
	}

	// -------------------------------------------------------------------------------------------
	// Set special stuff
	// -------------------------------------------------------------------------------------------

	public void setKBMatrix(double alpha) {
		a12 = a21 = a23 = a32 = 0.0d;
		a22 = 1.0d;
		a11 = a33 = Math.cos(alpha);
		a13 = Math.sin(alpha);
		a31 = -a13;
	}

	public void setKOMatrix(double chi, double gamma) {
		double coschi = Math.cos(chi);
		double sinchi = Math.sin(chi);
		double cosgamma = Math.cos(gamma);
		double singamma = Math.sin(gamma);
		a11 = coschi * cosgamma;
		a12 = sinchi * cosgamma;
		a13 = -singamma;
		a21 = -sinchi;
		a22 = coschi;
		a23 = 0.0d;
		a31 = coschi * singamma;
		a32 = sinchi * singamma;
		a33 = cosgamma;
	}

	public void setOEMatrix(DoubleVector3 WGS84_O) {
		double sinx = Math.sin(WGS84_O.x); // sin(lambda)
		double siny = Math.sin(WGS84_O.y); // sin(phi)
		double cosx = Math.cos(WGS84_O.x); // cos(lambda)
		double cosy = Math.cos(WGS84_O.y); // cos(phi)
		a11 = -siny * cosx;
		a12 = -siny * sinx;
		a13 = cosy;
		a21 = -sinx;
		a22 = cosx;
		a23 = 0.0d;
		a31 = -cosy * cosx;
		a32 = -cosy * sinx;
		a33 = -siny;
	}

	public void setIEMatrix(double tn, double omega) {
		double value = tn * omega;
		a11 = Math.cos(value);
		a12 = -Math.sin(value);
		a21 = Math.sin(value);
		a22 = Math.cos(value);
		a13 = a23 = a31 = a32 = 0.0d;
		a33 = 1.0d;
	}

	public void setR1Matrix(double u2, double beta, double w, double E2,
			double lambda) {
		double value = Math.sqrt(u2) / (w * Math.sqrt(u2 + E2));
		a11 = value * Math.cos(beta) * Math.cos(lambda);
		a12 = -1.0 / w * Math.sin(beta) * Math.cos(lambda);
		a13 = -Math.sin(lambda);
		a21 = value * Math.cos(beta) * Math.sin(lambda);
		a22 = -1.0 / w * Math.sin(beta) * Math.sin(lambda);
		a23 = Math.cos(lambda);
		a31 = 1.0 / w * Math.sin(beta);
		a32 = value * Math.cos(beta);
		a33 = 0.0d;
	}
}