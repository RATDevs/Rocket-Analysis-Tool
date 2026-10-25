package rat.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import rat.calculation.Project;
import rat.controller.dataIO.ProjectObjectIOAdapter;
import rat.controller.project.ProjectController;
import rat.exceptions.noProjectSelectedException;
import rat.gui.frames.MainGUI;
import rat.gui.panels.ProjectPanel;
import rat.language.GlobalConfig;

/**
 * Overall Controller for the whole application
 * 
 * @author Nils Vißmann
 * 
 */
public class MainController {

	private MainGUI mainGUI;
	private List<ProjectController> pcs;

	/**
	 * Initializes the MainGUI
	 */
	public void initialize() {
		pcs = new ArrayList<ProjectController>();
		// Initialize mainGui
		this.mainGUI = new MainGUI(this);
		this.createNewCalculation();
		this.repackGUI();
	}

	private void createNewCalculation() {
		ProjectController pc = new ProjectController(this);
		pcs.add(pc);
		this.mainGUI.createCalculationTab(pc);
	}

	public void addNewCalculation() {
		ProjectController pc = new ProjectController(this);
		pcs.add(pc);
		this.mainGUI.addCalculation(pc);
	}

	public void addNewCalculation(Project p) {
		ProjectController pc = new ProjectController(this, p);
		pcs.add(pc);
		this.mainGUI.addCalculation(pc);
	}

	/**
	 * Repacks the whole mainGUI
	 */
	public void repackGUI() {
		this.mainGUI.repack();
	}

	public void removeSelectedCalculation() {
		try {
			mainGUI.removeCalculationTab(mainGUI.getSelectedProjectPanel());
		} catch (noProjectSelectedException e) {
			// Do nothing. The "+" Tab is currently selected
		}
	}

	@SuppressWarnings("serial")
	public void saveSelectedCalculation() {

		try {
			ProjectPanel pp = mainGUI.getSelectedProjectPanel();

			JFileChooser fc = new JFileChooser() {
				@Override
				public void approveSelection() {
					File f = getSelectedFile();
					if (f.exists() && getDialogType() == SAVE_DIALOG) {
						int result = JOptionPane.showConfirmDialog(this,
								GlobalConfig
										.getMessage("ExisitngFileFrameText"),
								GlobalConfig
										.getMessage("ExistingFileFrameTitle"),
								JOptionPane.YES_NO_CANCEL_OPTION);
						switch (result) {
						case JOptionPane.YES_OPTION:
							super.approveSelection();
							return;
						case JOptionPane.NO_OPTION:
							return;
						case JOptionPane.CLOSED_OPTION:
							return;
						case JOptionPane.CANCEL_OPTION:
							cancelSelection();
							return;
						}
					}

					super.approveSelection();
				}
			};

			int ret = fc.showSaveDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				pp.getProjectController().saveProject(f);
			}

		} catch (noProjectSelectedException e) {
			// Do nothing. +-Tab is selected
		}

	}

	public void askForClose() {
		String[] options = { GlobalConfig.getMessage("CloseProgramClose"),
				GlobalConfig.getMessage("CloseProgramDontClose") };
		int overWriteReturn = JOptionPane.showOptionDialog(null,
				GlobalConfig.getMessage("CloseProgramText"),
				GlobalConfig.getMessage("CloseProgramTitle"),
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
		if (overWriteReturn == 0)
			System.exit(0);
	}

	public void loadCalculation() {
		JFileChooser fc = new JFileChooser();
		int ret = fc.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			Project p = ProjectObjectIOAdapter.loadProject(f);
			addNewCalculation(p);
		}
	}

	public void updateLanguage() {
		mainGUI.updateLanguage();
		for (ProjectController pc : pcs)
			pc.updateLanguage();
	}
}
