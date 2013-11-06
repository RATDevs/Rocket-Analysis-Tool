package rat.controller.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rat.calculation.Project;
import rat.controller.MainController;
import rat.exceptions.InvalidDataException;

/**
 * Abstract Superclass for the panel controller. Holds common variables and
 * functions.
 * 
 * @author Nils Vißmann
 * 
 */
public abstract class GeneralProjectController {

	protected MainController mainController;
	protected Project projectModel;

	public GeneralProjectController(MainController mainController,
			Project project) {
		this.mainController = mainController;
		this.projectModel = project;
	}

	/* General Utility Functions */

	/**
	 * Checks if a given String contains a valid Double-Number
	 * 
	 * @param text
	 *            The String to check
	 * @return true if the given String can be converted to a Double
	 */
	public boolean checkDouble(String text) {
		try {
			Double.valueOf(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * * Checks if the parameter contains a valid out Iterations Vector
	 * 
	 * @param data
	 *            The Vector to Check. All String[] should be of the form [time,
	 *            value]
	 * 
	 * @return Returns a sorted List of double Arrays containing the ew
	 *         OutIterationsVektor
	 * 
	 * @throws InvalidOutIterationsException
	 *             If the data turns out to be an invalid OutIterationsVector,
	 *             we throw an Exception
	 */
	public List<Double[]> checkDoubleVector(List<String[]> data)
			throws InvalidDataException {
		// 1. Remove last entry, since it will always be [null, null] //TODO:
		// Eventually remove all last lines that are just nulls?
		boolean textLine;
		for (int i = 0; i < data.size(); i++) {
			textLine = false;
			for (int j = 0; j < data.get(i).length; j++) {
				if (!(data.get(i)[j] == null || data.get(i)[j].equals("")))
					textLine = true;
			}
			if (!textLine)
				data.remove(i--);
		}

		// 2. Check if there are always pairs of data. Except for the Last row,
		// which will always be [null, null]
		for (int i = 0; i < data.size(); i++) {
			try {
				String[] current = data.get(i);
				for (int j = 0; j < current.length; j++)
					data.get(i)[j].equals("null");
			} catch (NullPointerException e) {

				throw new InvalidDataException();
			}
		}

		// 3. Check if all Entries could be converted to Double
		List<Double[]> transformedData = new ArrayList<Double[]>();
		for (String[] e : data) {
			try {
				Double[] current = new Double[e.length];

				for (int i = 0; i < e.length; i++)
					current[i] = Double.valueOf(e[i]);

				transformedData.add(current);
			} catch (NumberFormatException ex) {
				throw new InvalidDataException();
			}
		}

		// 4.1 sort the transformed Data after its timesteps
		Collections.sort(transformedData, new Comparator<Double[]>() {

			@Override
			public int compare(Double[] o1, Double[] o2) {
				if (o1[0] < o2[0])
					return -1;
				else if (o1[0] == o2[0])
					return 0;
				else
					return 1;
			}
		});

		// 4.2 check, if there are doubled timesteps:
		for (int i = 0; i < transformedData.size() - 1; i++) {
			if (transformedData.get(i)[0] == transformedData.get(i + 1)[0])
				throw new InvalidDataException();
		}

		return transformedData;
	}

	/**
	 * Repacks the whole mainGUI
	 */
	public void repackFrame() {
		this.mainController.repackGUI();
	}
}
