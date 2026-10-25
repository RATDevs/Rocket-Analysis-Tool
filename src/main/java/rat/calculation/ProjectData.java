package rat.calculation;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import rat.calculation.math.DoubleVector2;
import rat.controller.updateObjects.LocationLatitudeChange;
import rat.controller.updateObjects.LocationLongitudeChange;

/**
 * Class for input data which is not matching any specific other class.
 * 
 * @author Gerhard Mesch
 */
@SuppressWarnings("serial")
public class ProjectData extends Observable implements Serializable {

	// Calculation time step size in [s].
	private double delta_t;
	// Cancel time for the calculation in [s].
	private double cancelTime;

	// LinkedList for storing the variable iterations steps.
	private LinkedList<DoubleVector2> outIterations = new LinkedList<DoubleVector2>();

	private double startLong, startLati;
	private double t0, h0_E, h_O, v0_K, lambda0, phi0, alpha0, chi0;

	/** Default coordinates for ProjectData. */
	public ProjectData() {
		delta_t = 0.01d;
		cancelTime = 10000;
		outIterations.add(new DoubleVector2(0, 1));
		outIterations.add(new DoubleVector2(300, 5));
		outIterations.add(new DoubleVector2(600, 10));
		outIterations.add(new DoubleVector2(1200, 30));
		outIterations.add(new DoubleVector2(3600, 60));
		t0 = 0.0; // initial time
		h0_E = 0; // height above WGS84 ellipsoid (E-system)
		h_O = 0; // initial height (O-system)
		v0_K = 0; // initial speed (K-system)
		// Munich airport
		startLati = 52.36307424663969d;
		startLong = 13.503999710083008d;
		// North-Korean launch cite
		// startLati = 39.66d;
		// startLong = 124.705d;
		lambda0 = Math.toRadians(startLong); // geodetic longitude
		phi0 = Math.toRadians(startLati); // geodetic latitude
		alpha0 = Math.toRadians(0.0d); // initial angle-of-attack (A=K)
		// flight direction --> reference to true north - 0deg=North, 90deg=East
		chi0 = Math.toRadians(0.0d);
	}

	public double getDelta_t() {
		return delta_t;
	}

	public void setDelta_t(double delta_t) {
		this.delta_t = delta_t;

		// this.setChanged();
		// this.notifyObservers();
	}

	public LinkedList<DoubleVector2> getOutIterations() {
		return outIterations;
	}

	public double getFirstOutIteration() {
		return outIterations.getFirst().getValue();
	}

	public double getOutIterationTimeStep(double time) {
		if (time >= outIterations.getLast().t) {
			return outIterations.getLast().value;
		}
		Iterator<DoubleVector2> iter = outIterations.iterator();
		DoubleVector2 last = iter.next();
		while (iter.hasNext()) {
			DoubleVector2 current = iter.next();
			if (current.t >= time) {
				break;
			}
			last = current;
		}
		return last.value;
	}

	public void setOutIteration(LinkedList<DoubleVector2> outIterations) {
		this.outIterations = outIterations;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(double cancelTime) {
		this.cancelTime = cancelTime;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getStartLong() {
		return startLong;
	}

	public void setStartLong(double startLong) {
		this.startLong = startLong;
		lambda0 = Math.toRadians(startLong);
		this.setChanged();
		this.notifyObservers(new LocationLongitudeChange());
	}

	public double getStartLati() {
		return startLati;
	}

	public void setStartLati(double startLati) {
		this.startLati = startLati;
		this.phi0 = Math.toRadians(startLati);
		this.setChanged();
		this.notifyObservers(new LocationLatitudeChange());
	}

	public double getT0() {
		return t0;
	}

	public void setT0(double t0) {
		this.t0 = t0;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getH0_E() {
		return h0_E;
	}

	public void setH0_E(double h0_E) {
		this.h0_E = h0_E;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getH_O() {
		return h_O;
	}

	public void setH_O(double newStartHeigt) {
		this.h_O = newStartHeigt;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getV0_K() {
		return v0_K;
	}

	public void setV0_K(double v0_K) {
		this.v0_K = v0_K;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getLambda0() {
		return lambda0;
	}

	public void setLambda0(double lambda0) {
		this.lambda0 = lambda0;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getPhi0() {
		return phi0;
	}

	public void setPhi0(double phi0) {
		this.phi0 = phi0;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getAlpha0() {
		return alpha0;
	}

	public void setAlpha0(double alpha0) {
		this.alpha0 = alpha0;

		// this.setChanged();
		// this.notifyObservers();
	}

	public double getChi0() {
		return chi0;
	}

	public void setChi0(double chi0) {
		this.chi0 = chi0;

		// this.setChanged();
		// this.notifyObservers();
	}
}