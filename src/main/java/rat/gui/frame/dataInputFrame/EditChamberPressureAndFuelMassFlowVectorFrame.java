package rat.gui.frame.dataInputFrame;

import java.util.List;

import rat.controller.project.panelController.RocketController;
import rat.exceptions.InvalidDataException;
import rat.language.GlobalConfig;

/**
 * @author Nils Vißmann
 */
@SuppressWarnings("serial")
public class EditChamberPressureAndFuelMassFlowVectorFrame extends
		GeneralDataInputFrame {

	private RocketController controller;
	private int index;

	public EditChamberPressureAndFuelMassFlowVectorFrame(RocketController c,
			List<String[]> l, int index) {
		super(
				GlobalConfig
						.getMessage("ChamberPressureFuelMassFlowFrameTitle"),
				new String[] {
						GlobalConfig
								.getMessage("ChamberPressureFuelMassFlowFrameValuesTime"),
						GlobalConfig
								.getMessage("ChamberPressureFuelMassFlowFrameValuesChamberPressure"),
						GlobalConfig
								.getMessage("ChamberPressureFuelMassFlowFrameValuesFuelMassFlow") },
				l,
				GlobalConfig
						.getMessage("ChamberPressureFuelMassFlowFrameHeadLine"),
				null, null);

		this.controller = c;
		this.index = index;
	}

	@Override
	protected void updateModel(List<String[]> data) throws InvalidDataException {
		List<Double[]> validVector = controller.checkDoubleVector(data);

		// We just set the new OutIterations if the Check function before does
		// not throw any Exceptions
		controller.setNewChamberPressureAndFuelMassFlowVector(validVector,
				index);

	}

}
