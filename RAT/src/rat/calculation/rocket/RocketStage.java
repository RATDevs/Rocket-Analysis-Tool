package rat.calculation.rocket;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleVector2;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;
import rat.calculation.rocket.specificimpluse.models.LinearImpulseInterpolation;

/**
 * Rocket stage class which contains all the values for a rocket stage of a
 * rocket.
 * 
 * @author Gerhard Mesch
 */

@SuppressWarnings("serial")
public class RocketStage implements Serializable {

	// Aero table as linked list for the stage.
	private LinkedList<AeroDataList> aeroDataLists = new LinkedList<AeroDataList>();
	// activ selected aeroDataList
	private int aeroDataListIndex = 0;

	// Specific impulse of the stage at ground level [s].
	private double specImpuGround = 0;
	// Specific impulse in vacuum [s]
	private double specImpuVacuum = 0;
	// Fuel mass flow of the engine in [kg/m^3].
	private LinkedList<DoubleVector2> fuelMassFlow = new LinkedList<DoubleVector2>();
	// Burn chamber pressure in [Pa]
	private LinkedList<DoubleVector2> burnChamPressure = new LinkedList<DoubleVector2>();

	// mode variables for LinkedList values
	private int fuelMassFlowMode = 1;
	private int burnChamPresMode = 0;

	// Reference diameter of this stage [m].
	private double refeDiameter;
	// Mass of this stage inclusive fuel in [kg].
	private double mass0;
	// Stages burn time in [s]
	private double burnTime;
	// Engine parameter: A_e/A_t ^= engine throat / engine exit diameter.
	private double epsilon;
	// Thermodynamic degree of freedom of propellant.
	private double therDegrOfFreedom;
	// Characteristic velocity in [m/s]
	private double characteristicVelocity;
	// Ignite time of the stage after the last stage separation.
	private double stageIgniteTime;
	// Separation time of the stage a
	private double stageSeparationTime;
	// k-Factor for AeroModel.
	private double aeroKFactor;
	// aBull, bBull, precBull and etaBull Values constants for specific impulse.
	private double aBull, bBull, precBull, eta0Bull;
	private int bullIterations = 15;
	private boolean bullFailed = false;
	// Contains the time when the stage was activated. Has to be set before
	// performing gets.
	private double stageActivationTime;

	private SpecificImpulseModel specificImpulseModel;

	/**
	 * Default constructor for new rockets. Creates all lists and fills with
	 * default values.
	 */
	public RocketStage() {
		// set AeroData
		aeroDataLists.add(new AeroDataList(-1));

		// defualt values
		eta0Bull = 0.2d;
		precBull = 0.000001d;
		aBull = bBull = 0;
		bullIterations = 15;

		specImpuGround = 250.0d;
		specImpuVacuum = 270.0d;
		therDegrOfFreedom = 10;
		epsilon = 10;
		characteristicVelocity = 1258.0d;

		// difficult settings
		aeroKFactor = 0;

		fuelMassFlow.add(new DoubleVector2(0, 0));
		burnChamPressure.add(new DoubleVector2(0, 6500000));

		specificImpulseModel = new LinearImpulseInterpolation();
	}

