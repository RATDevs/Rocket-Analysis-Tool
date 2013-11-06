package rat.calculation.rocket.aero;

import java.io.Serializable;

/**
 * Abstract class for aeroModels.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public abstract class AeroModel implements Serializable {

	public final String modelName;

	protected AeroModel(String modelName) {
		this.modelName = modelName;
	}

	public abstract void init(AeroDataList aeroDataList);

	public abstract AeroData calculateOutput(AeroDataList aeroDataList,
			double alpha, double mach, boolean engine, double kFactor);

	public String getName() {
		return this.modelName;
	}
}
