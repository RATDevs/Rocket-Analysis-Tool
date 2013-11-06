package rat.calculation.rocket.specificimpluse.models;

import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;

/**
 * If SIP on ground is not known - calculate and set it! For SIP calculation
 * during flight - LinearImpulseInterpolation
 * 
 * @author Michael Sams
 */
@SuppressWarnings("serial")
public class NoSpecImpuGround extends SpecificImpulseModel {

	public NoSpecImpuGround() {
		super("SIP on ground NOT known");
	}

	@Override
	public void init(RocketStage stage, double p0, double g0) {
		double specImpuGnd = stage.getSpecImpuVacuum() - stage.getEpsilon()
				* stage.getCharacteristicVeleocity() * p0
				/ stage.getBurnChamPressure(0.0) / g0;
		stage.setSpecImpuGround(specImpuGnd);
		System.out.println("Calculated sea level ISP: " + specImpuGnd);
	}

	@Override
	public double calculateOutput(RocketStage stage, double p, double p0,
			double time, double g0) {
		// calculate specific impulse (LinearImpulseInterpolation)
		double specificImpuls = -1.0;
		specificImpuls = stage.getSpecImpuVacuum()
				- (stage.getSpecImpuVacuum() - stage.getSpecImpuGround()) * (p)
				/ (p0);
		return specificImpuls;
	}

}