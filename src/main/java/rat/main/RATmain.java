package rat.main;

import javax.swing.UIManager;

import rat.controller.MainController;

public class RATmain {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		MainController controller = new MainController();
		controller.initialize();
	}
}
