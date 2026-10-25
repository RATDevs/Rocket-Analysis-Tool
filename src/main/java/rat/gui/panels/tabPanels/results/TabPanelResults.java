package rat.gui.panels.tabPanels.results;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rat.controller.dataObjects.ImportantGraphsBean;
import rat.controller.project.ProjectController;
import rat.gui.StaticVariables;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt, Nils Vißmann
 */
@SuppressWarnings("serial")
public class TabPanelResults extends JPanel {

	private ProjectController controller;

	private static final String[] columNames = new String[9];

	private ImportantResultPanel pan;
	private ImportantGraphPanel gPan;
	private JTable _taTable;
	private DefaultTableModel _tblModel;
	private JComboBox<String> graphX;
	private JComboBox<String> graphY;
	private DefaultComboBoxModel<String> model_xAxis;
	private DefaultComboBoxModel<String> model_yAxis;
	private JLabel title;
	private JLabel xCoord;
	private JLabel yCoord;
	private JButton showGraph;

	public TabPanelResults(ProjectController projectController) {
		this.controller = projectController;
		updateColumnNames();
		model_xAxis = new DefaultComboBoxModel<String>();
		model_yAxis = new DefaultComboBoxModel<String>();

		this.setLayout(new GridBagLayout());
		initComponents();
	}

	/**
	 * updates the ColumnsNames
	 */
	private void updateColumnNames() {
		columNames[0] = GlobalConfig.getMessage("ResultsPanelTime");
		columNames[1] = GlobalConfig.getMessage("ResultsPanelHeight");
		columNames[2] = GlobalConfig.getMessage("ResultsPanelGroundDistance");
		columNames[3] = GlobalConfig.getMessage("ResultsPanelSpeed");
		columNames[4] = GlobalConfig.getMessage("ResultsPanelThetaAngle");
		columNames[5] = GlobalConfig.getMessage("ResultsPanelGammaAngle");
		columNames[6] = GlobalConfig.getMessage("ResultsPanelAlphaAngle");
		columNames[7] = GlobalConfig.getMessage("ResultsPanelThrust");
		columNames[8] = GlobalConfig.getMessage("ResultsPanelMass");
	}

	private void updateGraphComboBoxModel() {
		model_xAxis.removeAllElements();

		model_xAxis.addElement(GlobalConfig.getMessage("AxisTime"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisHeight"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisGroundDistance"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisSpeed"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisTheta"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisGamma"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisAlpha"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisThrust"));
		model_xAxis.addElement(GlobalConfig.getMessage("AxisMass"));

		model_yAxis.removeAllElements();

		model_yAxis.addElement(GlobalConfig.getMessage("AxisTime"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisHeight"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisGroundDistance"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisSpeed"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisTheta"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisGamma"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisAlpha"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisThrust"));
		model_yAxis.addElement(GlobalConfig.getMessage("AxisMass"));
	}

	private void updateLabelsAndToolTips() {
		title.setText(GlobalConfig.getMessage("TabNameFourth"));
		xCoord.setText(GlobalConfig.getMessage("Graph_X_Coordinate"));
		yCoord.setText(GlobalConfig.getMessage("Graph_Y_Coordinate"));
		showGraph.setText(GlobalConfig
				.getMessage("ResultsPanelShowGraphButtonTitle"));
		showGraph.setToolTipText(GlobalConfig
				.getMessage("ResultsPanelShowGraphButtonToolTip"));
	}

	private void initComponents() {
		int counter_y = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		title = new JLabel(GlobalConfig.getMessage("TabNameFourth"));
		title.setFont(title.getFont().deriveFont(30f));
		title.setHorizontalAlignment(JLabel.RIGHT);
		gbc.gridx = 1;
		gbc.gridy = counter_y++;
		add(title, gbc);

		JLabel space = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = counter_y++;
		add(space, gbc);

		/* Important Data */
		pan = new ImportantResultPanel();
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 3;
		add(pan, gbc);

		space = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 1;
		add(space, gbc);

		/* Graph Panels */
		gPan = new ImportantGraphPanel(controller);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 3;
		gbc.weighty = 0;
		add(gPan, gbc);

		space = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 1;
		add(space, gbc);

		/* Table */
		_tblModel = new DefaultTableModel(columNames, 0);
		_taTable = new JTable(_tblModel);
		_taTable.setEnabled(false);
		JScrollPane p = new JScrollPane(_taTable);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 3;
		gbc.weighty = 1;
		add(p, gbc);

		space = new JLabel(" ");
		gbc.gridx = 1;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 1;
		gbc.weighty = 0;
		add(space, gbc);

		/* DropDown for new Graph */
		xCoord = new JLabel();
		xCoord.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.weighty = 0;
		gbc.weightx = 0.1;
		add(xCoord, gbc);

		graphX = new JComboBox<String>(model_xAxis);
		graphX.setMaximumRowCount(9);
		gbc.gridx = 1;
		gbc.weightx = 0.4;
		add(graphX, gbc);

		showGraph = new JButton();
		showGraph.addActionListener(new showGraphListener());
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		gbc.gridheight = 2;
		add(showGraph, gbc);

		yCoord = new JLabel();
		yCoord.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.weightx = 0.1;
		gbc.gridheight = 1;
		add(yCoord, gbc);

		graphY = new JComboBox<String>(model_yAxis);
		graphY.setMaximumRowCount(9);
		gbc.gridx = 1;
		gbc.weightx = 0.4;
		add(graphY, gbc);

		updateGraphComboBoxModel();
		updateLabelsAndToolTips();
	}

	public ImportantResultPanel getResultPanel() {
		return this.pan;
	}

	public void clearTable() {
		this._tblModel = new DefaultTableModel(columNames, 0);
		this._taTable.setModel(_tblModel);
	}

	public void setMaxSpeed(double maxSpeed) {
		pan.setMaxSpeed(maxSpeed);
	}

	public void setMaxHeight(double maxGroundDistance) {
		pan.setMaxHeight(maxGroundDistance);
	}

	public void setRange(double range) {
		pan.setRange(range);
		controller.repackFrame();
	}

	public void setDuration(double duration) {
		pan.setDuration(duration);
	}

	public void drawGraphs(ImportantGraphsBean igb) {
		gPan.drawGraphs(igb);
		controller.repackFrame();
	}

	public void setTableModel(DefaultTableModel tm) {
		this._tblModel = tm;
	}

	public void addTableRow(String[] rowData) {
		this._tblModel.addRow(rowData);
	}

	private class showGraphListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.showGraph(
					StaticVariables.AxisNames[graphX.getSelectedIndex()],
					StaticVariables.AxisNames[graphY.getSelectedIndex()]);

		}
	}

	public void updateLanguage() {
		updateLabelsAndToolTips();
		updateColumnNames();
		updateGraphComboBoxModel();

		pan.updateLanguage();
		gPan.updateLanguage();
	}

}
