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
 * HIGH Solar Activity
 * 
 * @author Gerhard Mesch, Michael Sams
 **/
@SuppressWarnings("serial")
public class CIRAHigh extends AtmosphereModel {

	private LinkedList<AtmosphereDataEntry> ciraData;

	public CIRAHigh() {
		super("CIRA-High");
		ciraData = new LinkedList<AtmosphereDataEntry>();
		// CiraEntry( height [m], temperature [K], density [kg m^-3] )
		ciraData.add(new AtmosphereDataEntry(0, 300, 1.29));
		ciraData.add(new AtmosphereDataEntry(20000, 206, 0.105));
		ciraData.add(new AtmosphereDataEntry(40000, 257, 0.00449));
		ciraData.add(new AtmosphereDataEntry(60000, 245, 0.000364));
		ciraData.add(new AtmosphereDataEntry(80000, 193, 2.03e-05));
		ciraData.add(new AtmosphereDataEntry(100000, 202, 5.64e-07));
		ciraData.add(new AtmosphereDataEntry(120000, 380, 2.22e-08));
		ciraData.add(new AtmosphereDataEntry(140000, 710, 3.93e-09));
		ciraData.add(new AtmosphereDataEntry(160000, 916, 1.54e-09));
		ciraData.add(new AtmosphereDataEntry(180000, 1050, 7.87e-10));
		ciraData.add(new AtmosphereDataEntry(200000, 1140, 4.57e-10));
		ciraData.add(new AtmosphereDataEntry(220000, 1190, 2.86e-10));
		ciraData.add(new AtmosphereDataEntry(240000, 1230, 1.87e-10));
		ciraData.add(new AtmosphereDataEntry(260000, 1250, 1.27e-10));
		ciraData.add(new AtmosphereDataEntry(280000, 1270, 8.87e-11));
		ciraData.add(new AtmosphereDataEntry(300000, 1280, 6.31e-11));
		ciraData.add(new AtmosphereDataEntry(320000, 1290, 4.56e-11));
		ciraData.add(new AtmosphereDataEntry(340000, 1300, 3.34e-11));
		ciraData.add(new AtmosphereDataEntry(360000, 1300, 2.47e-11));
		ciraData.add(new AtmosphereDataEntry(380000, 1300, 1.85e-11));
		ciraData.add(new AtmosphereDataEntry(400000, 1300, 1.4e-11));
		ciraData.add(new AtmosphereDataEntry(420000, 1300, 1.06e-11));
		ciraData.add(new AtmosphereDataEntry(440000, 1310, 8.13e-12));
		ciraData.add(new AtmosphereDataEntry(460000, 1310, 6.26e-12));
		ciraData.add(new AtmosphereDataEntry(480000, 1310, 4.84e-12));
		ciraData.add(new AtmosphereDataEntry(500000, 1310, 3.76e-12));
		ciraData.add(new AtmosphereDataEntry(520000, 1310, 2.94e-12));
		ciraData.add(new AtmosphereDataEntry(540000, 1310, 2.31e-12));
		ciraData.add(new AtmosphereDataEntry(560000, 1310, 1.82e-12));
		ciraData.add(new AtmosphereDataEntry(580000, 1310, 1.43e-12));
		ciraData.add(new AtmosphereDataEntry(600000, 1310, 1.14e-12));
		ciraData.add(new AtmosphereDataEntry(620000, 1310, 9.06e-13));
		ciraData.add(new AtmosphereDataEntry(640000, 1310, 7.23e-13));
		ciraData.add(new AtmosphereDataEntry(660000, 1310, 5.79e-13));
		ciraData.add(new AtmosphereDataEntry(680000, 1310, 4.65e-13));
		ciraData.add(new AtmosphereDataEntry(700000, 1310, 3.75e-13));
		ciraData.add(new AtmosphereDataEntry(720000, 1310, 3.03e-13));
		ciraData.add(new AtmosphereDataEntry(740000, 1310, 2.46e-13));
		ciraData.add(new AtmosphereDataEntry(760000, 1310, 2e-13));
		ciraData.add(new AtmosphereDataEntry(780000, 1310, 1.63e-13));
		ciraData.add(new AtmosphereDataEntry(800000, 1310, 1.34e-13));
		ciraData.add(new AtmosphereDataEntry(820000, 1310, 1.1e-13));
		ciraData.add(new AtmosphereDataEntry(840000, 1310, 9.06e-14));
		ciraData.add(new AtmosphereDataEntry(860000, 1310, 7.5e-14));
		ciraData.add(new AtmosphereDataEntry(880000, 1310, 6.23e-14));
		ciraData.add(new AtmosphereDataEntry(900000, 1310, 6e-14));
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