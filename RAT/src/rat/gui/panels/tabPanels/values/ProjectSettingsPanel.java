package rat.gui.panels.tabPanels.values;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import rat.controller.dataObjects.GeneralValueBean;
import rat.controller.listener.GeneralTextFieldDocumentListener;
import rat.controller.listener.GeneralTextFieldKeyListener;
import rat.controller.project.GeneralProjectController;
import rat.controller.project.panelController.SettingController;
import rat.gui.frame.dataInputFrame.EditOutIterationsVectorFrame;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class ProjectSettingsPanel extends JPanel {

	private static final int DIRECTION = 9;
	private static final int ALPHA = 8;
	private static final int LATITUDE = 6;
	private static final int LONGITUDE = 7;
	private static final int INITIALSPEED = 5;
	private static final int INITIALHEIGHT = 4;
	private static final int HEIGHTABOVEELLIPSOID = 3;
	private static final int STARTTIME = 2;
	private static final int CANCELTIME = 1;
	private static final int TIMESTEP = 0;

	private List<JTextField> textFields;
	private List<JLabel> attributeNames;
	private JLabel outIterations;
	private JLabel title;
	private JButton edit;
	private SettingController controller;

	public ProjectSettingsPanel(SettingController projectController) {

		this.controller = projectController;

		setLayout(new GridBagLayout());
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		textFields = new ArrayList<JTextField>();
		attributeNames = new ArrayList<JLabel>();
		initComponents();
	}

	private void initComponents() {
		initTitle();
		initTextFields();
	}

	/**
	 * inits Title and Space between values and title
	 */
	private void initTitle() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		title = new JLabel(GlobalConfig.getMessage("GeneralValuesPanelTitle"));
		title.setFont(title.getFont().deriveFont(30f));
		title.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(title, gbc);

		JLabel space = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weighty = 0.5;
		add(space, gbc);
	}

	private void initTextFields() {
		JLabel name;
		JTextField field;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		for (int i = 2; i < 13; i++) {
			name = new JLabel();
			attributeNames.add(name);
			gbc.weightx = 0.05;
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = i;
			add(name, gbc);
			// Exception for OutIteration
			if (i != 4) {
				field = new JTextField();
				textFields.add(field);
				gbc.weightx = 0.8;
				gbc.gridwidth = 2;
				gbc.gridx = 1;
				add(field, gbc);
			} else {
				outIterations = new JLabel();
				gbc.weightx = 0.8;
				gbc.gridx = 1;
				add(outIterations, gbc);

				// Button
				gbc.weightx = 0;
				gbc.gridx = 2;
				edit = new JButton(
						GlobalConfig
								.getMessage("GeneralValuesPanelOutputTimestepsButtonTitle"));
				edit.setToolTipText(GlobalConfig
						.getMessage("GeneralValuesPanelOutputTimestepsButtonToolTip"));
				edit.addActionListener(new addOutIterationListener());

				add(edit, gbc);
			}
		}

		name = new JLabel(" ");
		gbc.gridy = 13;
		gbc.gridwidth = 1;
		gbc.weighty = 0.5;
		add(name, gbc);

		// Adding Button to add a OutIteration
		updateAttributNames();
		initializeListeners();
	}

	private void initializeListeners() {
		textFields.get(TIMESTEP).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(TIMESTEP)
				.getDocument()
				.addDocumentListener(
						new timeStepListener(this.controller, textFields
								.get(TIMESTEP)));

		textFields.get(CANCELTIME)
				.addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(CANCELTIME)
				.getDocument()
				.addDocumentListener(
						new cancelTimeListener(this.controller, textFields
								.get(CANCELTIME)));

		textFields.get(STARTTIME).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(STARTTIME)
				.getDocument()
				.addDocumentListener(
						new startTimeListener(this.controller, textFields
								.get(STARTTIME)));

		textFields.get(HEIGHTABOVEELLIPSOID).addKeyListener(
				new GeneralValueKeyListener());
		textFields
				.get(HEIGHTABOVEELLIPSOID)
				.getDocument()
				.addDocumentListener(
						new ellipsoidHeightListener(this.controller, textFields
								.get(HEIGHTABOVEELLIPSOID)));

		textFields.get(INITIALHEIGHT).addKeyListener(
				new GeneralValueKeyListener());
		textFields
				.get(INITIALHEIGHT)
				.getDocument()
				.addDocumentListener(
						new initHeightListener(this.controller, textFields
								.get(INITIALHEIGHT)));

		textFields.get(INITIALSPEED).addKeyListener(
				new GeneralValueKeyListener());
		textFields
				.get(INITIALSPEED)
				.getDocument()
				.addDocumentListener(
						new initSpeedListener(this.controller, textFields
								.get(INITIALSPEED)));

		textFields.get(LATITUDE).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(LATITUDE)
				.getDocument()
				.addDocumentListener(
						new latitudeListener(this.controller, textFields
								.get(LATITUDE)));

		textFields.get(LONGITUDE).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(LONGITUDE)
				.getDocument()
				.addDocumentListener(
						new longitudeListener(this.controller, textFields
								.get(LONGITUDE)));

		textFields.get(ALPHA).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(ALPHA)
				.getDocument()
				.addDocumentListener(
						new alphaListener(this.controller, textFields
								.get(ALPHA)));

		textFields.get(DIRECTION).addKeyListener(new GeneralValueKeyListener());
		textFields
				.get(DIRECTION)
				.getDocument()
				.addDocumentListener(
						new directionListener(this.controller, textFields
								.get(DIRECTION)));

	}

	public void updateValues(GeneralValueBean gvb) {
		textFields.get(TIMESTEP).setText(Double.toString(gvb.getTimeStep()));
		textFields.get(TIMESTEP).setBackground(Color.WHITE);
		textFields.get(CANCELTIME)
				.setText(Double.toString(gvb.getCancelTime()));
		textFields.get(CANCELTIME).setBackground(Color.WHITE);
		outIterations.setText(Double.toString(gvb.getFirstOutIteration()));
		textFields.get(STARTTIME).setText(Double.toString(gvb.getStartTime()));
		textFields.get(STARTTIME).setBackground(Color.WHITE);
		textFields.get(HEIGHTABOVEELLIPSOID).setText(
				Double.toString(gvb.getHeightAboveEllipsoid()));
		textFields.get(HEIGHTABOVEELLIPSOID).setBackground(Color.WHITE);
		textFields.get(INITIALHEIGHT).setText(
				Double.toString(gvb.getInitialHeight()));
		textFields.get(INITIALHEIGHT).setBackground(Color.WHITE);
		textFields.get(INITIALSPEED).setText(
				Double.toString(gvb.getInitialSpeed()));
		textFields.get(INITIALSPEED).setBackground(Color.WHITE);
		textFields.get(LATITUDE).setText(Double.toString(gvb.getLatitude()));
		textFields.get(LATITUDE).setBackground(Color.WHITE);
		textFields.get(LONGITUDE).setText(Double.toString(gvb.getLongitude()));
		textFields.get(LONGITUDE).setBackground(Color.WHITE);
		textFields.get(ALPHA).setText(
				Double.toString(Math.toDegrees(gvb.getAlpha())));
		textFields.get(ALPHA).setBackground(Color.WHITE);
		textFields.get(DIRECTION).setText(
				Double.toString(Math.toDegrees(gvb.getDirection())));
		textFields.get(DIRECTION).setBackground(Color.WHITE);
	}

	private void updateAttributNames() {
		attributeNames
				.get(0)
				.setText(
						GlobalConfig
								.getMessage("GeneralValuesPanelCalcTimestepsTitle"));
		attributeNames.get(0).setToolTipText(
				GlobalConfig
						.getMessage("GeneralValuesPanelCalcTimestepsToolTip"));

		attributeNames.get(1).setText(
				GlobalConfig
						.getMessage("GeneralValuesPanelCalcCancelTimeTitle"));
		attributeNames.get(1).setToolTipText(
				GlobalConfig
						.getMessage("GeneralValuesPanelCalcCancelTimeToolTip"));

		attributeNames.get(2).setText(
				GlobalConfig
						.getMessage("GeneralValuesPanelOutputTimestepsTitle"));
		attributeNames
				.get(2)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelOutputTimestepsToolTip"));

		attributeNames
				.get(3)
				.setText(
						GlobalConfig
								.getMessage("GeneralValuesPanelSimulationStartTimeTitle"));
		attributeNames
				.get(3)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelSimulationStartTimeToolTip"));

		attributeNames
				.get(4)
				.setText(
						GlobalConfig
								.getMessage("GeneralValuesPanelWGS84EllipsoidOffsetTitle"));
		attributeNames
				.get(4)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelWGS84EllipsoidOffsetToolTip"));

		attributeNames.get(5).setText(
				GlobalConfig.getMessage("GeneralValuesPanelStartHeightTitle"));
		attributeNames
				.get(5)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelStartHeightToolTip"));

		attributeNames
				.get(6)
				.setText(
						GlobalConfig
								.getMessage("GeneralValuesPanelStartVelocityTitle"));
		attributeNames.get(6).setToolTipText(
				GlobalConfig
						.getMessage("GeneralValuesPanelStartVelocityToolTip"));

		attributeNames
				.get(7)
				.setText(
						GlobalConfig
								.getMessage("GeneralValuesPanelStartLatitudeTitle"));
		attributeNames.get(7).setToolTipText(
				GlobalConfig
						.getMessage("GeneralValuesPanelStartLatitudeToolTip"));

		attributeNames.get(8).setText(
				GlobalConfig
						.getMessage("GeneralValuesPanelStartLongitudeTitle"));
		attributeNames.get(8).setToolTipText(
				GlobalConfig
						.getMessage("GeneralValuesPanelStartLongitudeToolTip"));

		attributeNames.get(9).setText(
				GlobalConfig
						.getMessage("GeneralValuesPanelRocketAlphaAngleTitle"));
		attributeNames
				.get(9)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelRocketAlphaAngleToolTip"));

		attributeNames.get(10).setText(
				GlobalConfig
						.getMessage("GeneralValuesPanelGeneralDirectionTitle"));
		attributeNames
				.get(10)
				.setToolTipText(
						GlobalConfig
								.getMessage("GeneralValuesPanelGeneralDirectionToolTip"));

		edit.setText(GlobalConfig
				.getMessage("GeneralValuesPanelOutputTimestepsButtonTitle"));
		edit.setToolTipText(GlobalConfig
				.getMessage("GeneralValuesPanelOutputTimestepsButtonToolTip"));
	}

	@Override
	public void setEnabled(boolean b) {
		for (JTextField tf : textFields) {
			tf.setEnabled(b);
		}
	}

	/* Listeners: */
	private class GeneralValueKeyListener extends GeneralTextFieldKeyListener {

		@Override
		public void updatePanel() {
			controller.updateValues();
		}
	}

	private class timeStepListener extends GeneralTextFieldDocumentListener {
		public timeStepListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewTimeStep(source.getText());
		}
	}

	private class cancelTimeListener extends GeneralTextFieldDocumentListener {
		public cancelTimeListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewCancelTime(source.getText());
		}
	}

	private class startTimeListener extends GeneralTextFieldDocumentListener {
		public startTimeListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewStartTime(source.getText());
		}
	}

	private class ellipsoidHeightListener extends
			GeneralTextFieldDocumentListener {
		public ellipsoidHeightListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewHeightAboveEllipse(source
					.getText());
		}
	}

	private class initHeightListener extends GeneralTextFieldDocumentListener {
		public initHeightListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller)
					.setNewStartHeight(source.getText());
		}
	}

	private class initSpeedListener extends GeneralTextFieldDocumentListener {
		public initSpeedListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewSpeed(source.getText());
		}
	}

	private class longitudeListener extends GeneralTextFieldDocumentListener {
		public longitudeListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewLongitude(source.getText());
		}
	}

	private class latitudeListener extends GeneralTextFieldDocumentListener {
		public latitudeListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewLatitude(source.getText());
		}
	}

	private class alphaListener extends GeneralTextFieldDocumentListener {
		public alphaListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewAlpha(source.getText());
		}
	}

	private class directionListener extends GeneralTextFieldDocumentListener {
		public directionListener(GeneralProjectController c, JTextField t) {
			super(c, t);
		}

		@Override
		public void listenerFunction() {
			((SettingController) controller).setNewDirection(source.getText());
		}
	}

	private class addOutIterationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EditOutIterationsVectorFrame eoi = new EditOutIterationsVectorFrame(
					controller, controller.getOutIterationList());
			eoi.setVisible(true);

		}
	}

	public void updateLanguage() {
		title.setText(GlobalConfig.getMessage("GeneralValuesPanelTitle"));
		updateAttributNames();
	}
}
