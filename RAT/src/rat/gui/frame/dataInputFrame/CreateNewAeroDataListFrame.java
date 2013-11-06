package rat.gui.frame.dataInputFrame;

import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import rat.calculation.rocket.aero.AeroDataList;
import rat.controller.project.panelController.RocketController;
import rat.exceptions.InvalidDataException;
import rat.gui.StaticVariables;
import rat.language.GlobalConfig;

/**
 * Window for reading in new Atmosphere Data
 * 
 * @author Manuel Schmidt, Nils Vißmann
 * 
 */
@SuppressWarnings("serial")
public class CreateNewAeroDataListFrame extends GeneralDataInputFrame {

	private RocketController controller;

	private JTextField _tfName;

	// private JTextField _tfFactor;

	public CreateNewAeroDataListFrame(RocketController c, List<String[]> l) {
		super(
				GlobalConfig.getMessage("AerodynamicDataAddFrameTitle"),
				new String[] {
						GlobalConfig
								.getMessage("AerodynamicDataAddFrameValuesMach"),
						GlobalConfig
								.getMessage("AerodynamicDataAddFrameValuesCd0EngineOff"),
						GlobalConfig
								.getMessage("AerodynamicDataAddFrameValuesCd0EngineOn"),
						GlobalConfig
								.getMessage("AerodynamicDataAddFrameValuesCLalpha") },
				l, GlobalConfig.getMessage("AerodynamicDataAddFrameHeadLine"),
				null, null);
		_tfName.setText(c.getSelectedAeroDataName(0));
		this.controller = c;
	}

	@Override
	void initComponents(String caption) {
		JLabel name;
		GridBagConstraints gbc = new GridBagConstraints();

		// Title
		name = new JLabel(caption);
		name.setFont(name.getFont().deriveFont(18f));
		name.setHorizontalAlignment(JLabel.CENTER);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = StaticVariables.INIT_X_SIZE_NEWDATAFRAME;
		gbc.ipady = StaticVariables.INIT_Y_SIZE_NEWDATAFRAME;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(name, gbc);

		// Name
		name = new JLabel(
				GlobalConfig.getMessage("AerodynamicDataAddFrameName"));
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(name, gbc);

		_tfName = new JTextField(
				GlobalConfig
						.getMessage("AerodynamicDataAddFrameNameTextfieldTitle"));
		_tfName.setToolTipText(GlobalConfig
				.getMessage("AerodynamicDataAddFrameNameTextfieldToolTip"));
		gbc.gridx = 1;
		add(_tfName, gbc);

		// init table for values
		name = new JLabel(" ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(name, gbc);

		name = new JLabel(GlobalConfig.getMessage("InputFrameValues"));
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(name, gbc);
	}

	@Override
	protected void updateModel(List<String[]> data) throws InvalidDataException {
		String listName = _tfName.getText();
		List<Double[]> validVector = controller.checkAeroDataList(listName,
				data);

		AeroDataList newList = new AeroDataList(listName);
		newList.setAeroDataList(validVector);

		// We just set the new OutIterations if the Check function before does
		// not throw any Exceptions
		controller.setNewAeroDataList(newList);
	}

}