	/**
	 * Default rocket stage constructor for testing. Returns a hard coded stages
	 * for default rocket
	 */
	public RocketStage(int testStage) {
		specificImpulseModel = new LinearImpulseInterpolation();
		// create test cases
		switch (testStage) {
		case 0: // 1 stage rocket
			refeDiameter = 0.80d;
			mass0 = 8500.0d;
			specImpuGround = 220.0d;
			specImpuVacuum = 250.0d;
			fuelMassFlow.add(new DoubleVector2(0, 60.0d));
			burnTime = 100.0d;
			burnChamPressure.add(new DoubleVector2(0, 6.8E6));
			epsilon = 10.0d;
			therDegrOfFreedom = 8;
			characteristicVelocity = 1974.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 0.0d;
			aBull = bBull = 0;
			break;
		case 1: // large stage 1
			refeDiameter = 2.0d;
			mass0 = 66000;
			specImpuGround = 230.0d;
			specImpuVacuum = 230.0d;
			fuelMassFlow.add(new DoubleVector2(0, 500.0d));
			burnTime = 120.0d;
			burnChamPressure.add(new DoubleVector2(0, 6.0E6));
			epsilon = 9.0d;
			therDegrOfFreedom = 10;
			characteristicVelocity = 0.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 120.0d;
			aBull = bBull = 0;
			break;
		case 2: // medium stage 2
			refeDiameter = 1.3;
			mass0 = 13000.00;
			specImpuGround = 240.0d;
			specImpuVacuum = 240.0d;
			fuelMassFlow.add(new DoubleVector2(0, 60.0d));
			burnTime = 200;
			burnChamPressure.add(new DoubleVector2(0, 7.0E6));
			epsilon = 10.0d;
			therDegrOfFreedom = 10;
			characteristicVelocity = 0.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 190.0d;
			aBull = bBull = 0;
			break;
		case 3: // small stage 3
			refeDiameter = 0.8d;
			mass0 = 3500;
			specImpuGround = 290.0d;
			specImpuVacuum = 290.0d;
			fuelMassFlow.add(new DoubleVector2(0, 12.0d));
			burnTime = 260;
			burnChamPressure.add(new DoubleVector2(0, 6.0E6));
			epsilon = 9.0d;
			therDegrOfFreedom = 10;
			characteristicVelocity = 0.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 260.0d;
			aBull = bBull = 0;
			break;
		case 4: // small satellite - stage 4
			refeDiameter = 0.5d;
			mass0 = 100;
			specImpuGround = 0.0;
			specImpuVacuum = 0.0;
			fuelMassFlow.add(new DoubleVector2(0, 0));
			burnTime = 0;
			burnChamPressure.add(new DoubleVector2(0, 0));
			epsilon = 0;
			therDegrOfFreedom = 10;
			characteristicVelocity = 0.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 0.0d;
			aBull = bBull = 0;
			break;
		case 5: // giant stage 1
			refeDiameter = 2.0d;
			mass0 = 150000;
			specImpuGround = 220.0d;
			specImpuVacuum = 240.0d;
			fuelMassFlow.add(new DoubleVector2(0, 1000d));
			burnTime = 135;
			burnChamPressure.add(new DoubleVector2(0, 6.0E6));
			epsilon = 10.0d;
			therDegrOfFreedom = 10;
			characteristicVelocity = 1161.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 138.0d;
			aBull = bBull = 0;
			break;
		case 6: // medium stage 2
			refeDiameter = 1.5d;
			mass0 = 45000.0d;
			specImpuGround = 230.0d;
			specImpuVacuum = 250.0d;
			fuelMassFlow.add(new DoubleVector2(0, 200.0d));
			burnTime = 200.0d;
			burnChamPressure.add(new DoubleVector2(0, 7.0E6));
			epsilon = 10.0d;
			therDegrOfFreedom = 8;
			characteristicVelocity = 1693.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 2.0d;
			stageSeparationTime = 200.0d;
			aBull = bBull = 0;
			break;
		case 7: // big satellite stage 4
			refeDiameter = 1.0d;
			mass0 = 1000;
			specImpuGround = 0.0;
			specImpuVacuum = 0.0;
			fuelMassFlow.add(new DoubleVector2(0, 0));
			burnTime = 0;
			burnChamPressure.add(new DoubleVector2(0, 0));
			epsilon = 0;
			therDegrOfFreedom = 10;
			characteristicVelocity = 0.0d;
			eta0Bull = 0.2d;
			precBull = 0.000001d;
			aeroKFactor = 0.0d;
			stageIgniteTime = 0.0d;
			stageSeparationTime = 0.0d;
			aBull = bBull = 0;
			break;
		}

		eta0Bull = 0.2d;
		precBull = 0.000001d;
		aBull = bBull = 0;
		bullIterations = 15;
		characteristicVelocity = 0.0d;

		// set other values
		if (burnTime != 0) {
			CalculateBullConstants(0);
		}

		aeroDataLists.add(new AeroDataList(-1));
	}

