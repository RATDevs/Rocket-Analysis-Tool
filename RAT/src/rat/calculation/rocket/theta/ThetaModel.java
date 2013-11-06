package rat.calculation.rocket.theta;

import java.io.Serializable;

/**
 * Abstract class for ThetaModels.
 * 
 * @author Gerhard Mesch, Michael Sams
 */
@SuppressWarnings("serial")
public abstract class ThetaModel implements Serializable {

	public final String thetaModelName;

	protected ThetaModel(String name) {
		thetaModelName = name;
	}

	/** This method should calculate the theta angle of the rocket. */
	public abstract double calculateOutput(ThetaList thetaList, double t,
			double gamma);

	public String getName() {
		return this.thetaModelName;
	}

	public abstract double getFirstThetaNon90DegTime(ThetaList thetaList);

}
