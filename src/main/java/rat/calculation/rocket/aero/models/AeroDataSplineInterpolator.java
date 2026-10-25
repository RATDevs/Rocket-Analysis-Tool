package rat.calculation.rocket.aero.models;

import java.util.Iterator;
import java.util.LinkedList;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.interpolation.SplineInterp;
import rat.calculation.rocket.aero.AeroData;
import rat.calculation.rocket.aero.AeroDataEntry;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.aero.AeroModel;

/**
 * Class uses the aeroTable of a RocketStage for the cubic-spline interpolation
 * of the aero coefficients.
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class AeroDataSplineInterpolator extends AeroModel {

	private SplineInterp splineCL;
	private SplineInterp splineCDON;
	private SplineInterp splineCDOFF;

	public AeroDataSplineInterpolator() {
		super("Cubic-Spline interpolation");
	}

	@Override
	public void init(AeroDataList aeroDataList) {
		LinkedList<AeroDataEntry> aeroTable = aeroDataList.getAeroDataList();
		int tableLength = aeroTable.size();
		double machTable[] = new double[tableLength];
		double CLalphaTable[] = new double[tableLength];
		double CD0EngOnTable[] = new double[tableLength];
		double CD0EngOFFTable[] = new double[tableLength];
		Iterator<AeroDataEntry> iter = aeroTable.iterator();
		AeroDataEntry next;

		// rewrite into arrays for interpolation
		for (int i = 0; i < tableLength; i++) {
			next = iter.next();
			machTable[i] = next.mach;
			CLalphaTable[i] = next.CLalpha;
			CD0EngOnTable[i] = next.CD0EngON;
			CD0EngOFFTable[i] = next.CD0EngOFF;
		}
		// spline interpolation
		// CL with CLAlpha
		splineCL = new SplineInterp(machTable, CLalphaTable);
		splineCDON = new SplineInterp(machTable, CD0EngOnTable);
		splineCDOFF = new SplineInterp(machTable, CD0EngOFFTable);
		// System.out.println(super.modelName + " init() done.");
	}

	public AeroData calculateOutput(AeroDataList aeroDataList, double alpha,
			double mach, boolean engine, double kFactor) {
		double CL, CD;
		LinkedList<AeroDataEntry> aeroTable = aeroDataList.getAeroDataList();
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
			CL = splineCL.interp(mach) * alpha;
			// CD with CD0i, kFaktor and CL
			if (engine) {
				CD = splineCDON.interp(mach) + kFactor * DoubleMath.square(CL);
			} else {
				CD = splineCDOFF.interp(mach) + kFactor * DoubleMath.square(CL);
			}
		}
		return new AeroData(CL, CD);
	}
}