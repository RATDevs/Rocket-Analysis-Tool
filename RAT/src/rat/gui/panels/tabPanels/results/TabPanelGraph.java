package rat.gui.panels.tabPanels.results;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import rat.controller.project.ProjectController;
import rat.gui.StaticVariables;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class TabPanelGraph extends JPanel {
	private List<Color> free_colors;

	private ProjectController controller;
	private Plot2DPanel graph;
	private JComboBox<String> graphX;
	private JComboBox<String> graphY;
	private JComboBox<String> plots;
	private DefaultComboBoxModel<String> existing_plots;
	private List<String[]> included_plots;
	private JLabel xCoord;
	private JLabel yCoord;
	private JButton addValues;
	private JLabel delPlot;
	private JButton removePlot;
	private DefaultComboBoxModel<String> model_xAxis;
	private DefaultComboBoxModel<String> model_yAxis;

	public TabPanelGraph(String i, String j, double[] x_values,
			double[] y_values, ProjectController c) {
		this.controller = c;
		this.setLayout(new GridBagLayout());
		existing_plots = new DefaultComboBoxModel<String>();
		included_plots = new ArrayList<String[]>();
		model_xAxis = new DefaultComboBoxModel<String>();
		model_yAxis = new DefaultComboBoxModel<String>();
		initColors();
		initComponents(i, j, x_values, y_values);

	}

	private void initColors() {
		free_colors = new ArrayList<Color>();
		free_colors.add(Color.BLUE);
		free_colors.add(Color.GREEN);
		free_colors.add(Color.RED);
		free_colors.add(Color.YELLOW);
		free_colors.add(Color.ORANGE);
		free_colors.add(Color.BLACK);
		free_colors.add(Color.CYAN);
		free_colors.add(Color.GRAY);
		free_colors.add(Color.PINK);
		free_colors.add(Color.MAGENTA);

	}

	private void initComponents(String x_name, String y_name,
			double[] x_values, double[] y_values) {
		int counter_y = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;

		/* Add Components possibility */
		/* DropDown for new Graph */
		xCoord = new JLabel();
		xCoord.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.weighty = 0;
		gbc.weightx = 0;
		add(xCoord, gbc);

		graphX = new JComboBox<String>(model_xAxis);
		graphX.setMaximumRowCount(9);
		gbc.gridx = 1;
		gbc.weightx = 0.4;
		add(graphX, gbc);

		addValues = new JButton();
		addValues.addActionListener(new showGraphListener(this));
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		gbc.gridheight = 2;
		add(addValues, gbc);

		yCoord = new JLabel();
		yCoord.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.weightx = 0;
		gbc.gridheight = 1;
		add(yCoord, gbc);

		graphY = new JComboBox<String>(model_yAxis);
		graphY.setMaximumRowCount(9);
		gbc.gridx = 1;
		gbc.weightx = 0.4;
		add(graphY, gbc);

		updateGraphComboBoxModel();
		/* Delete Components possibility */
		/* DropDown for delete Plot */
		delPlot = new JLabel();
		delPlot.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.weighty = 0;
		gbc.weightx = 0;
		add(delPlot, gbc);

		plots = new JComboBox<String>(existing_plots);
		gbc.gridx = 1;
		gbc.weightx = 0.4;
		add(plots, gbc);

		removePlot = new JButton();
		removePlot.addActionListener(new deletePlotListener());
		gbc.gridx = 2;
		gbc.weightx = 0.5;
		gbc.gridheight = 2;
		add(removePlot, gbc);

		JLabel space = new JLabel(" ");
		gbc.gridy = counter_y++;
		add(space, gbc);

		/* Graph */
		graph = new Plot2DPanel();
		includePlot(x_values, x_name, y_values, y_name);
		graph.addLegend("EAST");
		gbc.gridx = 0;
		gbc.gridy = counter_y++;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(graph, gbc);

		/* update Name */
		updateLabelsAndButtons();
	}

	/**
	 * Add a Plot
	 * 
	 * @param x_axsis_data
	 * @param x_axsis_name
	 * @param y_axsis_data
	 * @param y_axsis_name
	 */
	public void includePlot(double[] x_axsis_data, String x_axsis_name,
			double[] y_axsis_data, String y_axsis_name) {
		if (free_colors.size() > 0) {
			included_plots.add(new String[] { x_axsis_name, y_axsis_name });
			String p = GlobalConfig.getMessage(x_axsis_name) + " - "
					+ GlobalConfig.getMessage(y_axsis_name);
			graph.addLinePlot(p, free_colors.get(0), x_axsis_data, y_axsis_data);
			free_colors.remove(0);
			existing_plots.addElement(p);
			if (free_colors.size() == 0)
				addValues.setEnabled(false);
		}
	}

	/**
	 * Remove selected Plot
	 */
	private void deletePlot() {
		int index = plots.getSelectedIndex();
		free_colors.add(graph.getPlot(index).getColor());
		graph.removePlot(index);
		existing_plots.removeElementAt(index);
		included_plots.remove(index);

		addValues.setEnabled(true);
	}

	/**
	 * Update Name of Selection Speed and co
	 */
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

	/**
	 * update naming of the existing plots
	 */
	private void updateIncludedPlots() {
		existing_plots.removeAllElements();
		for (int i = 0; i < included_plots.size(); i++) {
			String p = GlobalConfig.getMessage(included_plots.get(i)[0])
					+ " - " + GlobalConfig.getMessage(included_plots.get(i)[1]);
			existing_plots.addElement(p);
			graph.changePlotName(i, p);
		}
	}

	/**
	 * updating titles and tooltip
	 */
	private void updateLabelsAndButtons() {
		xCoord.setText(GlobalConfig.getMessage("Graph_X_Coordinate"));
		yCoord.setText(GlobalConfig.getMessage("Graph_Y_Coordinate"));
		delPlot.setText(GlobalConfig.getMessage("GraphTabDeletePlot"));
		addValues.setText(GlobalConfig
				.getMessage("GraphTabAddValuesButtonTitle"));
		addValues.setToolTipText(GlobalConfig
				.getMessage("GraphTabAddValuesButtonToolTip"));
		removePlot.setText(GlobalConfig
				.getMessage("GraphTabDeletePlotButtonTitle"));
		removePlot.setToolTipText(GlobalConfig
				.getMessage("GraphTabDeletePlotButtonToolTip"));

		graph.setAxisLabel(0, GlobalConfig.getMessage("GraphTabX_AxisName"));
		graph.setAxisLabel(1, GlobalConfig.getMessage("GraphTabY_AxisName"));
	}

	/**
	 * Case of Language Change
	 */
	public void updateLanguage() {
		updateLabelsAndButtons();
		updateGraphComboBoxModel();
		updateIncludedPlots();
	}

	/*Listeners: */

	private class showGraphListener implements ActionListener {

		private TabPanelGraph tpg;

		public showGraphListener(TabPanelGraph tpg) {
			this.tpg = tpg;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Adding a Plot only if a Color is free.
			if (free_colors.size() > 0)
				controller.getGraphValues(
						((String) (StaticVariables.AxisNames[graphX
								.getSelectedIndex()])),
						((String) (StaticVariables.AxisNames[graphY
								.getSelectedIndex()])), tpg);
		}
	}

	private class deletePlotListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			deletePlot();
		}
	}

}
