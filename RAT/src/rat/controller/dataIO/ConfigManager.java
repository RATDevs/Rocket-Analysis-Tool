package rat.controller.dataIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import rat.calculation.ProjectData;

/**
 * 
 * Manages all save and load operations for the config file
 * 
 * @author Nils Vißmann
 * 
 */
public class ConfigManager {

	private static final String configPath = "config/config.txt";
	private static Properties property = null;

	/**
	 * saves the current configuration to the config-File
	 */
	public static void saveConfig(ProjectData data) {

		if (property == null) {
			property = new Properties();
		}

		try {
			saveCoordinates(data.getStartLong(), data.getStartLati(),
					Math.toDegrees(data.getChi0()));
		} catch (IOException e) {
			System.out.println("[ERROR] could not write to config file");
		}

	}

	/**
	 * Writes the current starting-Position into the config-file.
	 * 
	 * @throws IOException
	 */
	private static void saveCoordinates(double longitude, double latitude,
			double direction) throws IOException {
		property.setProperty("startLongitude", Double.toString(longitude));
		property.setProperty("startLatitude", Double.toString(latitude));
		property.setProperty("startDirection", Double.toString(direction));

		FileOutputStream fos = new FileOutputStream(new File(configPath));
		property.store(fos, null);
		fos.close();
	}

	/**
	 * Loads the saved settings into the projectData parameter
	 * 
	 * @param data
	 *            Object to store the configuration.
	 */
	public static void loadConfig(ProjectData data) {
		property = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(configPath));
			property.load(fis);
			fis.close();

			loadCoordinates(data);
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Could not find config file");
		} catch (IOException e) {
			System.out.println("[ERROR] Could not load config file");
		}
	}

	/**
	 * Loads the starting coordinates into the ProjectData parameter
	 * 
	 * @param data
	 */
	private static void loadCoordinates(ProjectData data) {
		try {
			data.setStartLong(Double.valueOf((String) property
					.get("startLongitude")));
			data.setStartLati(Double.valueOf((String) property
					.get("startLatitude")));
			data.setChi0(Math.toRadians(Double.valueOf((String) property
					.get("startDirection"))));
		} catch (NullPointerException e) {
			// Do nothing. The standard coordinates are used.
		}
	}

}
