package rat.calculation.rocket.aero;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for storing the aerodynamic base data for a rocket stage.
 * 
 * @author Gerhard Mesch
 * 
 */
@SuppressWarnings("serial")
public class AeroDataList implements Serializable {

	private LinkedList<AeroDataEntry> aeroDataList;
	private String name;

	/**
	 * Creates an empty AeroDataList.
	 */
	public AeroDataList(String name) {
		this.name = name;
		aeroDataList = new LinkedList<AeroDataEntry>();
	}

	/**
	 * Constructor for hard coded default AeroTables.
	 * 
	 * @param aeroDataCase
	 *            - Case select variable.
	 */
	public AeroDataList(int aeroDataCase) {
		switch (aeroDataCase) {

		default:
			name = "Default AeroData";
			aeroDataList = new LinkedList<AeroDataEntry>();
			aeroDataList.add(new AeroDataEntry(0.00, 0.33, 0.36, 8.30));
			aeroDataList.add(new AeroDataEntry(0.30, 0.33, 0.36, 8.39));
			aeroDataList.add(new AeroDataEntry(0.60, 0.33, 0.36, 8.74));
			aeroDataList.add(new AeroDataEntry(0.80, 0.34, 0.37, 8.98));
			aeroDataList.add(new AeroDataEntry(0.90, 0.38, 0.43, 9.50));
			aeroDataList.add(new AeroDataEntry(1.00, 0.44, 0.48, 9.51));
			aeroDataList.add(new AeroDataEntry(1.10, 0.45, 0.49, 9.76));
			aeroDataList.add(new AeroDataEntry(1.20, 0.45, 0.49, 8.82));
			aeroDataList.add(new AeroDataEntry(1.30, 0.45, 0.48, 7.44));
			aeroDataList.add(new AeroDataEntry(1.40, 0.44, 0.47, 6.80));
			aeroDataList.add(new AeroDataEntry(1.50, 0.43, 0.46, 6.53));
			aeroDataList.add(new AeroDataEntry(2.00, 0.36, 0.39, 5.83));
			aeroDataList.add(new AeroDataEntry(3.00, 0.27, 0.30, 5.44));
			aeroDataList.add(new AeroDataEntry(4.00, 0.23, 0.25, 4.58));
			aeroDataList.add(new AeroDataEntry(5.00, 0.20, 0.22, 4.40));
			aeroDataList.add(new AeroDataEntry(6.00, 0.19, 0.20, 4.30));
			aeroDataList.add(new AeroDataEntry(8.00, 0.17, 0.19, 4.20));
			aeroDataList.add(new AeroDataEntry(10.00, 0.16, 0.18, 4.10));
			break;
		}
	}

	/**
	 * Returns the AeroDataList.
	 * 
	 * @return AeroDataList
	 */
	public LinkedList<AeroDataEntry> getAeroDataList() {
		return this.aeroDataList;
	}

	/**
	 * Sets the reference to the given AeroDataList.
	 * 
	 * @param aeroDataList
	 */
	public void setAeroDataList(LinkedList<AeroDataEntry> aeroDataList) {
		this.aeroDataList = aeroDataList;
	}

	/**
	 * Gets the name of the AeroDataList.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the AeroDataList.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setAeroDataList(List<Double[]> doubleVector) {
		for (Double[] d : doubleVector) {
			if (!d[0].equals("") && !d[1].equals("") && !d[2].equals("")
					&& !d[3].equals(""))
				aeroDataList.add(new AeroDataEntry(d[0], d[1], d[2], d[3]));
		}
	}
}
