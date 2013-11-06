package rat.gui.panels.tabPanels.values;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.BevelBorder;

import rat.controller.project.panelController.PlanetController;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class PlanetPanel extends JPanel {

	private final int SEMI_MAJOR_AXIS = 0;
	private final int SEMI_MINOR_AXIS = 1;
	private final int PLANET_FLATTERING = 2;
	private final int FIRST_ECCENTRICITY = 3;
	private final int GRAVITATIONAL_CONSTANT = 4;
	private final int LINEAR_ECCENTRICITY = 5;
	private final int EARTH_ROTATION_SPEED = 6;
	private final int TOGGLE_EARTHROTATION = 7;

	// private List<JButton> buttons;
	private List<JComboBox<String>> comboBoxs;
	private List<JLabel> attributeNames;
	private List<JLabel> attributeValues;
	private JLabel title;
	private JLabel grav;
	private JLabel atmos;
	private JToggleButton rotation;

	PlanetController controller;

	public PlanetPanel(PlanetController projectController) {

		this.controller = projectController;

		comboBoxs = new ArrayList<JComboBox<String>>();
		attributeNames = new ArrayList<JLabel>();
		attributeValues = new ArrayList<JLabel>();
		setLayout(new GridBagLayout());
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		initComponents();
	}

	private void initComponents() {
		int y_counter = initTitle(0);
		y_counter = initDropDown(y_counter);
		y_counter = initTextFields(y_counter);
	}

	/**
	 * inits the title and the space between the values
	 */
	private int initTitle(int y_counter) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		title = new JLabel(GlobalConfig.getMessage("PlanetPanelTitle"));
		title.setFont(title.getFont().deriveFont(30f));
		title.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = y_counter++;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(title, gbc);

		JLabel space = new JLabel(" ");
		gbc.gridy = y_counter++;
		gbc.weighty = 0.5;
		add(space, gbc);

		return y_counter;
	}

	/**
	 * inits the DropDown Gravitation and Atmosphere
	 * 
	 * @param y_counter
	 */
	private int initDropDown(int y_counter) {
		JComboBox<String> menu;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		grav = new JLabel(GlobalConfig.getMessage("PlanetPanelGravitation"));
		gbc.weightx = 0.05;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(grav, gbc);
		menu = new JComboBox<String>();
		menu.addActionListener(new GravitiActionListener());
		comboBoxs.add(menu);
		gbc.weightx = 0.8;
		gbc.gridx = 1;
		add(menu, gbc);

		atmos = new JLabel(GlobalConfig.getMessage("PlanetPanelAtmosphere"));
		gbc.weightx = 0.05;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(atmos, gbc);
		menu = new JComboBox<String>();
		menu.addActionListener(new AtmosphereActionListener());
		comboBoxs.add(menu);
		gbc.weightx = 0.8;
		gbc.gridx = 1;
		add(menu, gbc);

		return y_counter;
	}

	/**
	 * inits the TextFields
	 * 
	 * @param y_counter
	 */
	private int initTextFields(int y_counter) {
		JLabel name;
		JLabel value;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		name = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = y_counter++;
		add(name, gbc);

		for (int i = 0; i < 7; i++) {
			name = new JLabel();
			attributeNames.add(name);
			gbc.weightx = 0.05;
			gbc.gridx = 0;
			gbc.gridy = y_counter++;
			add(name, gbc);
			value = new JLabel();
			value.setHorizontalAlignment(JLabel.RIGHT);
			attributeValues.add(value);
			gbc.gridwidth = 2;
			gbc.weightx = 0.8;
			gbc.gridx = 1;
			add(value, gbc);
		}

		name = new JLabel();
		attributeNames.add(name);
		gbc.weightx = 0.05;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.gridwidth = 1;
		add(name, gbc);
		rotation = new JToggleButton();
		rotation.addActionListener(new ToggleButtonListener());
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.8;
		add(rotation, gbc);

		name = new JLabel(" ");
		gbc.gridy = y_counter++;
		gbc.weighty = 0.5;
		add(name, gbc);

		updateAttributeNames();

		return y_counter;
	}

	/**
	 * Returns the Value of Earth Rotation
	 * 
	 * @return True if Earth Rotation is turned On
	 */
	public boolean useEarthRotation() {
		return rotation.isSelected();
	}

	/**
	 * updates Titles of JLabels
	 */
	private void updateAttributeNames() {
		attributeNames.get(SEMI_MAJOR_AXIS).setText(
				GlobalConfig
						.getMessage("PlanetPanelPlanetSemi-Major_AxisTitle"));
		attributeNames.get(SEMI_MAJOR_AXIS).setToolTipText(
				GlobalConfig
						.getMessage("PlanetPanelPlanetSemi-Major_AxisToolTip"));

		attributeNames.get(SEMI_MINOR_AXIS).setText(
				GlobalConfig
						.getMessage("PlanetPanelPlanetSemi-Minor-AxisTitle"));
		attributeNames.get(SEMI_MINOR_AXIS).setToolTipText(
				GlobalConfig
						.getMessage("PlanetPanelPlanetSemi-Minor-AxisToolTip"));

		attributeNames.get(PLANET_FLATTERING).setText(
				GlobalConfig
						.getMessage("PlanetPanelFlatteningCoefficientTitle"));
		attributeNames.get(PLANET_FLATTERING).setToolTipText(
				GlobalConfig
						.getMessage("PlanetPanelFlatteningCoefficientToolTip"));

		attributeNames.get(FIRST_ECCENTRICITY).setText(
				GlobalConfig.getMessage("PlanetPanelFirstEccentricityTitle"));
		attributeNames.get(FIRST_ECCENTRICITY).setToolTipText(
				GlobalConfig.getMessage("PlanetPanelFirstEccentricityToolTip"));

		attributeNames
				.get(GRAVITATIONAL_CONSTANT)
				.setText(
						GlobalConfig
								.getMessage("PlanetPanelGeocentricGravitationalConstantTitle"));
		attributeNames
				.get(GRAVITATIONAL_CONSTANT)
				.setToolTipText(
						GlobalConfig
								.getMessage("PlanetPanelGeocentricGravitationalConstantToolTip"));

		attributeNames.get(LINEAR_ECCENTRICITY).setText(
				GlobalConfig.getMessage("PlanetPanelLinearEccentricityTitle"));
		attributeNames
				.get(LINEAR_ECCENTRICITY)
				.setToolTipText(
						GlobalConfig
								.getMessage("PlanetPanelLinearEccentricityToolTip"));

		attributeNames.get(EARTH_ROTATION_SPEED).setText(
				GlobalConfig.getMessage("PlanetPanelEarthRotationSpeedTitle"));
		attributeNames
				.get(EARTH_ROTATION_SPEED)
				.setToolTipText(
						GlobalConfig
								.getMessage("PlanetPanelEarthRotationSpeedToolTip"));

		attributeNames.get(TOGGLE_EARTHROTATION).setText(
				GlobalConfig.getMessage("PlanetPanelEarthRotationTitle"));
		attributeNames.get(TOGGLE_EARTHROTATION).setToolTipText(
				GlobalConfig.getMessage("PlanetPanelEarthRotationToolTip"));

		if (rotation.isSelected())
			rotation.setText(GlobalConfig
					.getMessage("PlanetPanelEarthRotationToggleButtonTitleOff"));
		else
			rotation.setText(GlobalConfig
					.getMessage("PlanetPanelEarthRotationToggleButtonTitleOn"));
		rotation.setToolTipText(GlobalConfig
				.getMessage("PlanetPanelEarthRotationToggleButtonToolTip"));

	}

	// TODO: ADD EARTH ROTATION SPEED IN PARAM - Not Comment any longer
	public void initValues(double majAxis, double minAxis, double flattering,
			double firstEccent, double gravConst, double linEccent,
			double earthRot) {
		/* Gravitation Models */
		JComboBox<String> gravitation = comboBoxs.get(0);
		MutableComboBoxModel<String> gravMod = new DefaultComboBoxModel<String>(
				controller.getGravitationModelNamesArray());
		gravMod.setSelectedItem(controller.getGravitationDefaultModelName());

		gravitation.setModel(gravMod);

		/* Atmosphere Models */
		JComboBox<String> atmosphere = comboBoxs.get(1);
		MutableComboBoxModel<String> atMod = new DefaultComboBoxModel<String>(
				controller.getAtmosphereModelNamesArray());
		atMod.setSelectedItem(controller.getAtmosphereDefaultModelName());

		atmosphere.setModel(atMod);

		/* Default Values for Planet (Earth) */

		attributeValues.get(SEMI_MAJOR_AXIS).setText(Double.toString(majAxis));
		attributeValues.get(SEMI_MINOR_AXIS).setText(Double.toString(minAxis));
		attributeValues.get(PLANET_FLATTERING).setText(
				Double.toString(flattering));
		attributeValues.get(FIRST_ECCENTRICITY).setText(
				Double.toString(firstEccent));
		attributeValues.get(GRAVITATIONAL_CONSTANT).setText(
				Double.toString(gravConst));
		attributeValues.get(LINEAR_ECCENTRICITY).setText(
				Double.toString(linEccent));
		// TODO: Keine Kommmentar mehr
		attributeValues.get(EARTH_ROTATION_SPEED).setText(
				Double.toString(earthRot));

	}

	/**
	 * updates JLabels
	 */
	public void updateLanguage() {
		title.setText(GlobalConfig.getMessage("PlanetPanelTitle"));
		grav.setText(GlobalConfig.getMessage("PlanetPanelGravitation"));
		atmos.setText(GlobalConfig.getMessage("PlanetPanelAtmosphere"));
		updateAttributeNames();
	}

	@Override
	public void setEnabled(boolean b) {

		for (JComboBox<String> cb : comboBoxs)
			cb.setEnabled(b);
	}

	private class GravitiActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modelName = (String) cb.getSelectedItem();

			controller.setNewGravitationModel(modelName);

		}
	}

	private class AtmosphereActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
			String modelName = (String) cb.getSelectedItem();

			controller.setNewAtmosphereModel(modelName);

		}

	}

	private class ToggleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (rotation.isSelected()) {
				rotation.setText(GlobalConfig
						.getMessage("PlanetPanelEarthRotationToggleButtonTitleOff"));
				controller.setRotationEnabled(false);
			} else {
				rotation.setText(GlobalConfig
						.getMessage("PlanetPanelEarthRotationToggleButtonTitleOn"));
				controller.setRotationEnabled(true);
			}
			controller.updateValues();
		}

	}
}
