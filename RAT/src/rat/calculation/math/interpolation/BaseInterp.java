package rat.calculation.math.interpolation;

import java.io.Serializable;

/**
 * Abstract class for interpolation models
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public abstract class BaseInterp implements Serializable {
	// cor...search with correlated values (yes / no)
	// n.....size of vector x & y
	// m.....number of points used in interpolation
	// xx....array with x-values
	// yy....array with y-values
	private boolean cor;
	protected int n, m, jsav, dj;
	protected double xx[], yy[];

	/**
	 * constructor for BaseInterp-class
	 * 
	 * @param x
	 *            - double array with x-values
	 * @param y
	 *            - double array with x-values
	 * @param m
	 *            - number of points used in interpolation
	 */
	protected BaseInterp(double x[], double y[], int m) {
		n = x.length;
		this.m = m;
		jsav = 0;
		cor = false;
		xx = x;
		yy = y;
		dj = Math.min(1, (int) Math.pow((double) n, 0.25));
	}

	/**
	 * public interpolation method
	 * 
	 * @param x
	 *            - arbitrary x-value
	 * @return - interpolated y-value: y(x)
	 */
	public double interp(double x) {
		int jlo = 0; // lower bound of intervall: jlo <= x <= jhi
		// choose search algorithm
		if (cor)
			jlo = hunt(x);
		else
			jlo = locate(x);
		return rawinterp(jlo, x);
	}

	/**
	 * BISECTION-algorithm for searching lower bound of interval: ( jlo <= x <=
	 * jhi )
	 * 
	 * @param x
	 *            - arbitrary x-value
	 * @return - lower bound "jlo" of interval: ( jlo <= x <= jhi )
	 */
	private int locate(double x) {
		int ju, jm, jl;

		if (n < 2 || m < 2 || m > n) {
			System.err
					.println("BaseInterpolation.java, line73, locate size error");
			return 0;
		}
		boolean ascnd = (xx[n - 1] >= xx[0]);
		jl = 0;
		ju = n - 1;
		while (ju - jl > 1) {
			jm = (ju + jl) >> 1;
			if (x >= xx[jm] == ascnd)
				jl = jm;
			else
				ju = jm;
		}
		if (Math.abs(jl - jsav) > dj)
			cor = false;
		else
			cor = true;
		jsav = jl;
		return Math.max(0, Math.min(n - m, jl - ((m - 2) >> 1)));
	}

	/**
	 * HUNT-algorithm for searching lower bound of interval: ( jlo <= x <= jhi )
	 * 
	 * The routine searches from a previous known position in the table by
	 * increasing steps and then converges by bisection
	 * 
	 * @param x
	 *            - arbitrary x-value
	 * @return - lower bound "jlo" of interval: ( jlo <= x <= jhi )
	 */
	private int hunt(double x) {
		int jl = jsav, jm, ju, inc = 1;
		if (n < 2 || m < 2 || m > n) {
			System.err
					.println("BaseInterpolation.java, line111, hunt size error");
			return 0;
		}
		boolean ascnd = (xx[n - 1] >= xx[0]);
		if (jl < 0 || jl > n - 1) {
			jl = 0;
			ju = n - 1;
		} else {
			if (x >= xx[jl] == ascnd) {
				for (;;) {
					ju = jl + inc;
					if (ju >= n - 1) {
						ju = n - 1;
						break;
					} else if (x < xx[ju] == ascnd)
						break;
					else {
						jl = ju;
						inc += inc;
					}
				}
			} else {
				ju = jl;
				for (;;) {
					jl = jl - inc;
					if (jl <= 0) {
						jl = 0;
						break;
					} else if (x >= xx[jl] == ascnd)
						break;
					else {
						ju = jl;
						inc += inc;
					}
				}
			}
		}
		while ((ju - jl) > 1) {
			jm = (ju + jl) >> 1;
			if (x >= xx[jm] == ascnd)
				jl = jm;
			else
				ju = jm;
		}
		if (Math.abs(jl - jsav) > dj)
			cor = false;
		else
			cor = true;
		jsav = jl;
		return Math.max(0, Math.min(n - m, jl - ((m - 2) >> 1)));
	}

	/**
	 * definition of interpolation algorithm
	 * 
	 * @param jlo
	 *            - lower bound of interval: ( jlo <= x <= jhi )
	 * @param x
	 *            - arbitrary x-value
	 * @return - interpolated y-value: y(x)
	 */
	protected abstract double rawinterp(int jlo, double x);
}