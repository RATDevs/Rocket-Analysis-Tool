package rat.gui.panels.tabPanels.values.rocket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.BevelBorder;

import rat.controller.dataObjects.StageBean;
import rat.controller.listener.GeneralTextFieldDocumentListener;
import rat.controller.listener.GeneralTextFieldKeyListener;
import rat.controller.project.GeneralProjectController;
import rat.controller.project.panelController.RocketController;
import rat.gui.frame.dataInputFrame.CreateNewAeroDataListFrame;
import rat.gui.frame.dataInputFrame.EditChamberPressureAndFuelMassFlowVectorFrame;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt, Nils Vißmann
 */
@SuppressWarnings("serial")
public class SingleStagePanel extends JPanel {

	private JButton deleteAeroDataButton;
	private JButton addAeroDataButton;

	private static final int FUELMASSFLOW = 0;
	private static final int CHAMBERPRESSURE = 1;

	private static final int DIAMETER = 0;
	private static final int STARTMASS = 1;
	private static final int BURNTIME = 2;
	private static final int GROUNDIMPULS = 3;
	private static final int VACUUMIMPULS = 4;
	private static final int EPSILON = 5;
	private static final int CHARACTERISTIC_VELOCITY = 6;
	private static final int IGNITETIME = 7;
	private static final int STAGE_SEPARATION_TIME = 8;

	private int index;

	private JLabel titleLabel;
	private List<JButton> buttons;
	private List<JLabel> labels;
	private List<JLabel> valueLabels;
	private List<JTextField> fields;
	private List<JComboBox<String>> boxs;

	private RocketController controller;

	public SingleStagePanel(String title, RocketController c, int ind,
			StageBean stageBean) {

		this.controller = c;
		this.index = ind;

		this.fields = new ArrayList<JTextField>();
		this.boxs = new ArrayList<JComboBox<String>>();
		this.buttons = new ArrayList<JButton>();
		this.labels = new ArrayList<JLabel>();
		this.valueLabels = new ArrayList<JLabel>();
		this.setLayout(new GridBagLayout());

		if (index == -1)
			initValues();
		else
			initTextboxes(title, stageBean);

		if (stageBean != null) {
			switch (stageBean.getSelectedImpulsCalculationMode()) {
			case "Linear SIP interpolation":
				System.out.println("[DEBUG] linear SIP interpolation");
				// Greyout "Combustion chamber pressure [Pa]",
				// "Expansion ratio", "Characteristic velocity [m/s]"
				fields.get(EPSILON).setEnabled(false);
				fields.get(CHARACTERISTIC_VELOCITY).setEnabled(false);
				break;
			case "SIP in vacuum NOT known":
				System.out.println("[DEBUG] Vacuum not known SIP");
				// Greyout "Specific impulse in vacuum [s]"
				fields.get(VACUUMIMPULS).setEnabled(false);
				break;
			case "SIP on ground NOT known":
				System.out.println("[DEBUG] Ground not known SIP");
				// Greyout "Specific impulse at sea level [s]"
				fields.get(GROUNDIMPULS).setEnabled(false);
				break;
			}
		}
	}

	@Override
	public void setEnabled(boolean b) {
		for (JTextField tf : fields)
			tf.setEnabled(b);

		for (JComboBox<String> cb : boxs)
			cb.setEnabled(b);

		for (JButton bt : buttons) {
			bt.setEnabled(b);
		}
	}

	/**
	 * Case of index=-1 Case of Labeling Stage
	 */
	private void initValues() {
		JLabel actualLabel;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;

		titleLabel = new JLabel();
		add(titleLabel, gbc);

		for (int i = 0; i < 12; i++) {
			gbc.gridy++;
			actualLabel = new JLabel();
			valueLabels.add(actualLabel);
			add(actualLabel, gbc);

		}

		gbc.gridy++;
		JPanel p = createAerodataPanel();
		add(p, gbc);

		updateLabelNames();
	}

