package rat.gui.frame.dataInputFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rat.exceptions.InvalidDataException;
import rat.gui.StaticVariables;
import rat.language.GlobalConfig;

/**
 * @author Nils Vißmann
 * 
 *         Superclass for all Input-Frames like OutIteration or the impulses.
 * 
 */
@SuppressWarnings("serial")
public abstract class GeneralDataInputFrame extends JFrame {

	protected JTable _taTable;
	protected DefaultTableModel _tblModel;
	private String[] tableHeader;

	protected GeneralDataInputFrame(String title, String[] tableHeader,
			List<String[]> data, String caption, String warningTitle,
			String warningText) {
		this.initializeLayout(title);
		this.initializeTableModel(tableHeader);
		this.setTableData(data);
		this.initializeTable();

		this.setMinimumSize(new Dimension(400, 300));
		this.initializeComponents(
				caption,
				new SaveListener(this, warningTitle == null ? GlobalConfig
						.getMessage("InputFrameDefaultWarningTitle")
						: warningTitle, warningText == null ? GlobalConfig
						.getMessage("InputFrameDefaultWarningText")
						: warningText));

		this.pack();
	}

	private void initializeLayout(String title) {
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle(title);
		this.setLayout(new GridBagLayout());
	}

	private void initializeTableModel(String[] header) {
		this.tableHeader = header;
		_tblModel = new DefaultTableModel(header, 1);
	}

	private void initializeTable() {
		// y=2
		GridBagConstraints gbc = new GridBagConstraints();

		this._taTable = new JTable(this._tblModel);
		JScrollPane scroll = new JScrollPane(this._taTable);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;

		add(scroll, gbc);

		_taTable.addKeyListener(new TableController());
		_taTable.addMouseListener(new MouseTableListener());
	}

	private void initializeComponents(String caption, SaveListener list) {
		this.initComponents(caption);
		this.initButtons(list);
	}

	void initComponents(String caption) {
		int y_counter = 0;
		JLabel name;
		GridBagConstraints gbc = new GridBagConstraints();

		// Title y=0
		name = new JLabel(caption);
		name.setFont(name.getFont().deriveFont(18f));
		name.setHorizontalAlignment(JLabel.CENTER);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = StaticVariables.INIT_X_SIZE_NEWDATAFRAME;
		gbc.ipady = StaticVariables.INIT_Y_SIZE_NEWDATAFRAME;
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		gbc.gridwidth = 3;
		add(name, gbc);

		// init table for values y=1 & y=2
		name = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.gridy = y_counter++;
		add(name, gbc);

		name = new JLabel(GlobalConfig.getMessage("InputFrameValues"));
		gbc.gridx = 0;
		gbc.gridy = y_counter++;
		add(name, gbc);
	}

	private void initButtons(SaveListener list) {
		// y=3 & y=4
		JButton button;
		JLabel space;
		GridBagConstraints gbc = new GridBagConstraints();

		space = new JLabel(" ");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(space, gbc);

		button = new JButton(GlobalConfig.getMessage("SaveButtonTitle"));
		button.setToolTipText(GlobalConfig.getMessage("SaveButtonToolTip"));
		button.addActionListener(list);
		gbc.gridy = 4;
		gbc.gridx = 1;
		add(button, gbc);

		button = new JButton(GlobalConfig.getMessage("CancelButtonTitle"));
		button.setToolTipText(GlobalConfig.getMessage("CancelButtonToolTip"));
		button.addActionListener(new CancelListener(this));
		gbc.gridx = 2;
		add(button, gbc);
	}

	protected void saveData() throws InvalidDataException {
		List<String[]> data = new ArrayList<String[]>();
		@SuppressWarnings("unchecked")
		Vector<Vector<String>> dataVector = _tblModel.getDataVector();
		for (int i = 0; i < dataVector.size(); i++) {
			Vector<String> entry = dataVector.get(i);
			String[] e = new String[entry.size()];
			for (int j = 0; j < entry.size(); j++) {
				e[j] = entry.get(j);
			}
			data.add(e);
		}

		updateModel(data);
	}

	protected abstract void updateModel(List<String[]> data)
			throws InvalidDataException;

	private void setTableData(List<String[]> data) {
		this._tblModel = new DefaultTableModel(this.tableHeader, 1);

		if (data != null && !data.isEmpty())
			for (int i = 0; i < data.size(); i++)
				this._tblModel.insertRow(i, data.get(i));
	}

	protected void displayWarning(String title, String text) {
		JOptionPane.showMessageDialog(null, text, title,
				JOptionPane.WARNING_MESSAGE);
	}

	private class MouseTableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int rows = _taTable.getRowCount();
			if (_taTable.getSelectedRow() == (rows - 1))
				_tblModel.setRowCount(rows + 1);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	private class TableController implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 127 /* delete */) {
				JTable src = (JTable) e.getSource();
				_tblModel.setValueAt("", src.getSelectedRow(),
						src.getSelectedColumn());
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			int rows = _taTable.getRowCount();
			if (_taTable.getSelectedRow() == (rows - 1)
					&& !(e.getKeyChar() == KeyEvent.VK_ENTER
							|| e.getKeyChar() == KeyEvent.VK_DELETE || e
							.getKeyChar() == KeyEvent.VK_BACK_SPACE))
				_tblModel.setRowCount(rows + 1);

		}

		@Override
		public void keyTyped(KeyEvent e) {
			int rows = _taTable.getRowCount();
			if (rows > 2
					&& (e.getKeyChar() == KeyEvent.VK_ENTER
							|| e.getKeyChar() == KeyEvent.VK_DELETE || e
							.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
				boolean lastRowEmpty = false;
				for (int i = _taTable.getRowCount() - 1; i > 0; i--) {
					boolean empty = true;
					for (int j = 0; j < _taTable.getColumnCount(); j++) {
						if (_taTable.getValueAt(i, j) != null
								&& !_taTable.getValueAt(i, j).equals(""))
							empty = false;
					}
					if (empty) {
						if (lastRowEmpty)
							_tblModel.setRowCount(rows - 1);
						else
							lastRowEmpty = true;
					} else
						break;
				}
			}
		}
	}

	protected class SaveListener implements ActionListener {

		private JFrame frame;

		private String warningTitle = null;
		private String warningText = null;

		protected SaveListener(JFrame frame, String warningTitle,
				String warningText) {
			this.frame = frame;
			this.warningText = warningText;
			this.warningTitle = warningTitle;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				if (_taTable.getEditingColumn() == 0)
					_taTable.editCellAt(0, 1);
				else
					_taTable.editCellAt(0, 0);
				saveData();
				frame.setVisible(false);
			} catch (InvalidDataException ex) {
				displayWarning(warningTitle, warningText);
			}
		}

	}

	protected class CancelListener implements ActionListener {
		private JFrame frame;

		protected CancelListener(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.frame.setVisible(false);
		}
	}
}
