package rat.gui.panels.tabPanels.values;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import rat.controller.project.panelController.CalculateController;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class CalculationPanel extends JPanel {

	private CalculateController controller;

	private JComboBox<String> box;
	private JButton commit;

	public CalculationPanel(CalculateController projectController) {
		this.controller = projectController;

		this.setLayout(new GridBagLayout());
		initComponents();
		setComboBoxModels();
	}

	private void initComponents() {
		int count_y = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new CalculationListener());
		box = comboBox;
		gbc.gridx = 0;
		gbc.gridy = count_y++;
		gbc.gridwidth = 3;
		add(comboBox, gbc);

		JLabel space = new JLabel(" ");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = count_y++;
		gbc.weightx = 0.5;
		add(space, gbc);

		commit = new JButton(
				GlobalConfig.getMessage("StartCalculationButtonTitle"));
		commit.setToolTipText(GlobalConfig
				.getMessage("StartCalculationButtonToolTip"));
		commit.addActionListener(new CalculateHandler());
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = count_y++;
		gbc.gridwidth = 3;
		gbc.ipady = 40;
		add(commit, gbc);

		space = new JLabel(" ");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		add(space, gbc);

	}

	private void setComboBoxModels() {
		MutableComboBoxModel<String> calcModel = new DefaultComboBoxModel<String>(
				controller.getCalculatorNamesArray());
		calcModel.setSelectedItem(controller.getCurrentCalculatorName());
		box.setModel(calcModel);
	}

	private class CalculateHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.calculate();
		}

	}

	private class CalculationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modeName = (String) cb.getSelectedItem();

			controller.setNewCalculationMode(modeName);
		}
	}

	public void updateLanguage() {
		commit.setText(GlobalConfig.getMessage("StartCalculationButtonTitle"));
		commit.setToolTipText(GlobalConfig
				.getMessage("StartCalculationButtonToolTip"));
	}

}
