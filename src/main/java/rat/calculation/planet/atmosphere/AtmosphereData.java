package rat.calculation.planet.atmosphere;

/**
 * Return object for the atmosphere models
 * 
 * @author Gerhard Mesch
 */
public class AtmosphereData {

	public double temperatur, pressure, density, sonicSpeed;

	public AtmosphereData(double temperatur, double pressure, double density,
			double sonicSpeed) {
		this.temperatur = temperatur;
		this.pressure = pressure;
		this.density = density;
		this.sonicSpeed = sonicSpeed;
	}

}
