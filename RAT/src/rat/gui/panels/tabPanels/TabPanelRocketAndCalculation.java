package rat.gui.panels.tabPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rat.controller.project.panelController.CalculateController;
import rat.controller.project.panelController.RocketController;
import rat.gui.panels.tabPanels.values.CalculationPanel;
import rat.gui.panels.tabPanels.values.RocketPanel;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class TabPanelRocketAndCalculation extends JPanel {

	private RocketPanel rocketPanel;
	private CalculationPanel calculationPanel;

	private RocketController rocketController;
	private CalculateController calculationController;

	public TabPanelRocketAndCalculation(RocketController rc,
			CalculateController cc) {
		this.rocketController = rc;
		this.calculationController = cc;

		this.initComponents();

		this.rocketController.updateRocket();
	}

	private void initComponents() {
		this.setLayout(new GridBagLayout());

		this.initLabels();
		this.initPanels();
	}

	private void initLabels() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		// space
		JLabel space = new JLabel();

		gbc.weightx = 0.02;
		gbc.weighty = 0.02;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				space = new JLabel();
				gbc.gridx = i;
				gbc.gridy = j;
				this.add(space, gbc);
			}
		}
	}

	private void initPanels() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		// Init Rocket_Panel
		this.rocketPanel = this.rocketController.getRocketPanel();
		gbc.weightx = 0.85;
		gbc.weighty = 0.9;
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(this.rocketPanel, gbc);

		// Init CalculationPanel
		this.calculationPanel = this.calculationController
				.getCalculationPanel();
		gbc.weightx = 0.15;
		gbc.weighty = 0.9;
		gbc.gridx = 3;
		this.add(this.calculationPanel, gbc);
	}

	public void updateLanguage() {
		rocketPanel.updateLanguage();
		calculationPanel.updateLanguage();
	}
}
