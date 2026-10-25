package rat.gui.frame.dataInputFrame;

import java.util.List;

import rat.controller.project.panelController.SettingController;
import rat.exceptions.InvalidDataException;
import rat.language.GlobalConfig;

/**
 * Window for reading in new Out Iterations
 * 
 * @author Manuel Schmidt, Nils Vißmann
 */
@SuppressWarnings("serial")
public class EditOutIterationsVectorFrame extends GeneralDataInputFrame {

	private SettingController controller;

	public EditOutIterationsVectorFrame(SettingController c, List<String[]> l) {
		super(
				GlobalConfig.getMessage("OutIterationsFrameTitle"),
				new String[] {
						GlobalConfig.getMessage("OutIterationsFrameColumnTime"),
						GlobalConfig
								.getMessage("OutIterationsFrameColumnTimeBetweenSteps") },
				l, GlobalConfig.getMessage("OutIterationsFrameHeadLine"), null,
				null);

		this.controller = c;
	}

	@Override
	protected void updateModel(List<String[]> data) throws InvalidDataException {
		List<Double[]> validVector = controller.checkDoubleVector(data);

		// We just set the new OutIterations if the Check function before does
		// not throw any Exceptions
		controller.setNewOutIterations(validVector);
	}
}
