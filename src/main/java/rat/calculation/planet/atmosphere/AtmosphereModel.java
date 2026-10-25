package rat.calculation.planet.atmosphere;

import java.io.Serializable;

/**
 * Abstract class for implementing atmosphere models for calculation.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public abstract class AtmosphereModel implements Serializable {

	private final String modelName;

	// other constants
	public static final double kappa = 1.4d;
	public static final double R = 287.053d;

	protected AtmosphereModel(String name) {
		modelName = name;
	}

	public String getName() {
		return this.modelName;
	}

	/**
	 * This methods implements the atmosphere model.
	 * 
	 * @param height
	 *            double
	 * @return double[] - temperatur, pressure, density, sonicspeed
	 */
	public abstract AtmosphereData calculateOutput(double height);
}
