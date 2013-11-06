package rat.gui.panels.tabPanels.values.rocket;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import rat.controller.project.panelController.RocketController;

/**
 * @author Manuel Schmidt, Nils Vißmann
 */
@SuppressWarnings("serial")
public class StagesPanel extends JPanel {

	private RocketController controller;

	private List<SingleStagePanel> stages;

	public StagesPanel(RocketController projCont) {
		this.controller = projCont;

		this.stages = new ArrayList<SingleStagePanel>();

		this.setLayout(new GridBagLayout());
	}

	@Override
	public void setEnabled(boolean b) {
		for (SingleStagePanel ssp : stages)
			ssp.setEnabled(b);
	}

	public void updateStagesPanel() {
		this.displayStages();
	}

	public void displayStages() {
		this.removeStages();
		this.fillStagesList();

		GridBagConstraints sspConstraint = new GridBagConstraints();
		sspConstraint.fill = GridBagConstraints.BOTH;
		sspConstraint.weightx = 1;
		sspConstraint.weighty = 1;
		sspConstraint.gridy = 0;

		for (SingleStagePanel ssp : stages) {
			this.add(ssp, sspConstraint);
		}
	}

	private void removeStages() {
		this.removeAll();
		this.stages.clear();
	}

	private void fillStagesList() {
		this.stages.add(new SingleStagePanel(null, this.controller, -1, null));

		for (int index = 0; index < controller.getRocketStagesList().size(); index++) {
			this.stages.add(new SingleStagePanel(new String("" + (index + 1)),
					this.controller, index, controller.getRocketStagesList()
							.get(index)));
		}
	}

	public void disableAeroDataDelete() {
		this.stages.get(0).disableAeroDataDelete();
	}

	public void enableAeroDataDelete() {
		this.stages.get(0).enableAeroDataDelete();
	}

	/**
	 * Case of Changing Language
	 */
	public void updateLanguage() {
		for (SingleStagePanel ssp : stages) {
			ssp.updateLanguage();
		}
	}
}
