package rat.calculation.calculator;

import java.io.Serializable;

import rat.calculation.ProjectData;
import rat.calculation.planet.Planet;
import rat.calculation.rocket.Rocket;

/**
 * Abstract class for inheritance for new calculation steps. Encapsulates all
 * the data for the calculation. *
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public abstract class TrajectoryCalculator implements Runnable, Serializable {

	public final String name;
	protected boolean finished = false;

	// INPUT
	protected Rocket rocket;
	protected Planet planet;
	protected ProjectData inputData;

	// OUTPUT
	protected CalculationResult calculationResult;

	/**
	 * Super constructor for inheritance for new calculators.
	 * 
	 * @param rocket
	 *            Reference to the Rocket.
	 * @param planet
	 *            Reference to the Planet.
	 * @param data
	 *            Reference to the Data Input for calculation.
	 */
	protected TrajectoryCalculator(Rocket rocket, Planet planet,
			ProjectData data, String name) {
		// set input
		this.rocket = rocket;
		this.planet = planet;
		this.inputData = data;
		this.name = name;

		// generate Output object
		calculationResult = new CalculationResult();
	}

	/**
	 * @return the rocket
	 */
	public Rocket getRocket() {
		return rocket;
	}

	/**
	 * @param rocket
	 *            the rocket to set
	 */
	public void setRocket(Rocket rocket) {
		this.rocket = rocket;
	}

	/**
	 * @return the planet
	 */
	public Planet getPlanet() {
		return planet;
	}

	/**
	 * @param planet
	 *            the planet to set
	 */
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	/**
	 * @return the inputData
	 */
	public ProjectData getInputData() {
		return inputData;
	}

	/**
	 * @param inputData
	 *            the inputData to set
	 */
	public void setInputData(ProjectData inputData) {
		this.inputData = inputData;
	}

	/**
	 * @return the calculationResult
	 */
	public CalculationResult getCalculationResult() {
		return calculationResult;
	}

	/**
	 * @param calculationResult
	 *            the calculationResult to set
	 */
	public void setCalculationResult(CalculationResult calculationResult) {
		this.calculationResult = calculationResult;
	}

	public boolean isFinished() {
		return this.finished;
	}
}
