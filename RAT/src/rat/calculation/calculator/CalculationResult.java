package rat.calculation.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class CalculationResult implements Serializable {

	private LinkedList<CalculationStepResult> calculationResult;

	public CalculationResult() {
		calculationResult = new LinkedList<CalculationStepResult>();
	}

	public void addCalculationStep(CalculationStepResult result) {
		calculationResult.add(result);
	}

	public void clear() {
		calculationResult.clear();
	}

	public double getMaxMachNumber() {
		double maxMach = Double.MIN_VALUE;

		for (CalculationStepResult csr : calculationResult) {
			if (csr.getMachNumber() > maxMach) {
				maxMach = csr.getMachNumber();
			}
		}
		return maxMach;
	}

	public double getMaxHeight() {
		double maxHeight = Double.MIN_VALUE;

		for (CalculationStepResult csr : calculationResult) {
			if (csr.getHeight() > maxHeight)
				maxHeight = csr.getHeight();
		}

		return maxHeight;

	}

	public double getMaxSpeed() {
		double maxSpeed = Double.MIN_VALUE;

		Iterator<CalculationStepResult> iter = calculationResult.iterator();
		while (iter.hasNext()) {
			CalculationStepResult csr = iter.next();
			if (csr.getSpeed() > maxSpeed) {
				maxSpeed = csr.getSpeed();
			}
		}
		return maxSpeed;
	}

	public double getRange() {
		// Range is the Distance of the last calculation step
		double range = calculationResult.get(calculationResult.size() - 1)
				.getGroundDistance();
		return range;
	}

	public double getDuration() {
		double duration = calculationResult.get(calculationResult.size() - 1)
				.getTime();
		return duration;
	}

	/**
	 * @return the calculationResult
	 */
	public LinkedList<CalculationStepResult> getCalculationResult() {
		return calculationResult;
	}

	public double[] getTimeArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			ret[i] = calculationResult.get(i).time;
		return ret;
	}

	public double[] getGroundDistanceArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in km
			ret[i] = calculationResult.get(i).groundDistance / 1000;
		return ret;
	}

	public double[] getHeightArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in km
			ret[i] = calculationResult.get(i).WGS84_O.z / 1000;
		return ret;
	}

	public double[] getThetaArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in Degree
			ret[i] = Math.toDegrees(calculationResult.get(i).theta);
		return ret;
	}

	public double[] getGammaArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in Degree
			ret[i] = Math.toDegrees(calculationResult.get(i).gamma);
		return ret;
	}

	public double[] getAlphaArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in Degree
			ret[i] = Math.toDegrees(calculationResult.get(i).alpha);
		return ret;
	}

	public double[] getThrustArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			// in kN
			ret[i] = calculationResult.get(i).thrust_B / 1000;
		return ret;
	}

	public double[] getMassArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			ret[i] = calculationResult.get(i).mass;
		return ret;
	}

	public double[] getSpeedArray() {
		double[] ret = new double[calculationResult.size()];
		for (int i = 0; i < calculationResult.size(); i++)
			ret[i] = calculationResult.get(i).speed_K.x;
		return ret;
	}

	/**
	 * Method returns the longitude and latitude as a list.
	 * 
	 * @return ArrayList<double>[2] - longitude/latitude
	 */
	public void getPositionLists(ArrayList<Double> latitudeOut,
			ArrayList<Double> longitudeOut) {
		for (CalculationStepResult result : calculationResult) {
			longitudeOut.add(result.WGS84_O.x);
			latitudeOut.add(result.WGS84_O.y);
		}
	}
}
