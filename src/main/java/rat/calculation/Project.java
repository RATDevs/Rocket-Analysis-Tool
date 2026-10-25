package rat.calculation;

//IntTest
import java.io.Serializable;
import java.util.Observable;

import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.calculator.calculators.ForwardEuler;
import rat.calculation.planet.Planet;
import rat.calculation.rocket.Rocket;
import rat.controller.updateObjects.FullRocketChange;

/** @author Gerhard Mesch */

@SuppressWarnings("serial")
public class Project extends Observable implements Serializable {

	// INPUT
	public ProjectData data;
	public Planet planet;
	public Rocket rocket;

	// calculator
	private TrajectoryCalculator calculator;

	public Project() {
		data = new ProjectData();
		planet = new Planet(); // earth

		// dummy work around to prevent exception at startup phase
		calculator = new ForwardEuler(new Rocket(1), planet, data);
		// calculator = new RungeKutta(rocket, planet, data);
	}

	public void doCalculation() {
		Thread thread = new Thread(calculator);
		thread.start();
	}

	/**
	 * Returns the result of the last run of the calculation.
	 * 
	 * @return CalculationResult
	 */
	public CalculationResult getCalculationResult() {
		return calculator.getCalculationResult();
	}

	public ProjectData getProjectData() {
		return this.data;
	}

	public Rocket getRocket() {
		return this.rocket;
	}

	public Planet getPlanet() {
		return this.planet;
	}

	public TrajectoryCalculator getCalculator() {
		return this.calculator;
	}

	public void setRocket(Rocket rocket) {

		this.rocket = rocket;
		calculator.setRocket(rocket);
		this.setChanged();
		this.notifyObservers(new FullRocketChange());
	}

	public void setCalculator(TrajectoryCalculator c) {
		this.calculator = c;
	}
}
