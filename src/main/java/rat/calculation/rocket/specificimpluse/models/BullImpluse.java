package rat.calculation.rocket.specificimpluse.models;

import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;

/**
 * Bull method for calculating the specific input.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class BullImpluse extends SpecificImpulseModel {

	public BullImpluse() {
		super("Bull method for SIP calculation");
	}

	public void init(RocketStage stage, double p0, double g0) {

	}

	@Override
	public double calculateOutput(RocketStage stage, double p, double p0,
			double time, double g0) {
		double specificImpulse = -1.0;
		// recalculate bull constants
		stage.CalculateBullConstants(time);
		// calculate impulse
		specificImpulse = stage.getSpecImpuVacuum()
				* (1.0 - stage.getbBull() / stage.getaBull() * p
						/ stage.getBurnChamPressure(time));
		return specificImpulse;
	}
}
