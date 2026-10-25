package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import rat.calculation.rocket.aero.AeroDataList;

/**
 * Stores all available AeroData objects for a running project.
 * 
 * @author Nils Vißmann
 * 
 */
public class AeroDataStore {

	private List<AeroDataList> aeroData;

	public AeroDataStore() {
		aeroData = new ArrayList<AeroDataList>();

		this.aeroData.addAll(getAll());
	}

	public AeroDataStore(LinkedList<AeroDataList> aeroDataLists) {
		aeroData = new ArrayList<AeroDataList>();

		this.aeroData.addAll(aeroDataLists);

		if (aeroData.isEmpty())
			aeroData.addAll(getAll());
	}

	private Collection<AeroDataList> getAll() {
		ArrayList<AeroDataList> ret = new ArrayList<AeroDataList>();

		/* Create all known models: */
		ret.add(new AeroDataList(0)); // Free chosen index since there is just
										// one "default" aeroDataList

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (AeroDataList g : aeroData)
			ret.add(g.getName());

		return ret;
	}

	public List<AeroDataList> getModels() {
		return this.aeroData;
	}

	public AeroDataList getDefaultModel() {
		return aeroData.get(0); // Return the only one that's created on default
	}

	public void addAeroDataList(AeroDataList validVector) {
		/* Check if in list */
		for (int i = 0; i < aeroData.size(); i++) {
			if (validVector.getName().equals(aeroData.get(i).getName())) {
				aeroData.add(i, validVector);
				aeroData.remove(i + 1);
				return;
			}
		}
		// In case there is no List with that name we simply add the List at the
		// end of all Lists
		this.aeroData.add(validVector);
	}

	public void deleteAeroDataList(AeroDataList list) {
		this.aeroData.remove(list);
	}

	public void updateAeroList(AeroDataList validVector) {
		for (int i = 0; i < aeroData.size(); i++) {
			if (aeroData.get(i).getName().equals(validVector.getName())) {
				aeroData.add(i, validVector);
				aeroData.remove(i + 1);
				return;
			}
		}
	}
}
