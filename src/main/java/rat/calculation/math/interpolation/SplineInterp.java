package rat.calculation.math.interpolation;

import java.io.Serializable;

/**
 * cubic spline interpolation
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class SplineInterp extends BaseInterp implements Serializable {
	// variables
	private double y2[];

	/**
	 * constructor
	 * 
	 * @param xx
	 *            - double array with x-values
	 * @param yy
	 *            - double array with y-values
	 */
	public SplineInterp(double xx[], double yy[]) {
		super(xx, yy, 2);
		y2 = new double[n];
		// natural boundary conditions
		// sety2( 1.e99, 1.e99 );
		// set first derivatives as BCs
		sety2((yy[1] - yy[0]) / (xx[1] - xx[0]), (yy[n - 1] - yy[n - 2])
				/ (xx[n - 1] - xx[n - 2]));
	}

	//
	/**
	 * calculate unknown second derivatives y2
	 * 
	 * @param yp1
	 *            - first derivative at start point (boundary condition)
	 * @param ypn
	 *            - first derivative at end point (boundary condition)
	 */
	private void sety2(double yp1, double ypn) {
		double p, qn, sig, un;
		double u[] = new double[n - 1];
		// lower boundary condition
		if (yp1 > 0.99e99)
			y2[0] = u[0] = 0.0;
		else {
			y2[0] = -0.5;
			u[0] = (3.0 / (xx[1] - xx[0]))
					* ((yy[1] - yy[0]) / (xx[1] - xx[0]) - yp1);
		}
		// decomposition loop of tridiagonal algorithm
		for (int i = 1; i < n - 1; i++) {
			sig = (xx[i] - xx[i - 1]) / (xx[i + 1] - xx[i - 1]);
			p = sig * y2[i - 1] + 2.0;
			y2[i] = (sig - 1.0) / p;
			u[i] = (yy[i + 1] - yy[i]) / (xx[i + 1] - xx[i])
					- (yy[i] - yy[i - 1]) / (xx[i] - xx[i - 1]);
			u[i] = (6.0 * u[i] / (xx[i + 1] - xx[i - 1]) - sig * u[i - 1]) / p;
		}
		// upper boundary condition
		if (ypn > 0.99e99)
			qn = un = 0.0;
		else {
			qn = 0.5;
			un = (3.0 / (xx[n - 1] - xx[n - 2]))
					* (ypn - (yy[n - 1] - yy[n - 2]) / (xx[n - 1] - xx[n - 2]));
		}
		// backsubstitution loop of the tridiagonal algorithm
		y2[n - 1] = (un - qn * u[n - 2]) / (qn * y2[n - 2] + 1.0);
		for (int k = n - 2; k >= 0; k--) {
			y2[k] = y2[k] * y2[k + 1] + u[k];
		}
	}

	/**
	 * definition of spline-interpolation algorithm
	 * 
	 * @param jl
	 *            - lower bound of interval: ( jlo <= x <= jhi )
	 * @param x
	 *            - arbitrary x-value
	 * @return - interpolated y-value: y(x)
	 */
	@Override
	protected double rawinterp(int jl, double x) {
		// set intervall: klo <= x <= khi
		int klo = jl, khi = jl + 1;
		double y, h, b, a;
		h = xx[khi] - xx[klo];
		if (h == 0)
			return 0.0;
		a = (xx[khi] - x) / h;
		b = (x - xx[klo]) / h;
		y = a * yy[klo] + b * yy[khi]
				+ ((a * a * a - a) * y2[klo] + (b * b * b - b) * y2[khi])
				* (h * h) / 6.0;
		return y;
	}
}