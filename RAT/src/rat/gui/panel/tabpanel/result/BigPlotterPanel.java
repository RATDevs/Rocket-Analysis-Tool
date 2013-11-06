package rat.gui.panel.tabpanel.result;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class BigPlotterPanel extends JPanel {

	private List<Point> points = new ArrayList<Point>();
	private String x_name;
	private String y_name;
	private int factor_x;
	private int factor_y;
	private int size_x_min;
	private int size_x_max;
	private int size_y_min;
	private int size_y_max;

	private static final int WINDOW_SIZE_X = 900;
	private static final int WINDOW_SIZE_Y = 600;
	private static final int SPACE_X = 50;
	private static final int SPACE_Y = 25;

	public BigPlotterPanel(String x_name, String y_name, double[] x_val,
			double[] y_val) {
		double[] x_values = new double[x_val.length];
		double[] y_values = new double[y_val.length];

		// assert that arrays have the same size
		assert x_values.length == y_values.length;
		this.x_name = x_name;
		this.y_name = y_name;

		// Calculate factor
		size_x_max = Integer.MIN_VALUE;
		size_x_min = Integer.MAX_VALUE;
		size_y_max = Integer.MIN_VALUE;
		size_y_min = Integer.MAX_VALUE;
		// Copy array
		for (int i = 0; i < x_values.length; i++) {
			x_values[i] = x_val[i];
			y_values[i] = y_val[i];
			if (size_x_min > x_values[i])
				size_x_min = ((int) x_values[i]);
			if (size_x_max < x_values[i])
				size_x_max = ((int) x_values[i]);
			if (size_y_min > y_values[i])
				size_y_min = ((int) y_values[i]);
			if (size_y_max < y_values[i])
				size_y_max = ((int) y_values[i]);
		}
		// Set factor
		if (size_x_max != size_x_min)
			factor_x = (WINDOW_SIZE_X - SPACE_X)
					/ (Math.abs(size_x_max) + Math.abs(size_x_min));
		else
			factor_x = 1;
		if (size_y_max != size_y_min)
			factor_y = (WINDOW_SIZE_Y - SPACE_Y)
					/ (Math.abs(size_y_max) + Math.abs(size_y_min));
		else
			factor_y = 1;
		// Multiply each value with the factor
		for (int i = 0; i < x_values.length; i++) {
			x_values[i] *= factor_x;
			y_values[i] *= factor_y;
		}
		size_x_max *= factor_x;
		size_x_min *= factor_x;
		size_y_max *= factor_y;
		size_y_min *= factor_y;
		// Save values as points
		for (int i = 0; i < x_values.length; i++) {
			this.points
					.add(new Point((int) (x_values[i]), (int) (y_values[i])));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return this.getMaximumSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return this.getMaximumSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(WINDOW_SIZE_X, WINDOW_SIZE_Y);
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		// draw axis
		g.drawLine(0, Math.abs(size_y_max) + SPACE_Y / 2, Math.abs(size_x_max)
				+ Math.abs(size_x_min), Math.abs(size_y_max) + SPACE_Y / 2);
		g.drawLine(Math.abs(size_x_min), SPACE_Y / 2, Math.abs(size_x_min),
				Math.abs(size_y_max) + Math.abs(size_y_min) + SPACE_Y / 2);

		// Name the axis
		g.drawString(x_name, Math.abs(size_x_max) + Math.abs(size_x_min) + 5,
				Math.abs(size_y_max) + SPACE_Y / 2 + 4);
		g.drawString(y_name, Math.abs(size_x_min) + 2, 10);

		// change color of graph
		g.setColor(Color.RED);

		// draw points, take care: 0/0 is in the upper left corner
		// Draw points double, so that a polygon may be used
		Polygon poly = new Polygon();
		for (Point p : points) {
			poly.addPoint((int) p.getX(), (int) p.getY() + SPACE_Y / 2);
		}
		for (int i = points.size() - 1; i >= 0; i--) {
			poly.addPoint((int) points.get(i).getX(), (int) points.get(i)
					.getY() + SPACE_Y / 2);
		}
		g.drawPolygon(poly);
	}

	// TODO: Delte. Just for testing
	public static void main(String[] args) {
		double[] x_values = new double[] { 1, 2, 3, 4, 5, 6 };
		double[] y_values = new double[] { 0.5, 0.6, 0.8, 1.2, 2.0, 3.6 };
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(new BigPlotterPanel("x-Achse", "y-Achse", x_values, y_values));
		f.setVisible(true);
		f.pack();
	}
}