	private void updateLabelNames() {
		titleLabel.setText(GlobalConfig.getMessage("StagesPanelStageNumber"));
		valueLabels.get(0).setText(
				GlobalConfig.getMessage("StagesPanelStageDiameterTitle"));
		valueLabels.get(0).setToolTipText(
				GlobalConfig.getMessage("StagesPanelStageDiameterToolTip"));
		valueLabels.get(1).setText(
				GlobalConfig.getMessage("StagesPanelStageMassTitle"));
		valueLabels.get(1).setToolTipText(
				GlobalConfig.getMessage("StagesPanelStageMassToolTip"));
		valueLabels.get(2).setText(
				GlobalConfig.getMessage("StagesPanelStageBurntimeTitle"));
		valueLabels.get(2).setToolTipText(
				GlobalConfig.getMessage("StagesPanelStageBurntimeToolTip"));
		valueLabels
				.get(3)
				.setText(
						GlobalConfig
								.getMessage("StagesPanelSpecificImpulseAtGroundLevelTitle"));
		valueLabels
				.get(3)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelSpecificImpulseAtGroundLevelToolTip"));
		valueLabels.get(4).setText(
				GlobalConfig
						.getMessage("StagesPanelSpecificImpulseInVacuumTitle"));
		valueLabels
				.get(4)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelSpecificImpulseInVacuumToolTip"));
		valueLabels.get(5).setText(
				GlobalConfig.getMessage("StagesPanelFuelMassFlowTitle"));
		valueLabels.get(5).setToolTipText(
				GlobalConfig.getMessage("StagesPanelFuelMassFlowToolTip"));
		valueLabels
				.get(6)
				.setText(
						GlobalConfig
								.getMessage("StagesPanelCombustionChamberPressureTitle"));
		valueLabels
				.get(6)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelCombustionChamberPressureToolTip"));
		valueLabels.get(7).setText(
				GlobalConfig.getMessage("StagesPanelExpansionRatioTitle"));
		valueLabels.get(7).setToolTipText(
				GlobalConfig.getMessage("StagesPanelExpansionRatioToolTip"));
		valueLabels.get(8).setText(
				GlobalConfig
						.getMessage("StagesPanelCharacteristicVelocityTitle"));
		valueLabels
				.get(8)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelCharacteristicVelocityToolTip"));
		valueLabels.get(9).setText(
				GlobalConfig.getMessage("StagesPanelStageIgnitionTimeTitle"));
		valueLabels.get(9).setToolTipText(
				GlobalConfig.getMessage("StagesPanelStageIgnitionTimeToolTip"));
		valueLabels.get(10).setText(
				GlobalConfig.getMessage("StagesPanelStageSeperationTimeTitle"));
		valueLabels.get(10).setToolTipText(
				GlobalConfig
						.getMessage("StagesPanelStageSeperationTimeToolTip"));
		valueLabels
				.get(11)
				.setText(
						GlobalConfig
								.getMessage("StagesPanelSpecificImpulseCalculationModeTitle"));
		valueLabels
				.get(11)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelSpecificImpulseCalculationModeToolTip"));
		valueLabels
				.get(12)
				.setText(
						GlobalConfig
								.getMessage("StagesPanelAeodynamicCalculationDataTitle"));
		valueLabels
				.get(12)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelAeodynamicCalculationDataToolTip"));

		deleteAeroDataButton
				.setToolTipText(GlobalConfig
						.getMessage("StagesPanelAeodynamicCalculationDataAddButtonToolTip"));
		deleteAeroDataButton
				.setToolTipText(GlobalConfig
						.getMessage("StagesPanelAeodynamicCalculationDataDeleteButtonToolTip"));
	}

	/**
	 * Method to create the Panel for Aerodata
	 */
	private JPanel createAerodataPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Title
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		gbc.weightx = 1;

		JLabel actualLabel = new JLabel();
		valueLabels.add(actualLabel);
		p.add(actualLabel, gbc);

