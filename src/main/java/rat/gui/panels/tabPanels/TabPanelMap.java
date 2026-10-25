package rat.gui.panels.tabPanels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

import rat.calculation.ProjectData;
import rat.controller.project.ProjectController;
import rat.controller.project.panelController.SettingController;
import rat.controller.updateObjects.LocationLatitudeChange;
import rat.controller.updateObjects.LocationLongitudeChange;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class TabPanelMap extends JPanel implements Observer {

	private JXMapKit map;
	private ProjectController controller;
	private double start_latitude;
	private double start_longitude;

	private List<Double> end_latitude = null;
	private List<Double> end_longitude = null;

	public TabPanelMap(ProjectController projectController) {

		this.controller = projectController;
		this.controller.addObserverToData(this);

		this.setLayout(new GridBagLayout());
		initComponents();
		setStartPosition(controller.getLatitude(), controller.getLongitude());
		setFocusStart();

		/**
		 * Possibility of NASA- Maps -> Doesn't work in Germany
		 */
		// WMSService wms = new WMSService();
		// wms.setLayer("BMNG");
		// wms.setBaseUrl("http://wms.jpl.nasa.gov/wms.cgi?");
		// TileFactory fact = new WMSTileFactory(wms);
		// map.setTileFactory(fact);

	}

	private void initComponents() {

		map = new JXMapKit();
		map.getMainMap().addMouseListener(new MapMouseListener());
		map.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
		map.getMainMap().setOverlayPainter(new MapPainter());
		map.setMiniMapVisible(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(map, gbc);
	}

	/**
	 * Method to set startPosition
	 */
	public void setStartPosition(double latitude, double longitude) {
		this.start_latitude = latitude;
		this.start_longitude = longitude;
		map.repaint();
	}

	/**
	 * Method to set endPosition
	 */
	private void setEndPosition(List<Double> latitude, List<Double> longitude) {
		if (latitude.size() == longitude.size()) {
			this.end_latitude = latitude;
			this.end_longitude = longitude;
			map.repaint();
		}
	}

	private void setFocusStart() {
		map.setCenterPosition(new GeoPosition(start_latitude, start_longitude));
	}

	private void setFocusEnd() {
		int i = end_latitude.size() - 1;
		map.setCenterPosition(new GeoPosition(end_latitude.get(i),
				end_longitude.get(i)));
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof LocationLatitudeChange) {
			setStartPosition(((ProjectData) o).getStartLati(), start_longitude);
		}
		if (arg instanceof LocationLongitudeChange) {
			setStartPosition(start_latitude, ((ProjectData) o).getStartLong());
		}
	}

	/**
	 * Method to show postion of detonation and focus on it
	 */
	public void paintTarget(List<Double> latitude, List<Double> longitude) {
		setEndPosition(latitude, longitude);
		setFocusEnd();
	}

	/**
	 * MapPainter that paints the Start end the End of the Rocket
	 */
	private class MapPainter implements Painter<JXMapViewer> {

		@Override
		public void paint(Graphics2D g, JXMapViewer map, int arg2, int arg3) {

			// convert from Vwport to world bitmap
			Rectangle rect = map.getViewportBounds();
			g.translate(-rect.x, -rect.y);

			// Paint Starting Position
			g.setColor(Color.BLACK);
			Point2D start = map.getTileFactory().geoToPixel(
					new GeoPosition(start_latitude, start_longitude),
					map.getZoom());
			g.drawArc((int) (start.getX() - 3), ((int) start.getY() - 3), 6, 6,
					0, 360);
			g.fillArc((int) (start.getX() - 3), ((int) start.getY() - 3), 6, 6,
					0, 360);

			if (end_latitude != null && end_longitude != null) {
				for (int i = 0; i < end_latitude.size(); i++) {
					g.setColor(Color.RED);
					Point2D end = map.getTileFactory().geoToPixel(
							new GeoPosition(end_latitude.get(i),
									end_longitude.get(i)), map.getZoom());
					g.drawArc((int) (end.getX() - 3), ((int) end.getY() - 3),
							6, 6, 0, 360);
					g.fillArc((int) (end.getX() - 3), ((int) end.getY() - 3),
							6, 6, 0, 360);
				}
			}
			g.dispose();

		}
	}

	private class MapMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent me) {
			// In case of leftclick
			if (me.getButton() == MouseEvent.BUTTON1) {
				/**
				 * Get MousePosition and set as new Startposition
				 */
				JXMapViewer mapV = map.getMainMap();
				Rectangle rect = mapV.getViewportBounds();
				me.translatePoint(rect.x, rect.y);
				GeoPosition gp = mapV.getTileFactory().pixelToGeo(
						me.getPoint(), mapV.getZoom());
				setStartPosition(gp.getLatitude(), gp.getLongitude());
				((SettingController) controller.getSettingController())
						.setNewLatitude(Double.toString(start_latitude));
				((SettingController) controller.getSettingController())
						.setNewLongitude(Double.toString(start_longitude));
				((SettingController) controller.getSettingController())
						.updateValues();
			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	/**
	 * 
	 * *************************************************************************
	 * ***************************************
	 * **********************************
	 * ****************************************
	 * **************************************
	 * 
	 * FROM HERE
	 * 
	 * FUNCTIONS THAT ARE NOT USED AT THE MOMENT. BUT MIGHT BE INTERESTING BY
	 * TRYING TO IMPLEMENT A RANGE OF THE ROCKET
	 * 
	 */

	/**
	 * Compareable Values Munich-North 100km in y direction and Munich-East
	 * 100km in x direction
	 */
	private static final GeoPosition munich = new GeoPosition(48.1448353,
			11.5580066);
	private static final GeoPosition east = new GeoPosition(48.1448353,
			12.904265902760);
	private static final GeoPosition north = new GeoPosition(49.0431152339435,
			11.5580066);

	/**
	 * Calculates the pixel between Munich-East and Munich-North, which is
	 * exactly 100km and calculates the pixel for the given range and gives them
	 * back as dist[0]-x direction and dist[1] -y direction
	 */
	private double[] calcDistanceByExample(double range, JXMapViewer map) {
		double[] dist = new double[2];

		Point2D munichP = map.getTileFactory()
				.geoToPixel(munich, map.getZoom());
		Point2D eastP = map.getTileFactory().geoToPixel(east, map.getZoom());
		Point2D eastN = map.getTileFactory().geoToPixel(north, map.getZoom());

		dist[0] = (Math.abs(eastP.getX() - munichP.getX())) / 100 * range;
		dist[1] = (Math.abs(eastN.getY() - munichP.getY())) / 100 * range;

		return dist;
	}

	/**
	 * MapPainter that paints the Start end the End of the Rocket
	 */
	@SuppressWarnings("unused")
	private class MapPainter2 implements Painter<JXMapViewer> {

		private double range;

		public MapPainter2(double range) {
			this.range = range;
		}

		@Override
		public void paint(Graphics2D g, JXMapViewer map, int arg2, int arg3) {

			// convert from Vwport to world bitmap
			Rectangle rect = map.getViewportBounds();
			g.translate(-rect.x, -rect.y);

			// Paint Starting Position
			g.setColor(Color.BLACK);
			Point2D start = map.getTileFactory().geoToPixel(
					new GeoPosition(start_latitude, start_longitude),
					map.getZoom());
			g.drawArc((int) (start.getX() - 3), ((int) start.getY() - 3), 6, 6,
					0, 360);
			g.fillArc((int) (start.getX() - 3), ((int) start.getY() - 3), 6, 6,
					0, 360);

			// Paint Circle
			double[] dist = calcDistanceByExample(range, map);
			Point2D pt = new MapPoint(start.getX() - (dist[0]), start.getY()
					- (dist[1]));
			g.setColor(new Color(255, 0, 0, 100));
			g.fillArc(((int) pt.getX()), ((int) pt.getY()),
					(int) (2 * dist[0]), (int) (2 * dist[1]), 0, 360);
			g.setColor(Color.RED);
			g.drawArc(((int) pt.getX()), ((int) pt.getY()),
					(int) (2 * dist[0]), (int) (2 * dist[1]), 0, 360);

			g.dispose();
		}
	}

	/**
	 * Other possibility to calculate and show range of rocket - via Geopoints
	 * FOR NOW UNUSED
	 */
	@SuppressWarnings("unused")
	private void paintDistanceByGeoPoints(Graphics2D g, JXMapViewer map,
			int arg2, int arg3) {

		List<GeoPosition> region = new ArrayList<GeoPosition>();
		Rectangle rect = map.getViewportBounds();
		g.translate(-rect.x, -rect.y);
		Point2D pt;
		// create a polygon
		Polygon poly = new Polygon();
		for (GeoPosition gp : region) {
			// convert geo to world bitmap pixel
			pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
			poly.addPoint((int) pt.getX(), (int) pt.getY());
		}

		// do the drawing
		g.setColor(new Color(255, 0, 0, 100));
		g.fill(poly);
		g.setColor(Color.RED);
		g.draw(poly);
		g.dispose();
	}

	/**
	 * Own implementation of Point2D
	 */
	private class MapPoint extends Point2D {
		private double x;
		private double y;

		public MapPoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public double getX() {
			return x;
		}

		@Override
		public double getY() {
			return y;
		}

		@Override
		public void setLocation(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

}
