package rat.controller.dataIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;

public class ResultCSVOutputAdapter implements IDataOutputAdapter {

	private final String CATEGORIES = "Time,Mass,Theta,Alpha,Gamma,speed_K,r_E,r_I,SurfPositionVector,WGS84_0,GroundDistance,Thrust_B,MachNumber";

	@Override
	public void writeData(Object Data, String path) throws IOException {
		/* Sanity checks first. If it really is a rocket and if the path is a
		 * valid one */
		if (!(Data instanceof CalculationResult))
			throw new ClassCastException("Object is no Rocket");

		if (!isPathOK(path))
			throw new FileNotFoundException("Cannot save to that Path");

		CalculationResult res = (CalculationResult) Data;
		File outputCSV = new File(path);
		FileWriter fw = new FileWriter(outputCSV);
		fw.write(CATEGORIES);

		// Write platform-independent line-seperator:
		fw.write(System.getProperty("line.separator"));

		for (CalculationStepResult step : res.getCalculationResult()) {

			// Create line-String
			String line = "";
			// TODO: Schon ma was von String buffer gehört
			line += Double.toString(step.time) + ",";

			line += Double.toString(step.mass) + ",";

			line += Double.toString(step.theta) + ",";

			line += Double.toString(step.alpha) + ",";
			line += Double.toString(step.gamma) + ",";

			line += Double.toString(step.speed_K.x) + ";"
					+ Double.toString(step.speed_K.y) + ";"
					+ Double.toString(step.speed_K.z) + ",";

			line += Double.toString(step.r_E.x) + ";"
					+ Double.toString(step.r_E.y) + ";"
					+ Double.toString(step.r_E.z) + ",";
			line += Double.toString(step.r_I.x) + ";"
					+ Double.toString(step.r_I.y) + ";"
					+ Double.toString(step.r_I.z) + ",";

			line += Double.toString(step.surfPosiVector.x) + ";"
					+ Double.toString(step.surfPosiVector.y) + ";"
					+ Double.toString(step.surfPosiVector.z) + ",";

			line += Double.toString(step.WGS84_O.x) + ";"
					+ Double.toString(step.WGS84_O.y) + ";"
					+ Double.toString(step.WGS84_O.z) + ",";

			line += Double.toString(step.groundDistance) + ",";

			line += Double.toString(step.thrust_B) + ",";

			line += Double.toString(step.machNumber);

			System.out.println("line: " + line);
			fw.write(line);
			fw.write(System.getProperty("line.separator"));

			fw.flush();
			fw.close();
		}

	}

	/**
	 * Check if the
	 * 
	 * @param path
	 *            is one where we can write our File.
	 * 
	 * @return
	 */
	private boolean isPathOK(String path) {
		// TODO
		return true;
	}
}
