package rat.controller.listener;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import rat.controller.project.GeneralProjectController;

/**
 * 
 * General listener that does the main logic behind the instantiated Listeners
 * for Textfields.
 * 
 * @author Nils Vißmann
 * 
 */
public abstract class GeneralTextFieldDocumentListener implements
		DocumentListener {

	protected GeneralProjectController controller;

	protected String text;
	protected JTextField source;

	public GeneralTextFieldDocumentListener(GeneralProjectController c,
			JTextField source) {
		this.controller = c;
		this.source = source;

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changeHappened(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeHappened(e);
	}

	private void changeHappened(DocumentEvent e) {
		try {
			String text = e.getDocument().getText(0,
					e.getDocument().getLength());
			if (controller.checkDouble(text)) {
				source.setBackground(Color.WHITE);
				listenerFunction();
			} else {
				source.setBackground(Color.RED);
			}
		} catch (BadLocationException e1) {
		}
	}

	public abstract void listenerFunction();
}
