package rat.calculation.rocket.specificimpluse.models;

import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;

/**
 * If SIP in vacuum is not known - calculate and set it! For SIP calculation
 * during flight - LinearImpulseInterpolation
 * 
 * @author Michael Sams
 */
@SuppressWarnings("serial")
public class NoSpecImpuVacuum extends SpecificImpulseModel {

	public NoSpecImpuVacuum() {
		super("SIP in vacuum NOT known");
	}

	public void init(RocketStage stage, double p0, double g0) {
		// set specific impulse in vacuum
		double specImpuVac = stage.getSpecImpuGround() + stage.getEpsilon()
				* stage.getCharacteristicVeleocity() * p0
				/ stage.getBurnChamPressure(0.0) / g0;
		stage.setSpecImpuVacuum(specImpuVac);
		System.out.println("Calculated vacuum ISP: " + specImpuVac);
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