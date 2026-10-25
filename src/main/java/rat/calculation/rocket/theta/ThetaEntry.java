package rat.calculation.rocket.theta;

import java.io.Serializable;

/** @author Gerhard Mesch */
@SuppressWarnings("serial")
public class ThetaEntry implements Serializable {

	public double t;
	public double theta;

	public ThetaEntry(double t, double theta) {
		this.t = t;
		this.theta = theta;
	}

}
