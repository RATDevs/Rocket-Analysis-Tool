package rat.main;

import java.io.IOException;

import rat.calculation.rocket.Rocket;
import rat.controller.dataIO.RocketXMLOutputAdapter;

/**
 * 
 * Test-Main to test the output of the XML-adapter and generate test files.
 * 
 */
public class RocketOutput {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RocketXMLOutputAdapter adapter = new RocketXMLOutputAdapter();

		Rocket rocket1 = new Rocket(1);
		Rocket rocket2 = new Rocket(2);
		Rocket rocket3 = new Rocket(3);

		String folder = "Rockets/";
		try {
			adapter.writeData(rocket1, folder + "rocket1.xml");
			adapter.writeData(rocket2, folder + "rocket2.xml");
			adapter.writeData(rocket3, folder + "rocket3.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done...");
	}

}
