package rat.calculation.rocket;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.aero.AeroModel;
import rat.calculation.rocket.aero.models.AeroDataSplineInterpolator;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;
import rat.calculation.rocket.theta.ThetaList;
import rat.calculation.rocket.theta.ThetaModel;
import rat.calculation.rocket.theta.models.ThetaInterpolator;
import rat.controller.updateObjects.FullRocketChange;

/**
 * Class for Rocket objects,
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class Rocket extends Observable implements Serializable {

	private LinkedList<RocketStage> stages;

	private String name;
	private String description;

	// calculation models
	private ThetaModel thetaModel;
	private AeroModel aeroModel;
	private LinkedList<ThetaList> thetaLists;
	private int thetaListIndex;

	// rocket values
	private double rocketMass;
	private double rocketSeparationMass;
	private boolean engineON;

	// private calculation variables
	private RocketStage currentStage;
	private int currentStageIndex;
	private double nextStageSeparationTime;
	private double currentStageIgnitionTime;
	private boolean lastStageActive;

	/**
	 * Constructor for empty rocket. Sets default parameters for difficult
	 * values. Creates Gravity-Turn thetaList.
	 */
	public Rocket() {
		// init stages
		stages = new LinkedList<RocketStage>();
		stages.add(new RocketStage());
		description = "Additional rocket information";

		// load phi settings
		thetaListIndex = 0;
		thetaModel = new ThetaInterpolator();
		thetaLists = new LinkedList<ThetaList>();
		thetaLists.add(new ThetaList(1));
		thetaLists.add(new ThetaList(0));
		thetaLists.add(new ThetaList(2));
		// aeroModel
		aeroModel = new AeroDataSplineInterpolator();

		// set rocket values
		engineON = false;

		// set calculation variables
		initStageSeparationParameters();
	}

	public Rocket(int testCase) {
		// init stages
		stages = new LinkedList<RocketStage>();

		// load phi settings
		thetaListIndex = 0;
		thetaModel = new ThetaInterpolator();
		thetaLists = new LinkedList<ThetaList>();

		// aeroModel
		aeroModel = new AeroDataSplineInterpolator();

		// set rocket values
		engineON = false;

		description = "This is a ficional rocket, or at least we do not know any rocket with this specification!";

		switch (testCase) {
		case 1: // 1 stage test rocket
			this.name = "1 stage test rocket";
			stages.add(new RocketStage(0));
			thetaLists.add(new ThetaList(1));
			thetaLists.add(new ThetaList(0));
			thetaLists.add(new ThetaList(2));
			break;
		case 2: // 4 stages test rocket
			this.name = "4 stages test rocket";
			stages.add(new RocketStage(1));
			stages.add(new RocketStage(2));
			stages.add(new RocketStage(3));
			stages.add(new RocketStage(4));
			thetaLists.add(new ThetaList(2));
			thetaLists.add(new ThetaList(1));
			thetaLists.add(new ThetaList(0));
			break;
		case 3: // 3 stages test rocket
			this.name = "3 stages test rocket";
			stages.add(new RocketStage(5));
			stages.add(new RocketStage(6));
			stages.add(new RocketStage(7));
			thetaLists.add(new ThetaList(2));
			thetaLists.add(new ThetaList(1));
			thetaLists.add(new ThetaList(0));
			break;
		}

		// set calculation variables
		initStageSeparationParameters();
	}

	public int getStageIndex() {
		return currentStageIndex;
	}

	/**
	 * This method returns the current active rocket stage. Also updates the
	 * rocket mass taking into account the fuel mass loss and stages separation.
	 * 
	 * @param tn
	 *            double - Current simulation time
	 * @param delta_t
	 *            double - simulation time steps to calculate fuel mass loss
	 * @return RocketStage - The current active rocket stage.
	 */
	public RocketStage updateRocketStages(double time, double delta_t) {
		// Time of stage separation reached and is not in last stage
		if ((time >= nextStageSeparationTime) && !lastStageActive) {
			engineON = false;
			currentStageIndex++;

			if (currentStageIndex + 1 == stages.size()) {
				lastStageActive = true;
			}
			// set next stage
			currentStage = stages.get(currentStageIndex);
			currentStage.setStageActivationTime(time);

			currentStageIgnitionTime = time + currentStage.getStageIgniteTime();
			nextStageSeparationTime = time
					+ currentStage.getStageSeparationTime();

			// Iterator<RocketStage> iter = stages.iterator();
			rocketMass = 0;
			for (int i = currentStageIndex; i < stages.size(); i++) {
				rocketMass += stages.get(i).getMass0();
			}
			rocketSeparationMass = rocketMass;

			if (lastStageActive) {
				System.out.println(time
						+ " s; Stage separation done. Now in last stage "
						+ (currentStageIndex + 1) + "; Rocket mass: "
						+ rocketMass + " kg");
			} else {
				System.out.println(time
						+ " s; Stage separation done. Now in stage "
						+ (currentStageIndex + 1) + "; Rocket mass: "
						+ rocketMass + " kg; Next separation at "
						+ nextStageSeparationTime + "s simulation time");
			}
			aeroModel.init(currentStage.getSelectedAeroDataList());
		}
		// engine is on and has burn time left
		double currentStageBurnTime = time - currentStageIgnitionTime;
		if (currentStage.getBurnTime() == 0) {
			engineON = false;
			return currentStage;
		} else if (time >= currentStageIgnitionTime
				&& currentStage.getBurnTime() >= currentStageBurnTime) {
			rocketMass = rocketSeparationMass
					- (currentStageBurnTime * currentStage
							.getFuelMassFlow(time));
			engineON = true;
			return currentStage;
		}
		// ignition time not reach
		else {
			engineON = false;
			return currentStage;
		}
	}

	/**
	 * Call this function before starting a new calculation to reset the stage
	 * separation parameters.
	 */
	public void initStageSeparationParameters() {
		// stages
		currentStage = stages.getFirst();
		currentStage.setStageIgnitionTime(0.0d);
		if (stages.size() == 1) {
			lastStageActive = true;
		} else {
			lastStageActive = false;
		}

		// mass
		Iterator<RocketStage> iter = stages.iterator();
		rocketMass = 0;
		while (iter.hasNext()) {
			RocketStage stage = iter.next();
			rocketMass += stage.getMass0();
		}
		rocketSeparationMass = rocketMass;
		// init calculation variables
		currentStageIndex = 0;
		nextStageSeparationTime = currentStage.getStageSeparationTime();
		currentStageIgnitionTime = currentStage.getStageIgniteTime();

		aeroModel.init(currentStage.getSelectedAeroDataList());
	}

	/**
	 * 
	 * @param index
	 *            Index of the requested stage
	 * @return
	 */
	public RocketStage getStage(int index) {
		return this.stages.get(index);
	}

	public List<RocketStage> getStages() {
		return this.stages;
	}

	/**
	 * Adds a Stage to the stage lists and recalculates the init parameters.
	 * 
	 * @param index
	 *            and adds the new stage behind it.
	 */
	public void addStage(int index) {
		RocketStage newStage = stages.get(index).copy();
		stages.add(index, newStage);

		// recalculates the stage init parameters
		initStageSeparationParameters();

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * deletes the
	 * 
	 * @param index
	 *            from the rocket stages
	 */
	public void removeStage(int index) {
		this.stages.remove(index);

		this.setChanged();
		this.notifyObservers();
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public ThetaModel getThetaModel() {
		return this.thetaModel;
	}

	public AeroModel getAeroModel() {
		return this.aeroModel;
	}

	public LinkedList<ThetaList> getThetaLists() {
		return this.thetaLists;
	}

	public int getThetaListIndex() {
		return this.thetaListIndex;
	}

	public ThetaList getThetaList() {
		return this.thetaLists.get(this.thetaListIndex);
	}

	public double getRocketMass() {
		return this.rocketMass;
	}

	public boolean isEngineOn() {
		return engineON;
	}

	public void setThetaModel(ThetaModel t) {
		this.thetaModel = t;
	}

	public void setAeroModel(AeroModel m) {
		this.aeroModel = m;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setAeroData(AeroDataList m, int index) {
		List<AeroDataList> list = stages.get(index).getAeroDataLists();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(m.getName())) {
				stages.get(index).setAeroDataListIndex(i);
				return;
			}
		}
	}

	public void setSpecificImpulseCalculationMode(SpecificImpulseModel m,
			int index) {
		stages.get(index).setSpecificImpulseModel(m);
	}

	public void setThetaData(ThetaList l) {
		for (int i = 0; i < thetaLists.size(); i++) {
			if (l.getName().equals(thetaLists.get(i).getName())) {
				this.thetaListIndex = i;
				return;
			}
		}
	}

	public void setName(String text) {
		this.name = text;
	}

	public void setStagesList(LinkedList<RocketStage> rocketStages) {
		this.stages = rocketStages;
		initStageSeparationParameters();
	}

	public void setThetaTablesList(LinkedList<ThetaList> rocketPhiTables) {
		this.thetaLists = rocketPhiTables;
	}

	public void addThetaDataList(ThetaList newList) {
		this.thetaLists.add(newList);
	}

	public void replaceThetaDataList(ThetaList oldList, ThetaList newList) {
		for (int i = 0; i < this.thetaLists.size(); i++) {
			if (thetaLists.get(i) == oldList) {
				thetaLists.add(i, newList);
				thetaLists.remove(oldList);
				thetaListIndex = i;
			}
		}
	}

	public void deleteThetaData(String s) {
		for (int i = 0; i < thetaLists.size(); i++) {
			if (thetaLists.get(i).getName().equals(s)) {

				thetaLists.remove(i);

				if (thetaListIndex >= i && thetaListIndex > 0)
					thetaListIndex--;

				this.setChanged();
				this.notifyObservers(new FullRocketChange());
			}
		}

	}

	public void deleteAeroData(AeroDataList list) {
		this.stages.get(0).getAeroDataLists().remove(list);

		for (RocketStage rs : stages) {
			if (rs.getAeroDataListIndex() >= rs.getAeroDataLists().size())
				rs.setAeroDataListIndex(rs.getAeroDataListIndex() - 1);
		}

		this.setChanged();
		this.notifyObservers(/* new FullRocketChange() */);
	}
}