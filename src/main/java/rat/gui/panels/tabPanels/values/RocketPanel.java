package rat.gui.panels.tabPanels.values;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import rat.controller.project.panelController.RocketController;
import rat.controller.updateObjects.FullRocketChange;
import rat.gui.frame.dataInputFrame.CreateNewThetaDataListFrame;
import rat.gui.panels.tabPanels.values.rocket.StagesPanel;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class RocketPanel extends JPanel implements Observer {

	private static final int NAME_DROPDOWN = 0;
	private static final int AERO_CALCULATION_MODE_DROPDOWN = 1;
	private static final int THETA_DATA_DROPDOWN = 2;
	private static final int THETA_CALCULATION_MODE_DROPDOWN = 3;

	private List<JButton> buttons;
	private List<JComboBox<String>> comboBoxs;
	private List<JLabel> labels;
	private JTextArea textArea;
	private StagesPanel stagesPanel;
	private JTextField rocketMass;

	RocketController controller;

	public RocketPanel(RocketController projectController) {
		this.controller = projectController;

		buttons = new ArrayList<JButton>();
		comboBoxs = new ArrayList<JComboBox<String>>();
		labels = new ArrayList<JLabel>();

		setLayout(new GridBagLayout());
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		initComponents();

		updateTitles();
	}

	/**
	 * sets the absolut Mass
	 */
	public void setAbsoluteMass() {
		this.rocketMass.setText(Double.toString(this.controller
				.getAbsoluteRocketMass()));
	}

	public void setEnabled(boolean b) {

		for (JButton bt : buttons)
			bt.setEnabled(b);

		for (JComboBox<String> cb : comboBoxs)
			cb.setEnabled(b);
		textArea.setEnabled(b);
		stagesPanel.setEnabled(b);
	}

	private void initComponents() {
		int y_counter = 0;
		y_counter = initTop(y_counter);
		y_counter = initStages(y_counter);
		y_counter = initDropDown(y_counter);
		y_counter = initBottom(y_counter);
		this.setAbsoluteMass();
	}

	public void updateValues() {
		stagesPanel.updateStagesPanel();

		this.textArea.setText(controller.getDescription());

		this.setComboBoxModels();

		controller.disableDeleteButtons();
	}

	/**
	 * inits all JComponents over the Stages
	 * 
	 * @param y
	 *            -Axis counter for GBC
	 * @return y-Axis counter for GBC
	 */
	private int initTop(int y_counter) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		// Title
		JLabel name = new JLabel(GlobalConfig.getMessage("RocketPanelTitle"));
		labels.add(name);
		name.setFont(name.getFont().deriveFont(30f));
		name.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridwidth = 5;
		gbc.gridy = y_counter++;
		add(name, gbc);

		// Name of the rocket
		JComboBox<String> menu = new JComboBox<String>();
		menu.setMaximumRowCount(20);
		menu.addActionListener(new RocketChooserActionListener());
		comboBoxs.add(menu);
		gbc.gridy = y_counter++;
		add(menu, gbc);

		// Add new Rocket Button
		JButton button = new JButton("+");
		buttons.add(button);
		button.addActionListener(new NewRocketHandler());
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		add(button, gbc);

		// Description
		textArea = new JTextArea(3, 50);
		textArea.addKeyListener(new RocketDescriptionKeyListener());
		textArea.setForeground(Color.GRAY);
		JScrollPane sp = new JScrollPane(textArea);
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.gridwidth = 6;
		add(sp, gbc);

		// Total Rocket Mass
		name = new JLabel();
		labels.add(name);
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.gridy = y_counter++;
		add(name, gbc);
		rocketMass = new JTextField();
		rocketMass.setEditable(false);
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		add(rocketMass, gbc);

		return y_counter;
	}

	/**
	 * inits the Stage Part
	 * 
	 * @param y
	 *            -Axis counter for GBC
	 * @return y-Axis counter for GBC
	 */
	private int initStages(int y_counter) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		// Stage Title
		JLabel name = new JLabel();
		labels.add(name);
		name.setFont(name.getFont().deriveFont(18f));
		name.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.gridwidth = 6;
		add(name, gbc);

		// Stage Panel
		stagesPanel = new StagesPanel(controller);
		JScrollPane sP = new JScrollPane(stagesPanel);
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.weighty = 1;
		gbc.gridwidth = 6;
		add(sP, gbc);

		return y_counter;
	}

	/**
	 * inits Aero and Theta Mode
	 * 
	 * @param y
	 *            -Axis counter for GBC
	 * @return y-Axis counter for GBC
	 */
	private int initDropDown(int y_counter) {
		JLabel name;
		JComboBox<String> menu;
		JButton button;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		name = new JLabel(" ");
		gbc.gridy = y_counter++;
		add(name, gbc);

		// Aero_calculation_mode
		name = new JLabel();
		labels.add(name);
		gbc.weightx = 0.05;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(name, gbc);

		menu = new JComboBox<String>();
		menu.addActionListener(new AeroCalculationModeListener());
		comboBoxs.add(menu);
		gbc.weightx = 0.8;
		gbc.gridwidth = 5;
		gbc.gridx = 1;
		add(menu, gbc);

		// Theta_data
		name = new JLabel();
		labels.add(name);
		gbc.weightx = 0.05;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(name, gbc);

		menu = new JComboBox<String>();
		menu.addActionListener(new ThetaDataActionListener());
		comboBoxs.add(menu);
		gbc.weightx = 0.8;
		gbc.gridwidth = 3;
		gbc.gridx = 1;
		add(menu, gbc);

		button = new JButton("+");
		buttons.add(button);
		button.addActionListener(new addThetaDataActionListenener());
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.gridx = 4;
		add(button, gbc);

		button = new JButton("-");
		buttons.add(button);
		button.addActionListener(new deleteThetaDataActionListenener());
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.gridx = 5;
		add(button, gbc);

		// Theta_calculation_mode
		name = new JLabel();
		labels.add(name);
		gbc.weightx = 0.05;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(name, gbc);

		menu = new JComboBox<String>();
		menu.addActionListener(new ThetaCalculationModeListener());
		comboBoxs.add(menu);
		gbc.weightx = 0.8;
		gbc.gridwidth = 5;
		gbc.gridx = 1;
		add(menu, gbc);

		return y_counter;
	}

	/**
	 * inits everthing under theta Calc Mode
	 * 
	 * @param y
	 *            -Axis counter for GBC
	 * @return y-Axis counter for GBC
	 */
	private int initBottom(int y_counter) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel name = new JLabel(" ");
		gbc.gridy = y_counter++;
		add(name, gbc);

		JButton button = new JButton();
		buttons.add(button);
		button.addActionListener(new SaveRocketHandler());
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.gridwidth = 6;
		add(button, gbc);

		return y_counter;
	}

	private void setComboBoxModels() {
		MutableComboBoxModel<String> rocketsModel = new DefaultComboBoxModel<String>(
				controller.getRocketsNamesArray());
		rocketsModel.setSelectedItem(controller.getCurrentRocketName());
		comboBoxs.get(NAME_DROPDOWN).setModel(rocketsModel);

		MutableComboBoxModel<String> thetaCalcMode = new DefaultComboBoxModel<String>(
				controller.getThetaCalculationModeNamesArray());
		thetaCalcMode.setSelectedItem(controller
				.getDefaultThetaCalculationModeName());
		comboBoxs.get(THETA_CALCULATION_MODE_DROPDOWN).setModel(thetaCalcMode);

		MutableComboBoxModel<String> aeroCalcMode = new DefaultComboBoxModel<String>(
				controller.getAeroCalculationModeNamesArray());
		aeroCalcMode.setSelectedItem(controller
				.getDefaultAeroCalculationModeName());
		comboBoxs.get(AERO_CALCULATION_MODE_DROPDOWN).setModel(aeroCalcMode);

		MutableComboBoxModel<String> thetaData = new DefaultComboBoxModel<String>(
				controller.getThetaDataNamesArray());
		thetaData.setSelectedItem(controller.getDefaultThetaDataName());
		comboBoxs.get(THETA_DATA_DROPDOWN).setModel(thetaData);
	}

	private void updateTitles() {
		labels.get(0).setText(GlobalConfig.getMessage("RocketPanelTitle"));
		labels.get(1).setText(
				GlobalConfig.getMessage("RocketPanelTotalMassTitle"));
		labels.get(1).setToolTipText(
				GlobalConfig.getMessage("RocketPanelTotalMassToolTip"));
		labels.get(2).setText(GlobalConfig.getMessage("StagesPanelTitle"));
		labels.get(3)
				.setText(
						GlobalConfig
								.getMessage("RocketPanelAeodynamicCalculationModeTitle"));
		labels.get(3)
				.setToolTipText(
						GlobalConfig
								.getMessage("RocketPanelAeodynamicCalculationModeToolTip"));
		labels.get(4)
				.setText(
						GlobalConfig
								.getMessage("RocketPanelThetaCalculationDataTitle"));
		labels.get(4).setToolTipText(
				GlobalConfig
						.getMessage("RocketPanelThetaCalculationDataToolTip"));
		labels.get(5)
				.setText(
						GlobalConfig
								.getMessage("RocketPanelThetaCalculationModeTitle"));
		labels.get(5).setToolTipText(
				GlobalConfig
						.getMessage("RocketPanelThetaCalculationModeToolTip"));

		buttons.get(0).setToolTipText(
				GlobalConfig.getMessage("RocketPanelRocketAddButtonToolTip"));
		buttons.get(1)
				.setToolTipText(
						GlobalConfig
								.getMessage("RocketPanelThetaCalculationDataAddButtonToolTip"));
		buttons.get(2)
				.setToolTipText(
						GlobalConfig
								.getMessage("RocketPanelThetaCalculationDataDeleteButtonToolTip"));
		buttons.get(3).setText(
				GlobalConfig.getMessage("RocketPanelSaveRocketButtonTitle"));
		buttons.get(3).setToolTipText(
				GlobalConfig.getMessage("RocketPanelSaveRocketButtonToolTip"));

		textArea.setToolTipText(GlobalConfig
				.getMessage("RocketPanelRocketDescriptionToolTip"));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.setAbsoluteMass();

		if (!(arg1 instanceof FullRocketChange)) {
			// Just some changes inside the rocket
			this.stagesPanel.displayStages();
		} else {
			// New Rocket is set
			this.updateValues();
			controller.repackFrame();
		}
		controller.disableDeleteButtons();
	}

	private class RocketDescriptionKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			controller.setNewRocketDescription(((JTextArea) arg0.getSource())
					.getText());
		}

	}

	private class addThetaDataActionListenener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CreateNewThetaDataListFrame ntd = new CreateNewThetaDataListFrame(
					controller, controller.getThetaDataList());
			ntd.setVisible(true);
		}
	}

	private class deleteThetaDataActionListenener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/* To avoid deleting the wrong theta-datas, we remove them from
			 * possibilities */
			List<String> possibilities = new ArrayList<String>();

			for (String name : Arrays.asList(controller
					.getThetaDataNamesArray())) {
				if (!name.equals("Default shot")
						&& !name.equals("Gravity turn")) {
					possibilities.add(name);
				}
			}

			if (possibilities.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						GlobalConfig.getMessage("ThetaDataNoDeleteFrameText"));
				return;
			}

			String s = (String) JOptionPane.showInputDialog(null,
					GlobalConfig.getMessage("ThetaDataDeleteFrameDescription"),
					GlobalConfig.getMessage("ThetaDataDeleteFrameTitle"),
					JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(),
					controller.getDefaultThetaDataName());
			if (s != null)
				controller.deleteThetaData(s);
		}

	}

	private class ThetaCalculationModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modeName = (String) cb.getSelectedItem();

			controller.setNewThetaCalculationMode(modeName);
		}
	}

	private class AeroCalculationModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modeName = (String) cb.getSelectedItem();

			controller.setNewAeroCalculationMode(modeName);
		}

	}

	private class ThetaDataActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modelName = (String) cb.getSelectedItem();

			controller.setNewThetaData(modelName);
		}
	}

	private class RocketChooserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modelName = (String) cb.getSelectedItem();

			controller.setNewRocket(modelName);
		}

	}

	private class NewRocketHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Ask for name for new Rocket:
			String rocketName = (String) JOptionPane.showInputDialog(null,
					GlobalConfig.getMessage("RocketNameFrameDescription"),
					GlobalConfig.getMessage("RocketNameFrameTitle"),
					JOptionPane.PLAIN_MESSAGE, null, null, "new Rocket name");

			while (Arrays.asList(controller.getRocketsNamesArray()).contains(
					rocketName)) {
				JOptionPane.showMessageDialog(null,
						GlobalConfig.getMessage("RocketNameExists"));
				rocketName = (String) JOptionPane.showInputDialog(null,
						GlobalConfig.getMessage("RocketNameFrameDescription"),
						GlobalConfig.getMessage("RocketNameFrameTitle"),
						JOptionPane.PLAIN_MESSAGE, null, null, rocketName);
			}

			if (rocketName != null && rocketName.length() != 0)
				controller.newRocket(rocketName);
		}

	}

	private class SaveRocketHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc = new JFileChooser(new File("Rockets")) {// Set
																		// default
																		// Directory
																		// to
																		// Rocket
																		// Directory
				@Override
				public void approveSelection() {
					File f = getSelectedFile();
					if (f.exists() && getDialogType() == SAVE_DIALOG) {
						int result = JOptionPane
								.showConfirmDialog(
										this,
										GlobalConfig
												.getMessage("RocketNameExistsFrameToolTip"),
										GlobalConfig
												.getMessage("RocketNameExistsFrameTitle"),
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

			fc.setFileFilter(new FileNameExtensionFilter("XML-Files (.xml)",
					"xml"));

			int ret = fc.showSaveDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File returnFile = fc.getSelectedFile();

				// Add .xml if not already done.
				if (!returnFile.getName().endsWith(".xml")) {
					String newFileName = returnFile.getAbsolutePath() + ".xml";
					returnFile = new File(newFileName);
				}

				if (returnFile.exists()) {// Ask if existing File should be
											// overwritten
					String[] options = {
							GlobalConfig
									.getMessage("RocketNameExistsOverwriteFrameOverwrite"),
							GlobalConfig
									.getMessage("RocketNameExistsOverwriteFrameNotOverwrite") };
					int overWriteReturn = JOptionPane
							.showOptionDialog(
									null,
									GlobalConfig
											.getMessage("RocketNameExistsOverwriteFrameDescription1")
											+ returnFile.getAbsolutePath()
											+ GlobalConfig
													.getMessage("RocketNameExistsOverwriteFrameDescription2"),
									GlobalConfig
											.getMessage("RocketNameExistsOverwriteFrameTitle"),
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE, null, options,
									options[1]);
					if (overWriteReturn == 1) // Do not overwrite
						return;
				}
				controller.saveCurrentRocket(returnFile);
			}
		}

	}

	public void disableThetaDataDelete() {
		// By luck I found it is the button before the last button that deletes
		// the ThetaData
		this.buttons.get(this.buttons.size() - 2).setEnabled(false);

	}

	public void enableThetaDataDelete() {
		// By luck I found it is the button before the last button that deletes
		// the ThetaData
		this.buttons.get(this.buttons.size() - 2).setEnabled(true);
	}

	public void disableAeroDataDelete() {
		this.stagesPanel.disableAeroDataDelete();
	}

	public void enableAeroDataDelete() {
		this.stagesPanel.enableAeroDataDelete();
	}

	public void updateLanguage() {
		updateTitles();
		stagesPanel.updateLanguage();
	}
}
