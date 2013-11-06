package rat.controller.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

/**
 * Generall Key-Listener for all the TextFields in the GUI
 * 
 * @author Nils Vißmann
 * 
 */

public abstract class GeneralTextFieldKeyListener implements KeyListener {
	protected final static char RETURN_CODE = 10;
	protected final static char ARROW_RIGHT = 39;
	protected final static char ARROW_LEFT = 37;
	protected final static char ARROW_UP = 38;
	protected final static char ARROW_DOWN = 40;
	protected final static char SPACE = ' ';
	protected final static char DELTE = '';

	private List<Character> importandKeys = new ArrayList<Character>();

	public GeneralTextFieldKeyListener() {

		importandKeys.add(RETURN_CODE);
		importandKeys.add(ARROW_DOWN);
		importandKeys.add(ARROW_LEFT);
		importandKeys.add(ARROW_RIGHT);
		importandKeys.add(ARROW_UP);
		importandKeys.add(SPACE);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// System.out.println("Typed: code: "+arg0.getKeyCode()+"; char: "+arg0.getKeyChar()+"; location: "+arg0.getKeyLocation());
		// //DEBUG
		if (arg0.getKeyChar() == DELTE) {
			// If delete is pressed, the field is completely deleted and set to
			// 0
			((JTextField) arg0.getSource()).setText("");
		} else if (arg0.getKeyCode() == RETURN_CODE) {
			updatePanel();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public abstract void updatePanel();

}
