package rat.controller.dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to transport the data of the graphs displayed in the first result-tab.
 * 
 * @author Nils Vißmann
 * 
 */
public class ImportantGraphsBean {

	List<String[]> names; // names[0] = x-Axis, names[1] = y-Axis
	List<double[]> xAxis;
	List<double[]> yAxis;
	int graphCount;

	public ImportantGraphsBean() {
		names = new ArrayList<String[]>();
		xAxis = new ArrayList<double[]>();
		yAxis = new ArrayList<double[]>();
		graphCount = 0;
	}

	public void AddGraph(String xAxisName, String yAxisName,
			double[] xAxisData, double[] yAxisData) {
		this.names.add(new String[] { xAxisName, yAxisName });
		this.xAxis.add(xAxisData);
		this.yAxis.add(yAxisData);
		this.graphCount++;
	}

	public String[] getGraphNames(int index) {
		return names.get(index);
	}

	public double[] getXAxis(int index) {
		return xAxis.get(index);
	}

	public double[] getYAxis(int index) {
		return yAxis.get(index);
	}

	public int getGraphCount() {
		return this.graphCount;
	}

}
