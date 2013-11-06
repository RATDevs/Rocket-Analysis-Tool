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
 * LOW Solar Activity
 * 
 * @author Gerhard Mesch, Michael Sams
 **/
@SuppressWarnings("serial")
public class CIRALow extends AtmosphereModel {

	private LinkedList<AtmosphereDataEntry> ciraData;

	public CIRALow() {
		super("CIRA-Low");
		ciraData = new LinkedList<AtmosphereDataEntry>();
		// CiraEntry( height [m], temperature [K], density [kg m^-3] )
		ciraData.add(new AtmosphereDataEntry(0, 300, 1.07));
		ciraData.add(new AtmosphereDataEntry(20000, 206, 0.0868));
		ciraData.add(new AtmosphereDataEntry(40000, 257, 0.00372));
		ciraData.add(new AtmosphereDataEntry(60000, 245, 0.000301));
		ciraData.add(new AtmosphereDataEntry(80000, 206, 1.68e-05));
		ciraData.add(new AtmosphereDataEntry(100000, 171, 6.18e-07));
		ciraData.add(new AtmosphereDataEntry(120000, 353, 1.88e-08));
		ciraData.add(new AtmosphereDataEntry(140000, 521, 3.08e-09));
		ciraData.add(new AtmosphereDataEntry(160000, 605, 9.49e-10));
		ciraData.add(new AtmosphereDataEntry(180000, 648, 3.7e-10));
		ciraData.add(new AtmosphereDataEntry(200000, 670, 1.63e-10));
		ciraData.add(new AtmosphereDataEntry(220000, 682, 7.8e-11));
		ciraData.add(new AtmosphereDataEntry(240000, 688, 3.97e-11));
		ciraData.add(new AtmosphereDataEntry(260000, 692, 2.13e-11));
		ciraData.add(new AtmosphereDataEntry(280000, 694, 1.18e-11));
		ciraData.add(new AtmosphereDataEntry(300000, 695, 6.8e-12));
		ciraData.add(new AtmosphereDataEntry(320000, 696, 4.01e-12));
		ciraData.add(new AtmosphereDataEntry(340000, 696, 2.41e-12));
		ciraData.add(new AtmosphereDataEntry(360000, 696, 1.47e-12));
		ciraData.add(new AtmosphereDataEntry(380000, 696, 9.14e-13));
		ciraData.add(new AtmosphereDataEntry(400000, 696, 5.75e-13));
		ciraData.add(new AtmosphereDataEntry(420000, 696, 3.66e-13));
		ciraData.add(new AtmosphereDataEntry(440000, 696, 2.35e-13));
		ciraData.add(new AtmosphereDataEntry(460000, 696, 1.53e-13));
		ciraData.add(new AtmosphereDataEntry(480000, 696, 1.01e-13));
		ciraData.add(new AtmosphereDataEntry(500000, 696, 6.79e-14));
		ciraData.add(new AtmosphereDataEntry(520000, 696, 4.63e-14));
		ciraData.add(new AtmosphereDataEntry(540000, 696, 3.21e-14));
		ciraData.add(new AtmosphereDataEntry(560000, 696, 2.28e-14));
		ciraData.add(new AtmosphereDataEntry(580000, 696, 1.65e-14));
		ciraData.add(new AtmosphereDataEntry(600000, 696, 1.23e-14));
		ciraData.add(new AtmosphereDataEntry(620000, 696, 9.37e-15));
		ciraData.add(new AtmosphereDataEntry(640000, 696, 7.33e-15));
		ciraData.add(new AtmosphereDataEntry(660000, 696, 5.88e-15));
		ciraData.add(new AtmosphereDataEntry(680000, 696, 4.83e-15));
		ciraData.add(new AtmosphereDataEntry(700000, 696, 4.04e-15));
		ciraData.add(new AtmosphereDataEntry(720000, 696, 3.44e-15));
		ciraData.add(new AtmosphereDataEntry(740000, 696, 2.98e-15));
		ciraData.add(new AtmosphereDataEntry(760000, 696, 2.61e-15));
		ciraData.add(new AtmosphereDataEntry(780000, 696, 2.31e-15));
		ciraData.add(new AtmosphereDataEntry(800000, 696, 2.06e-15));
		ciraData.add(new AtmosphereDataEntry(820000, 696, 1.85e-15));
		ciraData.add(new AtmosphereDataEntry(840000, 696, 1.67e-15));
		ciraData.add(new AtmosphereDataEntry(860000, 696, 1.51e-15));
		ciraData.add(new AtmosphereDataEntry(880000, 696, 1.38e-15));
		ciraData.add(new AtmosphereDataEntry(900000, 696, 1.26e-15));
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
		AtmosphereDataEntry next = iter.next();
		while (iter.hasNext()) {
			current = next;
			next = iter.next();
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