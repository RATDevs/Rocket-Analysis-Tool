package rat.controller.objectStores;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rat.calculation.rocket.Rocket;
import rat.controller.dataIO.RocketLoader;

/**
 * Stores all Rocket-Objects of a project
 * 
 * @author Nils Vißmann
 * 
 */
public class RocketStore {

	private List<Rocket> rockets;

	public RocketStore() {
		rockets = new ArrayList<Rocket>();

		try {
			this.rockets.addAll(RocketLoader.loadRockets());
		} catch (FileNotFoundException e) {
		}
		if (rockets.isEmpty()) {
			this.rockets.add(new Rocket(1));// 1 stage test rocket
			this.rockets.add(new Rocket(3));// 3 stage test rocket
			this.rockets.add(new Rocket(2));// 4 stage test rocket
		}
	}

	public RocketStore(Rocket rocket) {
		rockets = new ArrayList<Rocket>();

		rockets.add(rocket);

		rockets.addAll(getOthers(rocket));
	}

	private Collection<Rocket> getOthers(Rocket rocket) {
		ArrayList<Rocket> ret = new ArrayList<Rocket>();

		try {
			for (Rocket r : RocketLoader.loadRockets()) {
				if (!r.getName().equals(rocket.getName()))
					ret.add(r);
			}
		} catch (FileNotFoundException e) {
		}

		if (ret.size() == 1) {
			if (!rocket.getName().equals("1 stage test rocket"))
				ret.add(new Rocket(1));
			if (!rocket.getName().equals("4 stages test rocket"))
				ret.add(new Rocket(2));
			if (!rocket.getName().equals("3 stages test rocket"))
				ret.add(new Rocket(3));

		}

		return ret;
	}

	public List<String> getNames() {
		List<String> ret = new ArrayList<String>();

		for (Rocket g : rockets)
			ret.add(g.getName());

		return ret;
	}

	public List<Rocket> getRockets() {
		return this.rockets;
	}

	public Rocket getDefaultRocket() {
		return rockets.get(0); // After creating the Store, there is always at
								// least one rocket in the list
	}

	public Rocket createNewRocket(String rocketName) {
		Rocket ret = new Rocket();
		ret.setName(rocketName);

		this.rockets.add(ret);

		return ret;

	}

}
