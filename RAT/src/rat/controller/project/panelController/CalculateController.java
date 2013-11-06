package rat.controller.project.panelController;

import java.util.List;

import rat.calculation.Project;
import rat.calculation.calculator.TrajectoryCalculator;
import rat.calculation.rocket.Rocket;
import rat.controller.MainController;
import rat.controller.objectStores.CalculatorStore;
import rat.controller.project.GeneralProjectController;
import rat.controller.project.ProjectController;
import rat.gui.panels.tabPanels.values.CalculationPanel;

/**
 * Controller for the Calculation Panel.
 * 
 * @author Nils Vißmann
 * 
 */
public class CalculateController extends GeneralProjectController {

	private CalculationPanel calculationPanel;
	private ProjectController projectController;

	private CalculatorStore calculatorStore;

	public CalculateController(Project project, MainController mainController2,
			ProjectController projectController) {
		super(mainController2, project);

		this.projectController = projectController;
		this.calculatorStore = new CalculatorStore(
				this.projectModel.getRocket(), this.projectModel.getPlanet(),
				this.projectModel.getProjectData());
		this.projectModel.setCalculator(calculatorStore.getDefaultCalculator());

		this.calculationPanel = new CalculationPanel(this);
	}

	public void setEnabled(boolean b) {
		calculationPanel.setEnabled(b);
	}

	/**
	 * Method that is invoked when the calculation Button is pressed. Disables
	 * all Input to the Project and starts the Calculation
	 */
	public void calculate() {
		projectController.setAllEnabled(false);
		this.projectModel.doCalculation();

		Thread wt = new Thread(new waitingThread());
		wt.start();
	}

	public CalculationPanel getCalculationPanel() {
		return this.calculationPanel;
	}

	/**
	 * Waiting thread that waits for the Calculator to finish it's calculation
	 * After the Calculator finished, the input is enabled again and the Results
	 * are shown
	 */
	private class waitingThread implements Runnable {
		private static final int waitIntervall = 1000;

		public void run() {
			while (!projectModel.getCalculator().isFinished()) {
				try {
					Thread.sleep(waitIntervall);
				} catch (InterruptedException e) {
				}
			}
			projectController.setAllEnabled(true);
			projectController.displayResults();
			projectController.generateLastRunOutput();
		}
	}

	public void setNewCalculationMode(String modeName) {
		List<TrajectoryCalculator> calculators = calculatorStore
				.getCalculators();

		for (TrajectoryCalculator c : calculators) {
			if (c.name.equals(modeName)) {
				this.projectModel.setCalculator(c);
				return;
			}
		}

	}

	public String getCurrentCalculatorName() {
		return this.projectModel.getCalculator().name;
	}

	public String[] getCalculatorNamesArray() {
		List<TrajectoryCalculator> calculators = calculatorStore
				.getCalculators();
		String[] names = new String[calculators.size()];

		for (int i = 0; i < calculators.size(); i++)
			names[i] = calculators.get(i).name;

		return names;
	}

	public void setNewCalculationRocket(Rocket m) {
		this.calculatorStore.setNewRocket(m);
	}
}