		this.addAeroDataButton = new JButton("+");
		addAeroDataButton.addActionListener(new AddAeroDataActionListenener());
		addAeroDataButton.setFont(addAeroDataButton.getFont().deriveFont(10f));
		addAeroDataButton.setMargin(new java.awt.Insets(0, 5, 0, 5));
		gbc.weightx = 0;
		gbc.gridx = 1;
		p.add(addAeroDataButton, gbc);
		this.deleteAeroDataButton = new JButton("-");
		deleteAeroDataButton
				.addActionListener(new DeleteAeroDataActionListenener());
		deleteAeroDataButton.setFont(deleteAeroDataButton.getFont().deriveFont(
				10f));
		deleteAeroDataButton.setMargin(new java.awt.Insets(0, 5, 0, 5));
		gbc.gridx = 2;
		p.add(deleteAeroDataButton, gbc);
		return p;
	}

	/**
	 * Case of "normal" Stage When index is != -1
	 * 
	 * @param title
	 *            is the Stage Number
	 * @param stageBean
	 *            is the data
	 */
	private void initTextboxes(String title, StageBean stageBean) {
		int counter = 0;
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JTextField field;

		/* init GridBag */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = counter;
		gbc.weightx = 1;
		gbc.weighty = 1;

		/* Create Head Stage with Buttons to add and delete */
		titleLabel = new JLabel(title);
		add(titleLabel, gbc);

		gbc.weightx = 0;

		JButton button = new JButton("+");
		buttons.add(button);
		button.setFont(button.getFont().deriveFont(15f));
		button.setForeground(Color.getHSBColor(0.3f, 1f, 0.9f));
		button.setMargin(new java.awt.Insets(0, 5, 0, 5));
		button.addActionListener(new AddStageListener());
		gbc.gridx = 1;
		add(button, gbc);

		button = new JButton("x");
		buttons.add(button);
		button.setFont(button.getFont().deriveFont(15f));
		button.setForeground(Color.getHSBColor(0f, 1f, 0.9f));
		button.setMargin(new java.awt.Insets(0, 5, 2, 5));
		button.addActionListener(new DeleteStageListener());
		gbc.gridx = 2;
		add(button, gbc);

		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.ipadx = 0;
		gbc.ipadx = 0;

		/* Create the Textfields */
		for (int i = ++counter; i < 12; i++) {
			gbc.gridy = i;
			// If not Mass Flow or Chamberpressure
			if (i != 6 && i != 7) {
				field = new JTextField();
				fields.add(field);
				add(field, gbc);
			} else {
				add(createOnlyWatchPanel(i), gbc);
			}
		}

		/* Set Values of Textfields and labels */
		initializeTextFields(stageBean);
		initializeLabels(stageBean);
		updateToolTipsOfDataStage();

		/* Create the DropDown Menues */
		gbc.gridy = 12;
		JComboBox<String> box = new JComboBox<String>();

		MutableComboBoxModel<String> specImpulsCalc = new DefaultComboBoxModel<String>(
				controller.getSpecificImpulsCalculationModeNamesArray());
		specImpulsCalc.setSelectedItem(stageBean
				.getSelectedImpulsCalculationMode());
		box.setModel(specImpulsCalc);
		box.addActionListener(new SpecificImpulseCalculationModeActionListener());

		boxs.add(box);
		add(box, gbc);
		gbc.gridy = 13;

		box = new JComboBox<String>();

		MutableComboBoxModel<String> aeroData = new DefaultComboBoxModel<String>(
				controller.getAeroDataNamesArray());
		aeroData.setSelectedItem(stageBean.getSelectedAeroData());
		box.setModel(aeroData);
		box.addActionListener(new AeroDataActionListener());

		boxs.add(box);
		add(box, gbc);
	}

	private void updateToolTipsOfDataStage() {
		buttons.get(0).setToolTipText(
				GlobalConfig.getMessage("StagesPanelAddStageButtonToolTip"));
		buttons.get(1).setToolTipText(
				GlobalConfig.getMessage("StagesPanelDeleteStageButtonToolTip"));
		buttons.get(2)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelCombustionChamberPressureFuelMassFlowEditButtonToolTip"));
		buttons.get(3)
				.setToolTipText(
						GlobalConfig
								.getMessage("StagesPanelCombustionChamberPressureFuelMassFlowEditButtonToolTip"));
	}

	/**
	 * Method to create the Panel for Impuls & MassFlow
	 */
	private JPanel createOnlyWatchPanel(int i) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		JLabel label = new JLabel();
		labels.add(label);
		p.add(label, BorderLayout.CENTER);

		JButton button = new JButton("+");
		buttons.add(button);
		button.setFont(button.getFont().deriveFont(10f));
		button.setMargin(new java.awt.Insets(0, 5, 0, 5));

		switch (i) {
		case 6: {
			button.addActionListener(new editChamberPressureAndFuelMassFlowListener());
			break;
		}
		case 7: {
			button.addActionListener(new editChamberPressureAndFuelMassFlowListener());
			break;
		}
		default:
			break; // This case should never be reached
		}
		p.add(button, BorderLayout.LINE_END);

		return p;
	}

	private void initializeTextFields(StageBean stageBean) {
		JTextField currentField;
		double value;

		currentField = this.fields.get(IGNITETIME);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new IgniteDocumentListener(this.controller, currentField));
		value = stageBean.getIgniteTime();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(DIAMETER);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new DiameterDocumentListener(this.controller, currentField));
		value = stageBean.getRefeDiameter();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(STARTMASS);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new StartMassDocumentListener(this.controller, currentField));
		value = stageBean.getStartMass();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(GROUNDIMPULS);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new GroundImpulsListener(this.controller, currentField));
		value = stageBean.getGroundImpuls();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(VACUUMIMPULS);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument()
				.addDocumentListener(
						new VacuumImpulsDocumentListener(this.controller,
								currentField));
		value = stageBean.getVacuumImpuls();
		;
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(BURNTIME);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new BurnTimeDocumentListener(this.controller, currentField));
		value = stageBean.getBurnTime();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(EPSILON);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new EpsilonDocumentListener(this.controller, currentField));
		value = stageBean.getEpsilon();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(CHARACTERISTIC_VELOCITY);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new CharacteristicVelocityDocumentListener(this.controller,
						currentField));
		value = stageBean.getCharacteristicVelocity();
		currentField.setText(Double.toString(value));

		currentField = this.fields.get(STAGE_SEPARATION_TIME);
		currentField.addKeyListener(new rocketKeyListener());
		currentField.getDocument().addDocumentListener(
				new StageSeparationTimeDocumentListener(this.controller,
						currentField));
		value = stageBean.getStageSeparationTime();
		currentField.setText(Double.toString(value));
	}

	private void initializeLabels(StageBean stageBean) {
		JLabel currentLabel;
		double value;

		currentLabel = this.labels.get(FUELMASSFLOW);
		value = stageBean.getFuelMassFlow();
		currentLabel.setText(Double.toString(value));

		currentLabel = this.labels.get(CHAMBERPRESSURE);
		value = stageBean.getChamberPressure();
		currentLabel.setText(Double.toString(value));
	}

	/**
	 * Dis&Enable of AeroDelteButton
	 */
	public void disableAeroDataDelete() {
		this.deleteAeroDataButton.setEnabled(false);
	}

	public void enableAeroDataDelete() {
		this.deleteAeroDataButton.setEnabled(true);
	}

	/**
	 * Case of Changing Language
	 */
	public void updateLanguage() {
		if (index == -1)
			updateLabelNames();
		else
			updateToolTipsOfDataStage();
	}

	/* *********************************************************************
	 * *******************************************************************
	 * Listener: */
	private class AddStageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.addStage(index);
		}

	}

	private class DeleteStageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.deleteStage(index);
		}

	}

	/**
	 * Key Listeners We capture every key-stroke and check, whether the input is
	 * valid. If the input is valid, we update the model. On a Return, we update
	 * the whole Stages-GUI.
	 */

	private class rocketKeyListener extends GeneralTextFieldKeyListener {
		@Override
		public void updatePanel() {
			controller.updateRocket();
		}
	}

	private abstract class RocketDocumentListener extends
			GeneralTextFieldDocumentListener {

		public RocketDocumentListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		public abstract void listenerFunction();

	}

	private class IgniteDocumentListener extends RocketDocumentListener {
		public IgniteDocumentListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewIgniteTime(index,
					source.getText());
		}
	}

	private class DiameterDocumentListener extends RocketDocumentListener {
		public DiameterDocumentListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewDiameter(index,
					source.getText());
		}
	}

	private class StartMassDocumentListener extends RocketDocumentListener {
		public StartMassDocumentListener(GeneralProjectController c,
				JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewMass(index, source.getText());
		}
	}

	private class BurnTimeDocumentListener extends RocketDocumentListener {
		public BurnTimeDocumentListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewBurntime(index,
					source.getText());
		}
	}

	private class GroundImpulsListener extends RocketDocumentListener {
		public GroundImpulsListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewGroundImpuls(index,
					source.getText());
		}
	}

	private class VacuumImpulsDocumentListener extends RocketDocumentListener {
		public VacuumImpulsDocumentListener(GeneralProjectController c,
				JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewVakuumImpuls(index,
					source.getText());
		}
	}

	private class EpsilonDocumentListener extends RocketDocumentListener {
		public EpsilonDocumentListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewEpsilon(index,
					source.getText());
		}
	}

	private class CharacteristicVelocityDocumentListener extends
			RocketDocumentListener {
		public CharacteristicVelocityDocumentListener(
				GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewCharacteristicVelocity(index,
					source.getText());
		}
	}

	private class StageSeparationTimeDocumentListener extends
			RocketDocumentListener {
		public StageSeparationTimeDocumentListener(GeneralProjectController c,
				JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((RocketController) controller).setNewStageSeperationTime(index,
					source.getText());
		}
	}

	private class AddAeroDataActionListenener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CreateNewAeroDataListFrame nad = new CreateNewAeroDataListFrame(
					controller, controller.getAeroDataLists());
			nad.setVisible(true);
		}
	}

	private class DeleteAeroDataActionListenener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] possibilities = controller.getAeroDataNamesArray();
			String s = (String) JOptionPane.showInputDialog(null, GlobalConfig
					.getMessage("AerodynamicDataDeleteFrameDescription"),
					GlobalConfig.getMessage("AerodynamicDataDeleteFrameTitle"),
					JOptionPane.PLAIN_MESSAGE, null, possibilities, controller
							.getDefaultAeroDataName());
			if (s != null)
				controller.deleteAeroData(s);
		}
	}

	private class editChamberPressureAndFuelMassFlowListener implements
			ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			EditChamberPressureAndFuelMassFlowVectorFrame nad = new EditChamberPressureAndFuelMassFlowVectorFrame(
					controller,
					controller.getChamberPressureAndFuelMassFlowList(index),
					index);
			nad.setVisible(true);
		}

	}

	private class AeroDataActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modeName = (String) cb.getSelectedItem();

			controller.setNewAeroData(modeName, index);
		}

	}

	private class SpecificImpulseCalculationModeActionListener implements
			ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modeName = (String) cb.getSelectedItem();

			controller.setNewSpecificImpulseCalculationMode(modeName, index);

			// Enable all eventually disabled fields
			enableFields();
			// grey out not known values:
			switch (modeName) {
			case "Linear SIP interpolation":
				System.out.println("[DEBUG] linear SIP interpolation");
				// Greyout "Combustion chamber pressure [Pa]",
				// "Expansion ratio", "Characteristic velocity [m/s]"
				fields.get(EPSILON).setEnabled(false);
				fields.get(CHARACTERISTIC_VELOCITY).setEnabled(false);
				break;
			case "SIP in vacuum NOT known":
				System.out.println("[DEBUG] Vacuum not known SIP");
				// Greyout "Specific impulse in vacuum [s]"
				fields.get(VACUUMIMPULS).setEnabled(false);
				break;
			case "SIP on ground NOT known":
				System.out.println("[DEBUG] Ground not known SIP");
				// Greyout "Specific impulse at sea level [s]"
				fields.get(GROUNDIMPULS).setEnabled(false);
				break;
			}

		}

		private void enableFields() {
			fields.get(EPSILON).setEnabled(true);
			fields.get(CHARACTERISTIC_VELOCITY).setEnabled(true);
			fields.get(VACUUMIMPULS).setEnabled(true);
			fields.get(GROUNDIMPULS).setEnabled(true);
		}

	}

}
