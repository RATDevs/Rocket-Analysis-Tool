package rat.calculation.math.interpolation;

import java.io.Serializable;

/**
 * Polynomial interpolation (Lagrange's formula)
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class PolyInterp extends BaseInterp implements Serializable {

	// error
	private double dy;

	/**
	 * constructor
	 * 
	 * @param xx
	 *            - double array with x-values
	 * @param yy
	 *            - double array with y-values
	 * @param m
	 *            - number of points used in interpolation
	 */
	public PolyInterp(double xx[], double yy[], int m) {
		super(xx, yy, m);
		dy = 0.0;
	}

	/**
	 * get error of polynomial interpolation
	 * 
	 * @return - dy - error
	 */
	public double getError() {
		return dy;
	}

	/**
	 * definition of linear-interpolation algorithm
	 * 
	 * @param jl
	 *            - lower bound of interval: ( jl <= x <= jh )
	 * @param x
	 *            - arbitrary x-value
	 * @return - interpolated y-value: y(x)
	 */
	@Override
	protected double rawinterp(int jl, double x) {
		int ns = 0;
		double y, den, dif, dift, ho, hp, w;
		double c[] = new double[m];
		double d[] = new double[m];
		dif = Math.abs(x - xx[0]);
		for (int i = 0; i < m; i++) {
			dift = Math.abs(x - xx[i + jl]);
			if (dift < dif) {
				ns = i;
				dif = dift;
			}
			c[i] = yy[i + jl];
			d[i] = yy[i + jl];
		}
		y = yy[(ns--) + jl];
		for (int ma = 1; ma < m; ma++) {
			for (int i = 0; i < m - ma; i++) {
				ho = xx[i + jl] - x;
				hp = xx[i + ma + jl] - x;
				w = c[i + 1] - d[i];
				den = ho - hp;
				if (den == 0.0)
					System.out.println("PolyInterp error");
				den = w / den;
				d[i] = hp * den;
				c[i] = ho * den;
			}
			// y += (dy=(2*(ns+1) < (m-ma) ? c[ns+1] : d[ns--]));
			if (2 * (ns + 1) < (m - ma))
				dy = c[ns + 1];
			else
				dy = d[ns--];
			y += dy;
		}
		return y;
	}
}