	public boolean CalculateBullConstants(double time) {
		// Newton-solver
		double etai = eta0Bull; // solution
		double eta_ip1 = 10;
		double eta_ip2 = 0;
		boolean convergenced = false;
		int i = 2; //
		while (!convergenced && i < bullIterations) {
			eta_ip1 = etai
					+ Math.pow(therDegrOfFreedom, therDegrOfFreedom)
					/ (DoubleMath.square(epsilon)
							* Math.pow((therDegrOfFreedom + 1),
									(therDegrOfFreedom + 1))
							* Math.pow((1 - etai), therDegrOfFreedom) * (-therDegrOfFreedom
							* etai / (1 - etai) + 1)) - etai
					/ (-therDegrOfFreedom * etai / (1 - etai) + 1);
			etai = eta_ip1;
			if ((eta_ip1 - eta_ip2) < precBull) {
				convergenced = true;
			}
			i = i + 1;
			eta_ip2 = eta_ip1;
		}
		if (!convergenced) {
			bullFailed = true;
			System.out
					.println("Bull constant calculation did not convergenced.");
			return convergenced;
		}

		// C_inf
		double C_inf = (therDegrOfFreedom + 2)
				* Math.sqrt(Math.pow(therDegrOfFreedom, therDegrOfFreedom)
						/ Math.pow((therDegrOfFreedom + 1),
								(therDegrOfFreedom + 1)));
		// p_e
		double p_e = getBurnChamPressure(time)
				* Math.pow((1 - etai), ((therDegrOfFreedom + 2) / 2));
		// set bull constants
		aBull = Math.sqrt(etai) + epsilon * p_e
				/ (C_inf * getBurnChamPressure(time));
		bBull = epsilon / C_inf;
		return convergenced;
	}

	/**
	 * @param data
	 *            List with the sampling data basis.
	 * @param time
	 *            Sampling time.
	 * @return
	 */
	private static double discretValueSampling(LinkedList<DoubleVector2> data,
			double time) {
		if (time >= data.getLast().t) {
			return data.getLast().value;
		}
		Iterator<DoubleVector2> iter = data.iterator();
		DoubleVector2 last = iter.next();
		while (iter.hasNext()) {
			DoubleVector2 current = iter.next();
			if (current.t >= time) {
				break;
			}
			last = current;
		}
		return last.value;
	}

	/**
	 * @param data
	 *            List with the interpolation data basis.
	 * @param time
	 *            Interpolation time.
	 * @return
	 */
	private static double linearInterpolation(LinkedList<DoubleVector2> data,
			double time) {
		double rvalue = -1;
		// time > last list entry -> return last
		if (time >= data.getLast().t) {
			rvalue = data.getLast().value;
		} else {
			// search entry and interpolate linear
			Iterator<DoubleVector2> iter = data.iterator();
			DoubleVector2 current;
			DoubleVector2 next = iter.next(); // set first value in list
			while (iter.hasNext()) {
				current = next; // set lower bound
				next = iter.next(); // set upper bound
				// if between lower and upper bound --> interpolate
				if (time >= current.t && time < next.t) {
					double distance = next.t - current.t;
					double nextFactor = (time - current.t) / distance;
					double currentFactor = (next.t - time) / distance;
					rvalue = currentFactor * current.value + nextFactor
							* next.value;
					break;
				}
			}
		}
		return rvalue;
	}

	// -------------- Getter and setter-------------------------------//

	public double getSpecImpuGround() {
		return specImpuGround;
	}

	public void setSpecImpuGround(double groundImpulse) {
		this.specImpuGround = groundImpulse;
	}

	public double getSpecImpuVacuum() {
		return specImpuVacuum;
	}

	public void setSpecImpuVacuum(double vacuumImpulse) {
		this.specImpuVacuum = vacuumImpulse;
	}

	/* fuel mass flow */

	/**
	 * Gets the fuel mass flow depending on the given time an the set
	 * fuelMassFlowMode.
	 * 
	 * @param time
	 * @return
	 */
	public double getFuelMassFlow(double time) {
		double absolutStageTime = time - stageActivationTime;
		double returnValue = 0;
		switch (fuelMassFlowMode) {
		case 0:
			returnValue = discretValueSampling(fuelMassFlow, absolutStageTime);
			break;
		case 1:
			returnValue = linearInterpolation(fuelMassFlow, absolutStageTime);
			break;
		default:
			returnValue = -1.0d;
			break;
		}
		// System.out.println(returnValue);
		return returnValue;
	}

	public void setFuelMassFlow(int editMode, double time, double fuelMassFlow) {
		switch (editMode) {
		case 0:
			this.fuelMassFlow.clear();
			break;
		case 1:
			this.fuelMassFlow.add(new DoubleVector2(time, fuelMassFlow));
			break;
		}
	}

