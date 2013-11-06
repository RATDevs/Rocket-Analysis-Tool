package rat.controller.dataObjects;

/**
 * Class to transport all important values of a Stage. Should provide better
 * separation.
 * 
 * @author Nils Vißmann
 * 
 */
public class StageBean {

	private double igniteTime, diameter, startMass, groundImpuls, vacuumImpuls,
			fuelMassFlow, burnTime, burnChamberPressure, epsilon,
			characteristicVelocity, stageSeperationTime;
	private String[] aeroData;
	private String selectedAeroData, selectedImpulsCalculationMode = null;

	public StageBean(double igniteTime, double diameter, double startMass,
			double groundImpuls, double vacuumImpuls, double fuelMassflow,
			double burnTime, double burnChamberPressure, double epsilon,
			double characteristicVelocity, double stageSeperationTime,
			String[] aeroDatas) {
		this.igniteTime = igniteTime;
		this.diameter = diameter;
		this.startMass = startMass;
		this.groundImpuls = groundImpuls;
		this.vacuumImpuls = vacuumImpuls;
		this.fuelMassFlow = fuelMassflow;
		this.burnTime = burnTime;
		this.burnChamberPressure = burnChamberPressure;
		this.epsilon = epsilon;
		this.characteristicVelocity = characteristicVelocity;
		this.aeroData = aeroDatas;
		this.stageSeperationTime = stageSeperationTime;
	}

	public void setSelectedAeroData(String name) {
		this.selectedAeroData = name;
	}

	public void setSelectedImpulsCalculationMode(String name) {
		this.selectedImpulsCalculationMode = name;
	}

	public String getSelectedAeroData() {
		return this.selectedAeroData;
	}

	public String getSelectedImpulsCalculationMode() {
		return this.selectedImpulsCalculationMode;
	}

	public String[] getAeroData() {
		return this.aeroData;
	}

	public double getIgniteTime() {
		return this.igniteTime;
	}

	public double getRefeDiameter() {
		return this.diameter;
	}

	public double getStartMass() {
		return this.startMass;
	}

	public double getGroundImpuls() {
		return this.groundImpuls;
	}

	public double getVacuumImpuls() {
		return this.vacuumImpuls;
	}

	public double getFuelMassFlow() {
		return this.fuelMassFlow;
	}

	public double getBurnTime() {
		return this.burnTime;
	}

	public double getChamberPressure() {
		return this.burnChamberPressure;
	}

	public double getEpsilon() {
		return this.epsilon;
	}

	public double getCharacteristicVelocity() {
		return this.characteristicVelocity;
	}

	public double getStageSeparationTime() {
		return this.stageSeperationTime;
	}

	public void setVacuumImpuls(double vi) {
		this.vacuumImpuls = vi;
	}

	public void setGroundImpuls(double gi) {
		this.groundImpuls = gi;
	}

}
