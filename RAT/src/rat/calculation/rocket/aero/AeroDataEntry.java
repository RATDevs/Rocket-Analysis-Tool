package rat.calculation.rocket.aero;

import java.io.Serializable;

/**
 * Object for aero data lists. Sorting after mach number of the table is
 * important.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class AeroDataEntry implements Serializable {

	// lookup mach number
	public double mach;

	// table output
	public double CD0EngON;
	public double CD0EngOFF;
	public double CLalpha;

	public AeroDataEntry(double mach, double CD0EngON, double CD0EngOFF,
			double CLalpha) {
		this.mach = mach;
		this.CD0EngON = CD0EngON;
		this.CD0EngOFF = CD0EngOFF;
		this.CLalpha = CLalpha;
	}
}
