package rat.calculation.planet.gravitation.models;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleMatrix3x3;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.Planet;
import rat.calculation.planet.gravitation.GravitationData;
import rat.calculation.planet.gravitation.GravitationModel;

/**
 * Implementation of the Newton-Gravity model on earth.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class Newton extends GravitationModel {

	// mean earth radius --> Walter2012
	private double EarthRadius = 6378000.0d;

	public Newton() {
		super("Newton gravitation model");
	}

	@Override
	public GravitationData calculateOutput(DoubleVector3 WGS84,
			DoubleVector3 r_E, Planet planet, DoubleMatrix3x3 tM_OE) {
		double gSurf, g_O;
		// Boiffier - Somigliana --> grav. accel. on surface
		gSurf = 9.7803d * (1 + 0.0053 * DoubleMath.square(Math.sin(WGS84.y)) - 5.8d * 0.000001d * DoubleMath
				.square(Math.sin(2.0d * WGS84.y)));
		// Walter2012, FSD
		g_O = gSurf
				* DoubleMath.square((EarthRadius / (EarthRadius + WGS84.z)));
		return new GravitationData(gSurf, new DoubleVector3(0, 0, g_O));
	}
}
