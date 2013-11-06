package rat.calculation.rocket.aero.models;

import java.util.Iterator;
import java.util.LinkedList;

import rat.calculation.math.DoubleMath;
import rat.calculation.rocket.aero.AeroData;
import rat.calculation.rocket.aero.AeroDataEntry;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.aero.AeroModel;

/**
 * Class uses the aeroTable of a RocketStage for the linear interpolation of the
 * aero coefficients.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class AeroDataInterpolator extends AeroModel {

	public AeroDataInterpolator() {
		super("Linear interpolation");
	}

	@Override
	public void init(AeroDataList aeroDataList) {

	}

	public AeroData calculateOutput(AeroDataList aeroDataList, double alpha,
			double mach, boolean engine, double kFactor) {
		double CL, CD;
		LinkedList<AeroDataEntry> aeroTable = aeroDataList.getAeroDataList();
		CL = CD = -1.0;
		// table to small
		if (mach >= aeroTable.getLast().mach) {
			CL = alpha * aeroTable.getLast().CLalpha;
			if (engine) {
				CD = aeroTable.getLast().CD0EngON + kFactor
						* DoubleMath.square(CL);
			} else {
				CD = aeroTable.getLast().CD0EngOFF + kFactor
						* DoubleMath.square(CL);
			}
		} else {
			// search entry and interpolate linear
			Iterator<AeroDataEntry> iter = aeroTable.iterator();
			AeroDataEntry current;
			AeroDataEntry next = iter.next(); // set first value in list
			while (iter.hasNext()) {
				current = next; // set lower bound
				next = iter.next(); // set upper bound
				// if between lower and upper bound --> interpolate
				if (mach >= current.mach && mach < next.mach) {
					// calculate linear interpolation factors
					double distance = next.mach - current.mach;
					double nextFactor = (mach - current.mach) / distance;
					double currentFactor = (next.mach - mach) / distance;
					CL = alpha
							* (current.CLalpha * currentFactor + next.CLalpha
									* nextFactor);
					if (engine) {
						CD = currentFactor * current.CD0EngON + nextFactor
								* next.CD0EngON + kFactor
								* DoubleMath.square(CL);
					} else {
						CD = currentFactor * current.CD0EngOFF + nextFactor
								* next.CD0EngOFF + kFactor
								* DoubleMath.square(CL);
					}
					break;
				}
			}
		}
		return new AeroData(CL, CD);
	}
}
