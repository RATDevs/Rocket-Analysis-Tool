package rat.controller.dataIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;

/**
 * Class for textfile output.
 * 
 * @author Gerhard Mesch
 * 
 */

public class TextFileGenerator {

	/**
	 * Writes a readable formated text file in the given file.
	 * 
	 * @param file
	 *            - File location
	 * @throws IOException
	 */
	public static void generateFormattedTextFile(File file,
			CalculationResult result) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write(CalculationStepResult.toFormatedStringRowDescription());
		writer.newLine();
		java.util.Iterator<CalculationStepResult> iter = result
				.getCalculationResult().iterator();
		while (iter.hasNext()) {
			CalculationStepResult step = iter.next();
			writer.write(step.getFormatedStringWithUnits());
			writer.newLine();
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * Writes all values in a matching .csv file. Decimal seperator - Comma
	 * 
	 * @param file
	 *            - File location
	 * @throws IOException
	 */
	public static void generateCommaCSVFile(File file, CalculationResult result)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write(CalculationStepResult.toStringRowDescription());
		writer.newLine();
		for (CalculationStepResult step : result.getCalculationResult()) {
			writer.write(step.toCommaString());
			writer.newLine();
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * Writes all values in a matching .csv file. Decimal seperator - dot
	 * 
	 * @param file
	 *            - File location
	 * @throws IOException
	 */
	public static void generateDotCSVFile(File file, CalculationResult result)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write(CalculationStepResult.toStringRowDescription());
		writer.newLine();
		for (CalculationStepResult step : result.getCalculationResult()) {
			writer.write(step.toString());
			writer.newLine();
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}
}
