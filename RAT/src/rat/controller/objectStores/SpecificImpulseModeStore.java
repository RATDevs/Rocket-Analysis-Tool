package rat.controller.objectStores;

import java.util.ArrayList;
import java.util.List;

import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;
import rat.calculation.rocket.specificimpluse.models.LinearImpulseInterpolation;
import rat.calculation.rocket.specificimpluse.models.NoSpecImpuGround;
import rat.calculation.rocket.specificimpluse.models.NoSpecImpuVacuum;

/**
 * Stores all Specific-Impuls-Mode-Objects of a project.
 * 
 * @author Nils Vißmann
 * 
 */
public class SpecificImpulseModeStore {

	private List<SpecificImpulseModel> modes;

	public SpecificImpulseModeStore() {
		modes = new ArrayList<SpecificImpulseModel>();

		/* Generate all known modes: */
		this.modes.add(new LinearImpulseInterpolation());
		this.modes.add(new NoSpecImpuVacuum());
		this.modes.add(new NoSpecImpuGround());
		// this.modes.add(new BullImpluse());
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (SpecificImpulseModel g : modes)
			ret.add(g.getName());

		return ret;
	}

	public List<SpecificImpulseModel> getModels() {
		return this.modes;
	}

	public SpecificImpulseModel getDefaultModel() {
		return modes.get(0); //
	}
}