	public void setFuelMassFlowList(LinkedList<DoubleVector2> list) {
		fuelMassFlow = list;
	}

	/* burn chamber pressure */
	/**
	 * Gets the burn chamber pressure depending on the given time and the set
	 * burnChamPresMode.
	 * 
	 * @param time
	 * @return
	 */
	public double getBurnChamPressure(double time) {
		double absolutStageTime = time - stageActivationTime;
		switch (burnChamPresMode) {
		case 0:
			return discretValueSampling(burnChamPressure, absolutStageTime);
		case 1:
			return linearInterpolation(burnChamPressure, absolutStageTime);
		default:
			return -1.0d;
		}
	}

	public LinkedList<DoubleVector2> getBurnChamPressureList() {
		return burnChamPressure;
	}

	public void setBurnChamPressure(int editMode, double time,
			double burnChamPressure) {
		switch (editMode) {
		case 0:
			this.burnChamPressure.clear();
			break;
		case 1:
			this.burnChamPressure
					.add(new DoubleVector2(time, burnChamPressure));
			break;
		}
	}

	public void setBurnChamPressureList(LinkedList<DoubleVector2> list) {
		burnChamPressure = list;
	}

	// --------------------------Static
	// values-----------------------------------//
	public double getRefeDiameter() {
		return refeDiameter;
	}

	public void setRefeDiameter(double refeDiameter) {
		this.refeDiameter = refeDiameter;
	}

	public double getMass0() {
		return mass0;
	}

	public void setMass0(double mass0) {
		this.mass0 = mass0;
	}

