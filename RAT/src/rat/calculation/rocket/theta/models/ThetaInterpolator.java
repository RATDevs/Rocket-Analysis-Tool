package rat.calculation.rocket.theta.models;

import java.util.Iterator;

import rat.calculation.rocket.theta.ThetaEntry;
import rat.calculation.rocket.theta.ThetaList;
import rat.calculation.rocket.theta.ThetaModel;

/**
 * Standard Theta-Model for the angle of the rocket. Linear interpolates the
 * theta list.
 * 
 * @author Gerhard Mesch, Michael Sams
 */

@SuppressWarnings("serial")
public class ThetaInterpolator extends ThetaModel {

	public ThetaInterpolator() {
		super("Linear theta interpolator");
	}

	/**
	 * Interpolates the Phi value for the input values and the ThetaList in the
	 * buffer and write it into the buffer.
	 */
	public double calculateOutput(ThetaList thetaList, double t, double gamma) {
		double theta = -1;
		// time > last list entry, return gamma
		if (t > thetaList.list.getLast().t) {
			theta = gamma;
		} else if (t == thetaList.list.getLast().t) {
			theta = Math.toRadians(thetaList.list.getLast().theta);
		} else {
			// search entry and interpolate linear
			Iterator<ThetaEntry> iter = thetaList.list.iterator();
			ThetaEntry current;
			ThetaEntry next = iter.next(); // set first value in list
			while (iter.hasNext()) {
				current = next; // set lower bound
				next = iter.next(); // set upper bound
				// if between lower and upper bound --> interpolate
				if (t >= current.t && t < next.t) {
					double distance = next.t - current.t;
					double nextFactor = (t - current.t) / distance;
					double currentFactor = (next.t - t) / distance;
					theta = Math.toRadians(currentFactor * current.theta
							+ nextFactor * next.theta);
					break;
				}
			}
		}
		return theta;
	}

	/**
	 * get the first time entry in ThetaList where Theta is NOT 90 degrees
	 */
	public double getFirstThetaNon90DegTime(ThetaList thetaList) {
		// search entry and return time
		Iterator<ThetaEntry> iter = thetaList.list.iterator();
		ThetaEntry entry = iter.next(); // set first value in list
		double time = 0.0;
		if (entry.theta != 90.0d) {
			time = entry.t;
		} else {
			while (iter.hasNext()) {
				entry = iter.next(); // select next entry in list
				if (entry.theta >= 90.0d) {
					time = entry.t;
					break;
				}
			}
		}
		return time;
	}
}
