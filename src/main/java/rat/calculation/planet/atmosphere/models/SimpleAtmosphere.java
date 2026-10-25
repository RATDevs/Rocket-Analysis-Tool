package rat.calculation.planet.atmosphere.models;

import rat.calculation.math.DoubleMath;
import rat.calculation.planet.atmosphere.AtmosphereData;
import rat.calculation.planet.atmosphere.AtmosphereModel;

/**
 * Model for a simple atmosphere from the BMTC work.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class SimpleAtmosphere extends AtmosphereModel {

	public SimpleAtmosphere() {
		super("Simple atmosphere model");
	}

	@Override
	public AtmosphereData calculateOutput(double height) {
		double temperatur, pressure, density, sonicSpeed;
		double h = height / 1000.0d; // [km]
		// Temperature
		if (h < 11)
			temperatur = 288.15 - 6.5053 * h;
		else if (h >= 11 && h < 20)
			temperatur = 217;
		else if (h >= 20 && h < 32)
			temperatur = h + 197;
		else if (h >= 32 && h < 47)
			temperatur = 2.8 * h + 139.4;
		else if (h >= 47 && h < 51)
			temperatur = 271;
		else if (h >= 51 && h < 71)
			temperatur = -2.8 * h + 413.8;
		else if (h >= 71 && h < 86)
			temperatur = -2 * h + 357;
		else if (h >= 86 && h < 90)
			temperatur = -h + 273;
		else if (h >= 90 && h < 100)
			temperatur = 1.2 * h + 75;
		else
			temperatur = 5.3 * h - 335;

		// Pressure
		if (h >= 0 && h < 5)
			pressure = (-3E-10) * (Math.pow(h, 6)) + 4E-8 * (Math.pow(h, 5))
					- 5E-7 * (DoubleMath.square(DoubleMath.square(h))) - 9E-5
					* (Math.pow(h, 3)) + 0.0054 * (DoubleMath.square(h))
					- 0.1197 * h + 1.0139;
		else if (h >= 5 && h < 20)
			pressure = 9E-10 * (Math.pow(h, 6)) - 1E-7 * (Math.pow(h, 5))
					+ 5E-6 * (DoubleMath.square(DoubleMath.square(h))) - 0.0002
					* (Math.pow(h, 3)) + 0.0064 * (DoubleMath.square(h))
					- 0.123 * h + 1.0143;
		else if (h >= 20 && h <= 100)
			pressure = 1.1014 * Math.exp(-0.1466 * h);
		else
			pressure = 0;

		// p in [Pa]
		pressure = pressure * 100000;
		// Density Determination
		density = pressure / (R * temperatur);
		// speed of sound
		sonicSpeed = Math.sqrt(R * kappa * temperatur);

		return new AtmosphereData(temperatur, pressure, density, sonicSpeed);
	}
}
