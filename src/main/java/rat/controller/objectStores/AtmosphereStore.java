package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rat.calculation.planet.atmosphere.AtmosphereModel;
import rat.calculation.planet.atmosphere.models.CIRAHigh;
import rat.calculation.planet.atmosphere.models.CIRALow;
import rat.calculation.planet.atmosphere.models.CIRAMean;
import rat.calculation.planet.atmosphere.models.SimpleAtmosphere;
import rat.calculation.planet.atmosphere.models.USStandardAtmosphere;

/**
 * Stores all Atmosphere-Model-Objects of a project
 * 
 * @author Nils Vißmann
 * 
 */
public class AtmosphereStore {

	private List<AtmosphereModel> atModels;
	private int defaultModel;

	public AtmosphereStore() {
		atModels = new ArrayList<AtmosphereModel>();

		atModels.addAll(getAllModels());
		defaultModel = 4;
	}

	public AtmosphereStore(AtmosphereModel m) {
		this.atModels = new ArrayList<AtmosphereModel>();

		this.atModels.add(m);
		this.atModels.addAll(getOtherModels(m));

		this.defaultModel = 0;
	}

	private Collection<AtmosphereModel> getOtherModels(AtmosphereModel m) {
		Collection<AtmosphereModel> ret = new ArrayList<AtmosphereModel>();

		for (AtmosphereModel mod : getAllModels()) {
			if (!(mod.getName().equals(m.getName()))) {
				ret.add(mod);
			}
		}
		return ret;
	}

	private Collection<AtmosphereModel> getAllModels() {
		ArrayList<AtmosphereModel> ret = new ArrayList<AtmosphereModel>();
		/* Create all known models: */

		ret.add(new SimpleAtmosphere());
		ret.add(new CIRAHigh());
		ret.add(new CIRAMean());
		ret.add(new CIRALow());
		ret.add(new USStandardAtmosphere());

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (AtmosphereModel m : this.atModels)
			ret.add(m.getName());

		return ret;
	}

	public List<AtmosphereModel> getModels() {
		return this.atModels;
	}

	public AtmosphereModel getDefaultModel() {
		return this.atModels.get(defaultModel);
	}
}
