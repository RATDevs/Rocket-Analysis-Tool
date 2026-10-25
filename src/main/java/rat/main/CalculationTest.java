package rat.main;

import rat.calculation.Project;
import rat.calculation.rocket.Rocket;

/**
 * Test-Main to test the calculation without GUI.
 * 
 */
public class CalculationTest {

	public CalculationTest() {

	}

	/** @param args */
	public static void main(String[] args) {
		Project testProject = new Project();
		testProject.setRocket(new Rocket(1));
		testProject.doCalculation();
		System.out
				.println("Time, Distance, Height, Velocity, Theta, Gamma, Alpha, Thrust, Mass\n");
	}
}
