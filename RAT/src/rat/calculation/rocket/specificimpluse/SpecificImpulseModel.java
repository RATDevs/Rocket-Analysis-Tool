package rat.calculation.rocket.specificimpluse;

import java.io.Serializable;

import rat.calculation.rocket.RocketStage;

/**
 * Abstract class for different specific impulse models for the rocket engine.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public abstract class SpecificImpulseModel implements Serializable {

	public final String modelName;

	protected SpecificImpulseModel(String modelName) {
		this.modelName = modelName;
	}

	public abstract void init(RocketStage stage, double p0, double g0);

	public abstract double calculateOutput(RocketStage stage, double p,
			double p0, double time, double g0);

	public String getName() {
		return this.modelName;
	}
}
