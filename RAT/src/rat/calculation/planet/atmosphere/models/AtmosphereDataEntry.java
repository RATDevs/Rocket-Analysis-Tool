package rat.calculation.planet.atmosphere.models;

import java.io.Serializable;

/**
 * Entry for the cira table.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class AtmosphereDataEntry implements Serializable {

	public double height, temperature, density;

	public AtmosphereDataEntry(double height, double temperature, double density) {
		this.height = height;
		this.temperature = temperature;
		this.density = density;
	}

}
