package rat.main;

import com.formdev.flatlaf.FlatLightLaf;

import rat.controller.MainController;

public class RATmain {

	public static void main(String[] args) {
		if (!FlatLightLaf.setup()) {
			throw new IllegalStateException("Could not initialize FlatLaf");
		}

		MainController controller = new MainController();
		controller.initialize();
	}
}
