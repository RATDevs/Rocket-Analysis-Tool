package rat.calculation.rocket.theta;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for ThetaList for the calculation. It is mandatory that the values are
 * sorted, otherwise the interpolation algorithms produce false values!
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class ThetaList implements Serializable {

	public final String thetaListName;
	public LinkedList<ThetaEntry> list;

	public ThetaList(String listName) {
		this.thetaListName = listName;
		list = new LinkedList<ThetaEntry>();
	}

	/** Constructor for default theta lists. */
	public ThetaList(int listCase) {
		list = new LinkedList<ThetaEntry>();
		switch (listCase) {
		case 0:
			thetaListName = "Gravity turn";
			list.add(new ThetaEntry(0, 90));
			list.add(new ThetaEntry(3, 90));
			list.add(new ThetaEntry(7, 89));
			list.add(new ThetaEntry(15, 85));
			return;
		case 1:
			thetaListName = "Default shot";
			list = new LinkedList<ThetaEntry>();
			list.add(new ThetaEntry(0, 90));
			list.add(new ThetaEntry(3, 90));
			list.add(new ThetaEntry(4, 87.5d));
			list.add(new ThetaEntry(5, 85.0d));
			list.add(new ThetaEntry(6, 82.5d));
			list.add(new ThetaEntry(7, 80.0d));
			list.add(new ThetaEntry(8, 77.5d));
			list.add(new ThetaEntry(9, 75.0d));
			list.add(new ThetaEntry(10, 72.6d));
			list.add(new ThetaEntry(11, 70.7d));
			list.add(new ThetaEntry(12, 69.5d));
			list.add(new ThetaEntry(13, 68.7d));
			list.add(new ThetaEntry(14, 68.0d));
			list.add(new ThetaEntry(48, 44.87d));
			list.add(new ThetaEntry(100, 44.87d));
			return;
		case 2:
			thetaListName = "Satellite launch try";
			list.add(new ThetaEntry(0, 90));
			list.add(new ThetaEntry(3, 90));
			list.add(new ThetaEntry(20, 87));
			list.add(new ThetaEntry(120, 60));
			list.add(new ThetaEntry(200, 42));
			list.add(new ThetaEntry(320, 15));
			list.add(new ThetaEntry(440, 6.0));
			list.add(new ThetaEntry(580, 3.0));
			return;
		}
		thetaListName = "FAIL THETA TABLE";
	}

	public String getName() {
		return this.thetaListName;
	}

	public void setThetaDataList(List<Double[]> validVector) {
		this.list = new LinkedList<ThetaEntry>();
		for (Double[] d : validVector) {
			list.add(new ThetaEntry(d[0], d[1]));
		}
	}
}
