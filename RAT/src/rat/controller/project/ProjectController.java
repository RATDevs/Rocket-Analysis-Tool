package rat.controller.project;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observer;

import rat.calculation.Project;
import rat.calculation.calculator.CalculationResult;
import rat.calculation.calculator.CalculationStepResult;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.rocket.Rocket;
import rat.controller.MainController;
import rat.controller.dataIO.ConfigManager;
import rat.controller.dataIO.KMLFileGenerator;
import rat.controller.dataIO.ProjectObjectIOAdapter;
import rat.controller.dataIO.TextFileGenerator;
import rat.controller.dataObjects.ImportantGraphsBean;
import rat.controller.objectStores.CalculatorStore;
import rat.controller.project.panelController.CalculateController;
import rat.controller.project.panelController.PlanetController;
import rat.controller.project.panelController.RocketController;
import rat.controller.project.panelController.SettingController;
import rat.gui.StaticVariables;
import rat.gui.panels.ProjectPanel;
import rat.gui.panels.tabPanels.TabPanelMap;
import rat.gui.panels.tabPanels.results.TabPanelGraph;
import rat.gui.panels.tabPanels.results.TabPanelResults;
import rat.language.GlobalConfig;

/**
 * Controller for one Project/Calculation.
 * 
 * @author Nils Vißmann
 * 
 */
public class ProjectController extends GeneralProjectController {

	private TabPanelResults resPan;
	private TabPanelMap mapPan;

	private ProjectPanel projectPanel;

	private PlanetController planetController;
	private SettingController settingsController;
	private RocketController rocketController;
	private CalculateController calculateController;
	private CalculatorStore calculatorStore;

	public ProjectController(MainController mainController) {
		super(mainController, new Project());

		initializeDefaultComponents(mainController);
	}

	public ProjectController(MainController mainController, Project project) {
		super(mainController, project);
		initializeComponents(mainController, project);
	}

	private void initializeDefaultComponents(MainController mainController) {
		// Create sub-Controllers for every part of the GUI
		this.planetController = new PlanetController(this.projectModel,
				mainController);
		this.rocketController = new RocketController(this.projectModel,
				mainController, this);
		this.settingsController = new SettingController(this.projectModel,
				mainController);
		this.calculateController = new CalculateController(this.projectModel,
				mainController, this);

		// Create Stores that do not belong to one special sub-Part of the GUI
		// this.calculatorStore = new
		// CalculatorStore(this.projectModel.getRocket(),
		// this.projectModel.getPlanet(), this.projectModel.getProjectData() );

		// Create Project-Panel
		this.projectPanel = new ProjectPanel(this);

		this.rocketController.disableDeleteButtons();
	}

	private void initializeComponents(MainController mainController,
			Project project) {
		// Create sub-Controllers for every part of the GUI
		this.planetController = new PlanetController(this.projectModel,
				mainController, project.getPlanet());
		this.rocketController = new RocketController(project, mainController,
				project.getRocket(), this);
		this.settingsController = new SettingController(project, mainController);
		this.calculateController = new CalculateController(this.projectModel,
				mainController, this);

		// Create Stores that do not belong to one special sub-Part of the GUI
		// this.calculatorStore = new CalculatorStore(project.getCalculator(),
		// this.projectModel.getRocket(), this.projectModel.getPlanet(),
		// this.projectModel.getProjectData());

		// Create Project-Panel
		this.projectPanel = new ProjectPanel(this);
	}

