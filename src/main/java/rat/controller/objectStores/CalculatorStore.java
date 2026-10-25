package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.List;

import rat.calculation.ProjectData;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.calculator.calculators.ForwardEuler;
import rat.calculation.calculator.calculators.RungeKutta;
import rat.calculation.planet.Planet;
import rat.calculation.rocket.Rocket;

/**
 * Stores all the Calculator-Objects of a Project
 * 
 * @author Nils Vißmann
 */
public class CalculatorStore {

	private List<TrajectoryCalculator> calculators;

	public CalculatorStore(Rocket r, Planet p, ProjectData d) {
		this.calculators = new ArrayList<TrajectoryCalculator>();
		this.calculators.add(new RungeKutta(r, p, d));
		this.calculators.add(new ForwardEuler(r, p, d));
	}

	public CalculatorStore(TrajectoryCalculator calculator, Rocket rocket,
			Planet planet, ProjectData projectData) {
		this.calculators = new ArrayList<TrajectoryCalculator>();

		this.calculators.add(calculator);
		if (!(calculator instanceof RungeKutta))
			this.calculators.add(new RungeKutta(rocket, planet, projectData));
		if (!(calculator instanceof ForwardEuler))
			this.calculators.add(new ForwardEuler(rocket, planet, projectData));
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();
		for (TrajectoryCalculator tj : calculators)
			ret.add(tj.name);

		return ret;
	}

	public List<TrajectoryCalculator> getCalculators() {
		return this.calculators;
	}

	public TrajectoryCalculator getDefaultCalculator() {
		return calculators.get(0); // return forward euler as default calculator
	}

	public void setNewRocket(Rocket m) {
		for (TrajectoryCalculator c : calculators)
			c.setRocket(m);
	}

}
