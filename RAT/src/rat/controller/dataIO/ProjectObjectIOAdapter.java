package rat.controller.dataIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import rat.calculation.Project;

/**
 * Class to serialize whole Projects
 * 
 * @author Nils Vißmann
 * 
 */
public class ProjectObjectIOAdapter {

	/**
	 * Saves a given Project-Object to the given File-Object
	 * 
	 * @param proj
	 *            The project to save
	 * @param f
	 *            The file-Object the project should be saved to
	 */
	public static void saveProject(Project proj, File f) {

		if (f.exists()) {
			if (!f.canWrite())
				JOptionPane.showMessageDialog(null, "Cannot write to file",
						"Warning", JOptionPane.WARNING_MESSAGE);
		}

		FileOutputStream fos;
		ObjectOutputStream oos;

		try {
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(proj);
			oos.flush();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}

	/**
	 * Loads a project from a file
	 * 
	 * @param f
	 *            The file the project should be loaded from
	 * 
	 * @return Returns a project or null if something went wrong.
	 */
	public static Project loadProject(File f) {
		Project ret = null;
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);

			ret = (Project) ois.readObject();

			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Could not find File to read from");
		} catch (IOException e) {
			System.out.println("[ERROR] Output went wrong");
		} catch (ClassNotFoundException e) {
			System.out.println("[ERROR] False File");
		}

		return ret;
	}

}
