package rat.controller.project.panelController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rat.calculation.Project;
import rat.calculation.ProjectData;
import rat.calculation.math.DoubleVector2;
import rat.controller.MainController;
import rat.controller.dataIO.ConfigManager;
import rat.controller.dataObjects.GeneralValueBean;
import rat.controller.project.GeneralProjectController;
import rat.gui.panels.tabPanels.values.ProjectSettingsPanel;

/**
 * Controller for the General Value Panel.
 * 
 * @author Nils Vißmann
 * 
 */
public class SettingController extends GeneralProjectController {

	private ProjectSettingsPanel settingsPanel;

	public SettingController(Project project, MainController mainController2) {
		super(mainController2, project);
		this.settingsPanel = new ProjectSettingsPanel(this);

		this.loadSettings();
	}

	private void loadSettings() {
		ConfigManager.loadConfig(this.projectModel.data);
	}

	public void updateValues() {
		ProjectData data = projectModel.getProjectData();

		GeneralValueBean gvb = new GeneralValueBean(data.getDelta_t(),
				data.getCancelTime(), data.getOutIterationTimeStep(0),
				data.getT0(), data.getH0_E(), data.getH_O(), data.getV0_K(),
				data.getStartLong(), data.getStartLati(), data.getAlpha0(),
				data.getChi0());

		settingsPanel.updateValues(gvb);

	}

	public List<String[]> getOutIterationList() {
		List<String[]> iterations = new ArrayList<String[]>();

		for (DoubleVector2 dv : this.projectModel.getProjectData()
				.getOutIterations()) {
			iterations.add(new String[] { Double.toString(dv.getTime()),
					Double.toString(dv.getValue()) });
		}

		return iterations;
	}

	public ProjectSettingsPanel getSettingsPanel() {
		return this.settingsPanel;
	}

	public void setEnabled(boolean b) {
		settingsPanel.setEnabled(b);
	}

	public void setNewTimeStep(String text) {
		double timeStep = Double.valueOf(text);
		this.projectModel.getProjectData().setDelta_t(timeStep);
	}

	public void setNewCancelTime(String text) {
		double cancleTime = Double.valueOf(text);
		this.projectModel.getProjectData().setCancelTime(cancleTime);
	}

	public void setNewOutIterations(List<Double[]> validVector) {
		LinkedList<DoubleVector2> outItVector = new LinkedList<DoubleVector2>();
		for (Double[] a : validVector) {
			outItVector.add(new DoubleVector2(a[0], a[1]));
		}

		this.projectModel.getProjectData().setOutIteration(outItVector);
		this.updateValues();
	}

	public void setNewStartTime(String text) {
		double startTime = Double.valueOf(text);
		this.projectModel.getProjectData().setT0(startTime);
	}

	public void setNewHeightAboveEllipse(String text) {
		double heightEllipse = Double.valueOf(text);
		this.projectModel.getProjectData().setH0_E(heightEllipse);
	}

	public void setNewStartHeight(String text) {
		double height = Double.valueOf(text);
		this.projectModel.getProjectData().setH_O(height);
	}

	public void setNewSpeed(String text) {
		double speed = Double.valueOf(text);
		this.projectModel.getProjectData().setV0_K(speed);
	}

	public void setNewLongitude(String text) {
		double longitude = Double.valueOf(text);
		this.projectModel.getProjectData().setStartLong(longitude);
	}

	public void setNewLatitude(String text) {
		double latitude = Double.valueOf(text);
		this.projectModel.getProjectData().setStartLati(latitude);
	}

	public void setNewAlpha(String text) {
		double alpha = Double.valueOf(text);
		this.projectModel.getProjectData().setAlpha0(Math.toRadians(alpha));
	}

	public void setNewDirection(String text) {
		double direction = Double.valueOf(text);
		this.projectModel.getProjectData().setChi0(Math.toRadians(direction));
	}
}
