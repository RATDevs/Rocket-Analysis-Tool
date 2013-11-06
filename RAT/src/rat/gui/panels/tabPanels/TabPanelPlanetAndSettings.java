package rat.gui.panels.tabPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rat.controller.project.panelController.PlanetController;
import rat.controller.project.panelController.SettingController;
import rat.gui.panels.tabPanels.values.PlanetPanel;
import rat.gui.panels.tabPanels.values.ProjectSettingsPanel;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class TabPanelPlanetAndSettings extends JPanel {

	private PlanetPanel planetPanel;
	private ProjectSettingsPanel generalPanel;

	private PlanetController planetController;
	private SettingController settingsController;

	public TabPanelPlanetAndSettings(PlanetController pc, SettingController sc) {

		this.planetController = pc;
		this.settingsController = sc;

		this.initComponents();
		this.updateValues();
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

		// init Planet_Panel
		planetPanel = this.planetController.getPlanetPanel();
		gbc.weightx = 0.9;
		gbc.weighty = 0.9;
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(planetPanel, gbc);

		// init General_Panel
		generalPanel = this.settingsController.getSettingsPanel();
		gbc.gridx = 3;
		this.add(generalPanel, gbc);
	}

	private void updateValues() {
		this.planetController.updateValues();
		this.settingsController.updateValues();
	}

	public void updateLanguage() {
		planetPanel.updateLanguage();
		generalPanel.updateLanguage();
	}
}