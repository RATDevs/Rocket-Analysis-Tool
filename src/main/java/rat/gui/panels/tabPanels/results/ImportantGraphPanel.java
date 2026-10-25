package rat.gui.panels.tabPanels.results;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.math.plot.Plot2DPanel;

import rat.controller.dataObjects.ImportantGraphsBean;
import rat.controller.project.ProjectController;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class ImportantGraphPanel extends JPanel {

	private final Dimension d_size = new Dimension(250, 150);
	private ProjectController controller;
	private List<Plot2DPanel> plots;
	private List<String[]> plotNames;

	public ImportantGraphPanel(ProjectController controller) {
		setLayout(new GridBagLayout());
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.controller = controller;
		plots = new ArrayList<Plot2DPanel>();
		plotNames = new ArrayList<String[]>();

		initComponents();
	}

	/**
	 * Just Placeholders
	 */
	private void initComponents() {
		int counter_x = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		JLabel space;

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		gbc.weightx = 0.2;
		add(space, gbc);

		// Spaceholder for graph
		space = new JLabel("Pic 1");
		gbc.gridx = counter_x++;
		gbc.weightx = 0;
		add(space, gbc);

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		gbc.weightx = 0.2;
		add(space, gbc);

		// Spaceholder for graph
		space = new JLabel("Pic 2");
		gbc.gridx = counter_x++;
		gbc.weightx = 0;
		add(space, gbc);

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		gbc.weightx = 0.2;
		add(space, gbc);

		// Spaceholder for graph
		space = new JLabel("Pic 3");
		gbc.gridx = counter_x++;
		gbc.weightx = 0;
		add(space, gbc);

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		gbc.weightx = 0.2;
		add(space, gbc);

		// Spaceholder for graph
		space = new JLabel("Pic 4");
		gbc.gridx = counter_x++;
		gbc.weightx = 0;
		add(space, gbc);

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		gbc.weightx = 0.2;
		add(space, gbc);

	}

	public void drawGraphs(ImportantGraphsBean igb) {
		Plot2DPanel p;
		this.removeAll();
		int counter_x = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		JLabel space;

		space = new JLabel(" ");
		gbc.gridx = counter_x++;
		add(space, gbc);

		for (int i = 0; i < igb.getGraphCount(); i++) {
			gbc.gridx = counter_x++;
			gbc.weightx = 1;
			p = createGraphPlotPanel(igb.getGraphNames(i)[0],
					igb.getGraphNames(i)[1], igb.getXAxis(i), igb.getYAxis(i));
			plotNames.add(new String[] { igb.getGraphNames(i)[0],
					igb.getGraphNames(i)[1] });
			plots.add(p);
			add(p, gbc);

			space = new JLabel(" ");
			gbc.gridx = counter_x++;
			gbc.weightx = 0;
			add(space, gbc);
		}
	}

	private Plot2DPanel createGraphPlotPanel(String xAxisName,
			String yAxisName, double[] xAxisData, double[] yAxisData) {
		Plot2DPanel ret = new Plot2DPanel();
		ret.setMinimumSize(d_size);
		ret.setPreferredSize(d_size);

		if (xAxisName.equals("AxisGroundDistance")
				&& yAxisName.equals("AxisHeight")) {
			/* In case of this special Diagram, we want to set the scaling of
			 * the y-axis Because it is so easy, we will just set the last value
			 * to zero instead of letting it be <0 */
			if (yAxisData[yAxisData.length - 1] < 0)
				yAxisData[yAxisData.length - 1] = 0;

		}

		String plotName = GlobalConfig.getMessage(xAxisName) + " / "
				+ GlobalConfig.getMessage(yAxisName);
		ret.addLinePlot(plotName, xAxisData, yAxisData);

		ret.setAxisLabel(0, GlobalConfig.getMessage(xAxisName));
		ret.setAxisLabel(1, GlobalConfig.getMessage(yAxisName));

		ret.setEditable(false);
		ret.setEnabled(false);
		ret.setFocusable(false);
		// Remove all MouseListeners so the graphs stay in place
		for (MouseWheelListener l : ret.plotCanvas.getMouseWheelListeners())
			ret.plotCanvas.removeMouseWheelListener(l);
		for (MouseMotionListener l : ret.plotCanvas.getMouseMotionListeners())
			ret.plotCanvas.removeMouseMotionListener(l);
		for (MouseListener l : ret.plotCanvas.getMouseListeners())
			ret.plotCanvas.removeMouseListener(l);
		ret.addLegend("INVISIBLE");
		ret.removePlotToolBar();

		ret.addMouseListener(new GraphListener(xAxisName, yAxisName));

		return ret;
	}

	private class GraphListener implements MouseListener {

		private String xAxisName;
		private String yAxisName;

		public GraphListener(String xAxisName, String yAxisName) {
			this.xAxisName = xAxisName;
			this.yAxisName = yAxisName;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			controller.showGraph(xAxisName, yAxisName);
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

	/**
	 * Called if language changes
	 */
	public void updateLanguage() {
		for (int i = 0; i < plots.size(); i++) {
			plots.get(i).setAxisLabel(0,
					GlobalConfig.getMessage(plotNames.get(i)[0]));
			plots.get(i).setAxisLabel(1,
					GlobalConfig.getMessage(plotNames.get(i)[1]));
		}
	}
}