	public double getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(double burnTime) {
		this.burnTime = burnTime;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getCharacteristicVeleocity() {
		return characteristicVelocity;
	}

	public void setCharacteristicVeleocity(double characteristicVeleocity) {
		this.characteristicVelocity = characteristicVeleocity;
	}

	public double getTherDegrOfFreedom() {
		return therDegrOfFreedom;
	}

	public void setTherDegrOfFreedom(double therDegrOfFreedom) {
		this.therDegrOfFreedom = therDegrOfFreedom;
	}

	public double getStageIgniteTime() {
		return stageIgniteTime;
	}

	public void setStageIgnitionTime(double stageIgniteTime) {
		this.stageIgniteTime = stageIgniteTime;
	}

	public double getStageSeparationTime() {
		return stageSeparationTime;
	}

	public void setStageSeparationTime(double stageSeparationTime) {
		this.stageSeparationTime = stageSeparationTime;
	}

	public LinkedList<AeroDataList> getAeroDataLists() {
		return aeroDataLists;
	}

	public AeroDataList getSelectedAeroDataList() {
		return aeroDataLists.get(aeroDataListIndex);
	}

	public void setAeroDataLists(LinkedList<AeroDataList> aeroTable) {
		aeroDataLists = aeroTable;
	}

	public double getaBull() {
		return aBull;
	}

	public void setaBull(double aBull) {
		this.aBull = aBull;
	}

	public double getbBull() {
		return bBull;
	}

	public void setbBull(double bBull) {
		this.bBull = bBull;
	}

	public double getPrecBull() {
		return precBull;
	}

	public void setPrecBull(double precBull) {
		this.precBull = precBull;
	}

	public double getEta0Bull() {
		return eta0Bull;
	}

	public void setEta0Bull(double eta0Bull) {
		this.eta0Bull = eta0Bull;
	}

	public double getStageActivationTime() {
		return stageActivationTime;
	}

	public void setStageActivationTime(double stageActivationTime) {
		this.stageActivationTime = stageActivationTime;
	}

	public double getAeroKFactor() {
		return aeroKFactor;
	}

	public void setAeroKFactor(double kFactor) {
		aeroKFactor = kFactor;
	}

	public int getBullIterations() {
		return bullIterations;
	}

	public void setBullIterations(int bullIterations) {
		this.bullIterations = bullIterations;
	}

	public int getAeroDataListIndex() {
		return aeroDataListIndex;
	}

	public void setAeroDataListIndex(int aeroDataListIndex) {
		this.aeroDataListIndex = aeroDataListIndex;
	}

	/**
	 * @return the fuelMassFlowMode
	 */
	public int getFuelMassFlowMode() {
		return fuelMassFlowMode;
	}

	/**
	 * @param fuelMassFlowMode
	 *            the fuelMassFlowMode to set
	 */
	public void setFuelMassFlowMode(int fuelMassFlowMode) {
		this.fuelMassFlowMode = fuelMassFlowMode;
	}

	/**
	 * @return the burnChamPresMode
	 */
	public int getBurnChamPresMode() {
		return burnChamPresMode;
	}

	/**
	 * @param burnChamPresMode
	 *            the burnChamPresMode to set
	 */
	public void setBurnChamPresMode(int burnChamPresMode) {
		this.burnChamPresMode = burnChamPresMode;
	}

	/**
	 * @return the bullFailed
	 */
	public boolean isBullFailed() {
		return bullFailed;
	}

	/**
	 * @param bullFailed
	 *            the bullFailed to set
	 */
	public void setBullFailed(boolean bullFailed) {
		this.bullFailed = bullFailed;
	}

	public void setIgniteTime(double i) {
		this.stageIgniteTime = i;
	}

	public void setDiameter(double d) {
		this.refeDiameter = d;
	}

	public void setStartMass(double m) {
		this.mass0 = m;
	}

	public void setChamberPressure(
			LinkedList<DoubleVector2> chamberPressureVector) {
		this.burnChamPressure = chamberPressureVector;
	}

	public void setFuelMassFlow(LinkedList<DoubleVector2> fmf) {
		this.fuelMassFlow = fmf;
	}

	public RocketStage copy() {
		RocketStage ret = new RocketStage();

		ret.refeDiameter = this.refeDiameter;
		ret.mass0 = this.mass0;
		ret.specImpuGround = this.specImpuGround;
		ret.specImpuVacuum = this.specImpuVacuum;
		ret.fuelMassFlow = this.fuelMassFlow;
		ret.burnTime = this.burnTime;
		ret.burnChamPressure = this.burnChamPressure;
		ret.epsilon = this.epsilon;
		ret.therDegrOfFreedom = this.therDegrOfFreedom;
		ret.stageIgniteTime = this.stageIgniteTime;
		ret.stageSeparationTime = this.stageSeparationTime;
		ret.aeroDataLists = aeroDataLists;
		ret.aeroKFactor = this.aeroKFactor;
		ret.aBull = this.aBull;
		ret.bBull = this.bBull;
		ret.precBull = this.precBull;
		ret.eta0Bull = this.eta0Bull;

		return ret;
	}

	public double getIgniteTime() {
		return this.stageIgniteTime;
	}

	public List<DoubleVector2> getFuelMassFlowList() {
		return this.fuelMassFlow;
	}

	/**
	 * Adds aeroDataList to Stage if there is no other List with that name If
	 * there is already a List with the same name, the List is replaced
	 * 
	 * 
	 * @param aeroDataList
	 *            DataList do add to stage
	 */
	public void addAeroDataList(AeroDataList aeroDataList) {
		/* Check if in list */
		for (int i = 0; i < aeroDataLists.size(); i++) {
			if (aeroDataList.getName().equals(aeroDataLists.get(i).getName())) {
				aeroDataLists.add(i, aeroDataList);
				aeroDataLists.remove(i + 1);
				return;
			}
		}
		// In case there is no List with that name we simply add the List at the
		// end of all Lists
		aeroDataLists.addLast(aeroDataList);
	}

	public List<DoubleVector2> getChamberPressureList() {
		return this.burnChamPressure;
	}

	/**
	 * @return the specificImpulseModel
	 */
	public SpecificImpulseModel getSpecificImpulseModel() {
		return specificImpulseModel;
	}

	/**
	 * @param specificImpulseModel
	 *            the specificImpulseModel to set
	 */
	public void setSpecificImpulseModel(
			SpecificImpulseModel specificImpulseModel) {
		this.specificImpulseModel = specificImpulseModel;
	}

	public void updateAeroDataList(AeroDataList validVector) {
		for (int i = 0; i < aeroDataLists.size(); i++) {
			if (aeroDataLists.get(i).getName().equals(validVector)) {
				aeroDataLists.add(i, validVector);
				aeroDataLists.remove(i);
				return;
			}
		}
	}
}
