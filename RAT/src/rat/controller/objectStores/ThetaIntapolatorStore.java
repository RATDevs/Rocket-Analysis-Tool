package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rat.calculation.rocket.theta.ThetaModel;
import rat.calculation.rocket.theta.models.ThetaInterpolator;

/**
 * Stores all Intapolation-Modules for the theta-values of a project.
 * 
 * @author Nils Vißmann
 * 
 */
public class ThetaIntapolatorStore {

	private List<ThetaModel> thetaModels;

	public ThetaIntapolatorStore() {
		thetaModels = new ArrayList<ThetaModel>();

		thetaModels.addAll(getAllModels());
	}

	public ThetaIntapolatorStore(ThetaModel tm) {
		thetaModels = new ArrayList<ThetaModel>();
		thetaModels.add(tm);

		thetaModels.addAll(getOthers(tm));
	}

	private Collection<? extends ThetaModel> getOthers(ThetaModel tm) {
		ArrayList<ThetaModel> ret = new ArrayList<ThetaModel>();

		for (ThetaModel m : getAllModels()) {
			if (!tm.getName().equals(m.getName()))
				ret.add(m);
		}

		return ret;
	}

	private Collection<ThetaModel> getAllModels() {
		ArrayList<ThetaModel> ret = new ArrayList<ThetaModel>();

		/* Create all known models: */
		ret.add(new ThetaInterpolator());

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (ThetaModel g : thetaModels)
			ret.add(g.getName());

		return ret;
	}

	public List<ThetaModel> getModels() {
		return this.thetaModels;
	}

	public ThetaModel getDefaultModel() {
		return this.thetaModels.get(0); // Return the only Model that's created
										// by default
	}

}
