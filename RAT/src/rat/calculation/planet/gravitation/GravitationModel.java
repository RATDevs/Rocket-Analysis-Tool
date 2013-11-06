package rat.calculation.planet.gravitation;

import java.io.Serializable;

import rat.calculation.math.DoubleMatrix3x3;
import rat.calculation.math.DoubleVector3;
import rat.calculation.planet.Planet;

/**
 * Abstract class for implementing new gravitation models.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public abstract class GravitationModel implements Serializable {

	private final String modelName;

	public GravitationModel(String name) {
		modelName = name;
	}

	public String getName() {
		return this.modelName;
	}

	/** Implements the update of the buffer with the current input values. */
	public abstract GravitationData calculateOutput(DoubleVector3 WGS84,
			DoubleVector3 r_E, Planet planet, DoubleMatrix3x3 tM_OE);
}
