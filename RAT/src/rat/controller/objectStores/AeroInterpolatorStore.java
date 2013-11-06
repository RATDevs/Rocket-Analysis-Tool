package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rat.calculation.rocket.aero.AeroModel;
import rat.calculation.rocket.aero.models.AeroDataInterpolator;
import rat.calculation.rocket.aero.models.AeroDataSplineInterpolator;

/**
 * Stores all available AeroModel objects for a running project.
 * 
 * @author Nils Vißmann
 * 
 */
public class AeroInterpolatorStore {

	private List<AeroModel> aeroModels;

	public AeroInterpolatorStore() {
		aeroModels = new ArrayList<AeroModel>();

		aeroModels.addAll(getAll());
	}

	public AeroInterpolatorStore(AeroModel aeroModel) {
		aeroModels = new ArrayList<AeroModel>();

		aeroModels.add(aeroModel);
		aeroModels.addAll(getOthers(aeroModel));
	}

	private Collection<AeroModel> getOthers(AeroModel am) {
		ArrayList<AeroModel> ret = new ArrayList<AeroModel>();

		for (AeroModel a : getAll()) {
			if (!am.getName().equals(a.getName()))
				ret.add(a);
		}

		return ret;
	}

	private Collection<AeroModel> getAll() {
		ArrayList<AeroModel> ret = new ArrayList<AeroModel>();

		/* Create all known models: */
		ret.add(new AeroDataSplineInterpolator());
		ret.add(new AeroDataInterpolator());

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (AeroModel g : aeroModels)
			ret.add(g.getName());

		return ret;
	}

	public List<AeroModel> getModels() {
		return this.aeroModels;
	}

	public AeroModel getDefaultModel() {
		return this.aeroModels.get(0); // return the only model that's created
										// on default
	}

}