	public void displayResults() {
		// Refresh the Rocket-Stages in case a SIP was calculated:
		this.rocketController.updateRocket();

		// Enable the other results
		this.projectPanel.setResultsEnabled();

		resPan.setMaxSpeed(this.projectModel.getCalculationResult()
				.getMaxSpeed());
		resPan.setRange(this.projectModel.getCalculationResult().getRange());
		resPan.setMaxHeight(this.projectModel.getCalculationResult()
				.getMaxHeight());
		resPan.setDuration(this.projectModel.getCalculationResult()
				.getDuration());

		ImportantGraphsBean igb = new ImportantGraphsBean();
		// Ground Distance - Height
		igb.AddGraph(StaticVariables.AxisNames[2],
				StaticVariables.AxisNames[1], caluclateResultArray(GlobalConfig
						.getMessage("AxisGroundDistance")),
				caluclateResultArray(GlobalConfig.getMessage("AxisHeight")));
		// Time - Speed
		igb.AddGraph(StaticVariables.AxisNames[0],
				StaticVariables.AxisNames[3],
				caluclateResultArray(GlobalConfig.getMessage("AxisTime")),
				caluclateResultArray(GlobalConfig.getMessage("AxisSpeed")));
		// Time - Gamma
		igb.AddGraph(StaticVariables.AxisNames[0],
				StaticVariables.AxisNames[5],
				caluclateResultArray(GlobalConfig.getMessage("AxisTime")),
				caluclateResultArray(GlobalConfig.getMessage("AxisGamma")));
		// Time - Theta
		igb.AddGraph(StaticVariables.AxisNames[0],
				StaticVariables.AxisNames[4],
				caluclateResultArray(GlobalConfig.getMessage("AxisTime")),
				caluclateResultArray(GlobalConfig.getMessage("AxisTheta")));

		resPan.drawGraphs(igb);

		// draw map plot
		ArrayList<Double> longitude = new ArrayList<Double>();
		ArrayList<Double> latitudue = new ArrayList<Double>();
		projectModel.getCalculationResult().getPositionLists(longitude,
				latitudue);
		mapPan.paintTarget(longitude, latitudue);

		resPan.clearTable();

		fillResultTable();

		this.generateLastRunOutput();
	}

	/**
	 * This method generates the default output files of the last run.
	 * Generates: - readable .txt file - complete .csv file - default graphs
	 * images
	 */
	public void generateLastRunOutput() {

		ConfigManager.saveConfig(this.projectModel.data);

		String folder = "LastRun/";
		CalculationResult result = this.projectModel.getCalculationResult();
		File folderFile = new File(folder);

		if (!folderFile.exists()) {
			folderFile.mkdir();
		}

		try {
			KMLFileGenerator.generateKMLFile(new File(folder
					+ "lastRunGoogleEarth.kml"), this.projectModel);
			TextFileGenerator.generateCommaCSVFile(new File(folder
					+ "lastRun.Comma.csv"), result);
			TextFileGenerator.generateDotCSVFile(new File(folder
					+ "lastRun.Dot.csv"), result);
			TextFileGenerator.generateFormattedTextFile(new File(folder
					+ "lastRun.txt"), result);
		} catch (IOException e) {
			System.out
					.println("Not able to generate the last run output."
							+ " Probably another programm is blocking some of the files.");
		}
	}

	/**
	 * Fills the Table that shows the results
	 */
	private void fillResultTable() {
		CalculationResult res = this.projectModel.getCalculationResult();

		for (CalculationStepResult csr : res.getCalculationResult()) {
			DecimalFormat df = new DecimalFormat("0.000");
			String[] row = new String[9];
			row[0] = df.format(csr.getTime());
			row[1] = df.format(csr.getHeight() / 1000);
			row[2] = df.format(csr.getGroundDistance() / 1000);
			row[3] = df.format(csr.getSpeed());
			row[4] = df.format(csr.getThetaDegree());
			row[5] = df.format(csr.getGammaDegree());
			row[6] = df.format(csr.getAlphaDegree());
			row[7] = df.format(csr.getThrust() / 1000);
			row[8] = df.format(csr.getMass());

			resPan.addTableRow(row);
		}
	}

	/**
	 * Enables/Disables I/O
	 * 
	 * @param value
	 *            indicates enable(true) or disable(false)
	 */
	public void setAllEnabled(boolean b) {
		this.planetController.setEnabled(b);
		this.settingsController.setEnabled(b);
		this.rocketController.setEnabled(b);
		this.calculateController.setEnabled(b);
	}

