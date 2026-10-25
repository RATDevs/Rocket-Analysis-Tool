package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rat.calculation.planet.gravitation.GravitationModel;
import rat.calculation.planet.gravitation.models.Newton;
import rat.calculation.planet.gravitation.models.WGS84Grav;

/**
 * Stores all Gravitation-Model-Objects of a Project
 * 
 * @author Nils Vißmann
 * 
 */
public class GravitationStore {

	private List<GravitationModel> gravModels;

	public GravitationStore() {
		gravModels = new ArrayList<GravitationModel>();
		this.gravModels.addAll(getAllModels());
	}

	public GravitationStore(GravitationModel gravModel) {
		gravModels = new ArrayList<GravitationModel>();

		gravModels.add(gravModel);
		gravModels.addAll(getOtherModels(gravModel));
	}

	private Collection<? extends GravitationModel> getOtherModels(
			GravitationModel gravModel) {
		Collection<GravitationModel> ret = new ArrayList<GravitationModel>();

		for (GravitationModel m : getAllModels()) {
			if (!m.getName().equals(gravModel.getName())) {
				ret.add(m);
			}
		}

		return ret;
	}

	private Collection<? extends GravitationModel> getAllModels() {
		ArrayList<GravitationModel> ret = new ArrayList<GravitationModel>();
		/* Create all known models: */

		ret.add(new WGS84Grav());
		ret.add(new Newton());

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (GravitationModel g : gravModels)
			ret.add(g.getName());

		return ret;
	}

	public List<GravitationModel> getModels() {
		return this.gravModels;
	}

	public GravitationModel getDefaultModel() {
		return this.gravModels.get(0); // return Newton or Model that was
										// pre-defined
	}

}
