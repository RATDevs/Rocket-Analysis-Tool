package rat.calculation.planet.atmosphere.models;

import java.util.Iterator;
import java.util.LinkedList;

import rat.calculation.planet.atmosphere.AtmosphereData;
import rat.calculation.planet.atmosphere.AtmosphereModel;

/**
 * Implementation of the CIRA2012 atmosphere model.
 * 
 * http://spaceweather.usu.edu/files/uploads/PDF/
 * COSPAR_INTERNATIONAL_REFERENCE_ATMOSPHERE-CHAPTER-1_3(rev-01-11-08-2012).pdf
 * 
 * MEAN Solar Activity
 * 
 * @author Gerhard Mesch, Michael Sams
 **/
@SuppressWarnings("serial")
public class CIRAMean extends AtmosphereModel {

	private LinkedList<AtmosphereDataEntry> ciraData;

	public CIRAMean() {
		super("CIRA-Mean");
		ciraData = new LinkedList<AtmosphereDataEntry>();
		// CiraEntry( height [m], temperature [K], density [kg m^-3] )
		ciraData.add(new AtmosphereDataEntry(0, 300, 1.16));
		ciraData.add(new AtmosphereDataEntry(20000, 206, 0.0937));
		ciraData.add(new AtmosphereDataEntry(40000, 257, 0.00402));
		ciraData.add(new AtmosphereDataEntry(60000, 245, 0.000326));
		ciraData.add(new AtmosphereDataEntry(80000, 198, 1.83e-05));
		ciraData.add(new AtmosphereDataEntry(100000, 188, 5.73e-07));
		ciraData.add(new AtmosphereDataEntry(120000, 365, 2.03e-08));
		ciraData.add(new AtmosphereDataEntry(140000, 610, 3.44e-09));
		ciraData.add(new AtmosphereDataEntry(160000, 759, 1.2e-09));
		ciraData.add(new AtmosphereDataEntry(180000, 853, 5.46e-10));
		ciraData.add(new AtmosphereDataEntry(200000, 911, 2.84e-10));
		ciraData.add(new AtmosphereDataEntry(220000, 949, 1.61e-10));
		ciraData.add(new AtmosphereDataEntry(240000, 973, 9.6e-11));
		ciraData.add(new AtmosphereDataEntry(260000, 988, 5.97e-11));
		ciraData.add(new AtmosphereDataEntry(280000, 998, 3.83e-11));
		ciraData.add(new AtmosphereDataEntry(300000, 1000, 2.52e-11));
		ciraData.add(new AtmosphereDataEntry(320000, 1010, 1.69e-11));
		ciraData.add(new AtmosphereDataEntry(340000, 1010, 1.16e-11));
		ciraData.add(new AtmosphereDataEntry(360000, 1010, 7.99e-12));
		ciraData.add(new AtmosphereDataEntry(380000, 1010, 5.6e-12));
		ciraData.add(new AtmosphereDataEntry(400000, 1020, 3.96e-12));
		ciraData.add(new AtmosphereDataEntry(420000, 1020, 2.83e-12));
		ciraData.add(new AtmosphereDataEntry(440000, 1020, 2.03e-12));
		ciraData.add(new AtmosphereDataEntry(460000, 1020, 1.47e-12));
		ciraData.add(new AtmosphereDataEntry(480000, 1020, 1.07e-12));
		ciraData.add(new AtmosphereDataEntry(500000, 1020, 7.85e-13));
		ciraData.add(new AtmosphereDataEntry(520000, 1020, 5.78e-13));
		ciraData.add(new AtmosphereDataEntry(540000, 1020, 4.29e-13));
		ciraData.add(new AtmosphereDataEntry(560000, 1020, 3.19e-13));
		ciraData.add(new AtmosphereDataEntry(580000, 1020, 2.39e-13));
		ciraData.add(new AtmosphereDataEntry(600000, 1020, 1.8e-13));
		ciraData.add(new AtmosphereDataEntry(620000, 1020, 1.36e-13));
		ciraData.add(new AtmosphereDataEntry(640000, 1020, 1.04e-13));
		ciraData.add(new AtmosphereDataEntry(660000, 1020, 7.98e-14));
		ciraData.add(new AtmosphereDataEntry(680000, 1020, 6.16e-14));
		ciraData.add(new AtmosphereDataEntry(700000, 1020, 4.8e-14));
		ciraData.add(new AtmosphereDataEntry(720000, 1020, 3.76e-14));
		ciraData.add(new AtmosphereDataEntry(740000, 1020, 2.98e-14));
		ciraData.add(new AtmosphereDataEntry(760000, 1020, 2.38e-14));
		ciraData.add(new AtmosphereDataEntry(780000, 1020, 1.92e-14));
		ciraData.add(new AtmosphereDataEntry(800000, 1020, 1.57e-14));
		ciraData.add(new AtmosphereDataEntry(820000, 1020, 1.29e-14));
		ciraData.add(new AtmosphereDataEntry(840000, 1020, 1.07e-14));
		ciraData.add(new AtmosphereDataEntry(860000, 1020, 9.03e-15));
		ciraData.add(new AtmosphereDataEntry(880000, 1020, 7.67e-15));
		ciraData.add(new AtmosphereDataEntry(900000, 1020, 6.59e-15));
	}

	@Override
	public AtmosphereData calculateOutput(double height) {
		double h = height;
		double temperatur, pressure, density, sonicSpeed;
		temperatur = pressure = density = sonicSpeed = -1.0;
		if (ciraData.getLast().height <= h) {
			temperatur = ciraData.getLast().temperature;
			density = ciraData.getLast().density;
		}
		// search for correct entries in CIRA2012-table
		Iterator<AtmosphereDataEntry> iter = ciraData.iterator();
		AtmosphereDataEntry current;
		AtmosphereDataEntry next = iter.next(); // set first value in list
		while (iter.hasNext()) {
			current = next; // set lower bound
			next = iter.next(); // set upper bound
			// if between lower and upper bound --> interpolate
			if (h >= current.height && h < next.height) {
				// piecewise exponential interpolation --> density [kg m^-3]
				double b = 1 / ((next.height) - (current.height))
						* Math.log(current.density / next.density);
				double a = current.density * Math.exp(b * current.height);
				density = a * Math.exp((-b) * h);
				// linear interpolation --> temperature [K]
				double distance = next.height - current.height;
				double higherFactor = (h - current.height) / distance;
				double lowerFactor = (next.height - h) / distance;
				temperatur = higherFactor * next.temperature + lowerFactor
						* current.temperature;
				break;
			}
		}
		// Pressure [Pa]
		pressure = density * R * temperatur;
		// speed of sound [m/s]
		sonicSpeed = Math.sqrt(R * kappa * temperatur);

		return new AtmosphereData(temperatur, pressure, density, sonicSpeed);
	}
}