	/**
	 * Shows an Graph-Tab
	 */
	public void showGraph(String i, String j) {
		double[] x_axsis_data = caluclateResultArray(GlobalConfig.getMessage(i));
		double[] y_axsis_data = caluclateResultArray(GlobalConfig.getMessage(j));

		this.projectPanel.createGraphTab(new String(GlobalConfig.getMessage(i)
				+ " / " + GlobalConfig.getMessage(j)), x_axsis_data, i,
				y_axsis_data, j);
	}

	private double[] caluclateResultArray(String name) {
		CalculationResult cr = this.projectModel.getCalculationResult();
		if (name.equals(GlobalConfig.getMessage("AxisTime")))
			return cr.getTimeArray();
		else if (name.equals(GlobalConfig.getMessage("AxisGroundDistance")))
			return cr.getGroundDistanceArray();
		else if (name.equals(GlobalConfig.getMessage("AxisSpeed")))
			return cr.getSpeedArray();
		else if (name.equals(GlobalConfig.getMessage("AxisHeight")))
			return cr.getHeightArray();
		else if (name.equals(GlobalConfig.getMessage("AxisTheta")))
			return cr.getThetaArray();
		else if (name.equals(GlobalConfig.getMessage("AxisGamma")))
			return cr.getGammaArray();
		else if (name.equals(GlobalConfig.getMessage("AxisAlpha")))
			return cr.getAlphaArray();
		else if (name.equals(GlobalConfig.getMessage("AxisThrust")))
			return cr.getThrustArray();
		else if (name.equals(GlobalConfig.getMessage("AxisMass")))
			return cr.getMassArray();
		else
			return null; // This case should never be reached in case the names
							// matched here are the same that are set in the
							// ComboboxModel in TabPanelResults.
	}

	public String[] getCalculatorNamesArray() {
		java.util.List<String> names = this.calculatorStore.getNames();
		String[] ret = new String[names.size()];

		for (int i = 0; i < names.size(); i++)
			ret[i] = names.get(i);

		return ret;
	}

	public void setNewCalculator(String calculatorName) {
		for (TrajectoryCalculator tjc : this.calculatorStore.getCalculators()) {
			if (tjc.name.equals(calculatorName)) {
				this.projectModel.setCalculator(tjc);
				return;
			}
		}
	}

	public void setMapPanel(TabPanelMap mp) {
		this.mapPan = mp;
	}

	public void setResultPanel(TabPanelResults tr) {
		this.resPan = tr;
	}

	public ProjectPanel getProjectPanel() {
		return this.projectPanel;
	}

	public PlanetController getPlanetController() {
		return this.planetController;
	}

	public SettingController getSettingController() {
		return this.settingsController;
	}

	public RocketController getRocketController() {
		return this.rocketController;
	}

	public CalculateController getCalculationController() {
		return this.calculateController;
	}

	public void getGraphValues(String axisnames, String axisnames2,
			TabPanelGraph tpg) {
		double[] x_axsis_data = caluclateResultArray(GlobalConfig
				.getMessage(axisnames));
		double[] y_axsis_data = caluclateResultArray(GlobalConfig
				.getMessage(axisnames2));
		tpg.includePlot(x_axsis_data, axisnames, y_axsis_data, axisnames2);
	}

	public void addObserverToData(Observer o) {
		this.projectModel.getProjectData().addObserver(o);
	}

	public double getLatitude() {
		return this.projectModel.getProjectData().getStartLati();
	}

	public double getLongitude() {
		return this.projectModel.getProjectData().getStartLong();
	}

	public void saveProject(File f) {
		ProjectObjectIOAdapter.saveProject(this.projectModel, f);
	}

	public void setNewCalculationRocket(Rocket m) {
		this.calculateController.setNewCalculationRocket(m);
	}

	public void updateLanguage() {
		projectPanel.updateLanguage();
	}

}
