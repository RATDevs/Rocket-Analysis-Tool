package rat.calculation.rocket.aero;

/**
 * Return object for aero data models.
 * 
 * @author Gerhard Mesch
 */
public class AeroData {

	public double CL, CD;

	public AeroData(double CL, double CD) {
		this.CL = CL;
		this.CD = CD;
	}
}
