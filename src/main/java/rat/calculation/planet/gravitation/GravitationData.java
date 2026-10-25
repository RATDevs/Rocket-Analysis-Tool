package rat.calculation.planet.gravitation;

import rat.calculation.math.DoubleVector3;

/**
 * Return object for the gravitation models.
 * 
 * @author Gerhard Mesch
 */

public class GravitationData {

	public double gSurf;
	public DoubleVector3 g_O;

	public GravitationData(double g0, DoubleVector3 g_O) {
		this.gSurf = g0;
		this.g_O = g_O;
	}
}
