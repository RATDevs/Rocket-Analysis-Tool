package rat.calculation.math.interpolation;

import java.io.Serializable;

/**
 * linear interpolation
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class LinearInterp extends BaseInterp implements Serializable {

	/**
	 * constructor
	 * 
	 * @param xx
	 *            - double array with x-values
	 * @param yy
	 *            - double array with y-values
	 */
	public LinearInterp(double xx[], double yy[]) {
		super(xx, yy, 2);
	}

	/**
	 * definition of linear-interpolation algorithm
	 * 
	 * @param j
	 *            - lower bound of interval: ( jlo <= x <= jhi )
	 * @param x
	 *            - arbitrary x-value
	 * @return - interpolated y-value: y(x)
	 */
	@Override
	protected double rawinterp(int j, double x) {
		if (xx[j] == xx[j + 1])
			return yy[j];
		else
			return yy[j] + ((x - xx[j]) / (xx[j + 1] - xx[j]))
					* (yy[j + 1] - yy[j]);
	}
}