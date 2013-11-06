package rat.controller.dataIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import rat.calculation.rocket.Rocket;

/**
 * Class to load all Rockets from files in a directory
 * 
 * @author Nils Vißmann
 * 
 */
public class RocketLoader {

	// The directory containing the Rocket-XML-Files.
	private static final String folder = "Rockets";

	/**
	 * Loads the Rocket from the default Rocket-directory
	 * 
	 * @return A list of all Rocket-Objects that were created.
	 * 
	 * @throws FileNotFoundException
	 *             Thrown if the directory does not exist.
	 */
	public static List<Rocket> loadRockets() throws FileNotFoundException {
		List<Rocket> ret = new ArrayList<Rocket>();

		File rocketFolder = new File(folder);

		// Checks if the file exists and if it is a folder

		if (!rocketFolder.exists() || !rocketFolder.isDirectory())
			throw new FileNotFoundException("There is no \"Rocket\" folder");

		for (File f : rocketFolder.listFiles()) {
			try {
				Rocket rocket = RocketXMLInputAdapter.getRocketFromFile(f
						.getAbsolutePath());
				if (rocket != null)
					ret.add(rocket);
			} catch (Exception e) {
				// TODO: low priority - overwrite with version check maybe
				System.out.println("Failed loading file " + f.getPath()
						+ "\nFile brocken.");
			}
		}
		return ret;
	}

}
