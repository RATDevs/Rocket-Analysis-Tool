package rat.calculation.rocket.specificimpluse.models;

import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;

/**
 * Model for linear interpolation of the specific impulse between sea level and
 * vacuum.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class LinearImpulseInterpolation extends SpecificImpulseModel {

	public LinearImpulseInterpolation() {
		super("Linear SIP interpolation");
	}

	public void init(RocketStage stage, double p0, double g0) {

	}

	@Override
	public double calculateOutput(RocketStage stage, double p, double p0,
			double time, double g0) {
		double specificImpuls = -1.0;
		// calculate specific impulse
		specificImpuls = stage.getSpecImpuVacuum()
				- (stage.getSpecImpuVacuum() - stage.getSpecImpuGround()) * (p)
				/ (p0);
		return specificImpuls;
	}

}
