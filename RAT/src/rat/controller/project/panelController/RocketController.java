package rat.controller.project.panelController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import rat.calculation.Project;
import rat.calculation.math.DoubleVector2;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.aero.AeroDataEntry;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.aero.AeroModel;
import rat.calculation.rocket.specificimpluse.SpecificImpulseModel;
import rat.calculation.rocket.theta.ThetaEntry;
import rat.calculation.rocket.theta.ThetaList;
import rat.calculation.rocket.theta.ThetaModel;
import rat.controller.MainController;
import rat.controller.dataIO.RocketXMLOutputAdapter;
import rat.controller.dataObjects.StageBean;
import rat.controller.objectStores.AeroDataStore;
import rat.controller.objectStores.AeroInterpolatorStore;
import rat.controller.objectStores.RocketStore;
import rat.controller.objectStores.SpecificImpulseModeStore;
import rat.controller.objectStores.ThetaIntapolatorStore;
import rat.controller.project.GeneralProjectController;
import rat.controller.project.ProjectController;
import rat.exceptions.InvalidDataException;
import rat.gui.panels.tabPanels.values.RocketPanel;

/**
 * Controller for the Rocket Panel.
 * 
 * @author Nils Vißmann
 * 
 */
public class RocketController extends GeneralProjectController {

	private RocketPanel rocketPanel;

	private RocketStore rocketStore;
	private ThetaIntapolatorStore thetInterpolatorStore;
	private AeroInterpolatorStore aeroInterpolatorStore;
	private AeroDataStore aeroDataStore;
	private SpecificImpulseModeStore specImpulsStore;

	private ProjectController projectController;

	public RocketController(Project project, MainController mainController2,
			ProjectController pc) {
		super(mainController2, project);

		this.projectController = pc;

		this.rocketStore = new RocketStore();
		this.setDefaultRocket();

		this.thetInterpolatorStore = new ThetaIntapolatorStore();
		this.aeroInterpolatorStore = new AeroInterpolatorStore();
		this.aeroDataStore = new AeroDataStore(project.getRocket().getStage(0)
				.getAeroDataLists());
		this.specImpulsStore = new SpecificImpulseModeStore();

		this.rocketPanel = new RocketPanel(this);
		this.projectModel.addObserver(rocketPanel);
		this.projectModel.getRocket().addObserver(rocketPanel);

	}

	public RocketController(Project project, MainController mainController,
			Rocket rocket, ProjectController pc) {
		super(mainController, project);

		this.projectController = pc;

		this.rocketStore = new RocketStore(rocket);
		this.thetInterpolatorStore = new ThetaIntapolatorStore(
				rocket.getThetaModel());
		this.aeroInterpolatorStore = new AeroInterpolatorStore(
				rocket.getAeroModel());
		this.aeroDataStore = new AeroDataStore(rocket.getStage(0)
				.getAeroDataLists());
		this.specImpulsStore = new SpecificImpulseModeStore();

		this.setDefaultRocket();

		this.rocketPanel = new RocketPanel(this);
		this.projectModel.addObserver(rocketPanel);
		this.projectModel.getRocket().addObserver(rocketPanel);

	}

	public void disableDeleteButtons() {
		// check Aero Data
		if (this.aeroDataStore.getModels().size() <= 1)
			this.rocketPanel.disableAeroDataDelete();

		// check Theta Data
		if (this.projectModel.getRocket().getThetaLists().size() <= 1)
			this.rocketPanel.disableThetaDataDelete();
	}

	private void setDefaultRocket() {
		this.projectModel.setRocket(this.rocketStore.getDefaultRocket());
	}

	public void updateRocket() {
		rocketPanel.updateValues();
	}

	public void addStage(int index) {
		projectModel.getRocket().addStage(index);
	}

	public void deleteStage(int index) {
		if (projectModel.getRocket().getStages().size() > 1)
			projectModel.getRocket().removeStage(index);
	}

	/**
	 * Should save the current Rocket
	 */
	public void saveCurrentRocket(File path) {
		RocketXMLOutputAdapter rocketOutPut = new RocketXMLOutputAdapter();
		try {
			rocketOutPut.writeData(projectModel.getRocket(),
					path.getAbsolutePath());
		} catch (IOException e) {
		}
	}

	public RocketPanel getRocketPanel() {
		return this.rocketPanel;
	}

	public void setEnabled(boolean b) {
		rocketPanel.setEnabled(b);
	}

	public String[] getThetaDataNamesArray() {
		List<ThetaList> thetalist = this.projectModel.getRocket()
				.getThetaLists();
		String[] ret = new String[thetalist.size()];

		for (int i = 0; i < thetalist.size(); i++) {
			ret[i] = thetalist.get(i).getName();
		}
		return ret;
	}

	public String[] getThetaCalculationModeNamesArray() {
		List<String> names = this.thetInterpolatorStore.getNames();
		String[] ret = new String[names.size()];

		for (int i = 0; i < names.size(); i++)
			ret[i] = names.get(i);

		return ret;
	}

	public String[] getAeroCalculationModeNamesArray() {
		List<String> names = this.aeroInterpolatorStore.getNames();
		String[] ret = new String[names.size()];

		for (int i = 0; i < names.size(); i++)
			ret[i] = names.get(i);

		return ret;
	}

	public String[] getAeroDataNamesArray() {
		List<String> aeroLists = this.aeroDataStore.getNames();
		String[] ret = new String[aeroLists.size()];

		for (int i = 0; i < aeroLists.size(); i++)
			ret[i] = aeroLists.get(i);

		return ret;
	}

	public String[] getRocketsNamesArray() {
		List<String> rockets = this.rocketStore.getNames();
		String[] ret = new String[rockets.size()];

		for (int i = 0; i < rockets.size(); i++)
			ret[i] = rockets.get(i);

		return ret;
	}

	public String[] getSpecificImpulsCalculationModeNamesArray() {
		List<String> modes = this.specImpulsStore.getNames();
		String[] ret = new String[modes.size()];

		for (int i = 0; i < modes.size(); i++)
			ret[i] = modes.get(i);

		return ret;
	}

	public List<StageBean> getRocketStagesList() {

		List<StageBean> ret = new ArrayList<StageBean>();
		List<RocketStage> stages = projectModel.getRocket().getStages();
		for (RocketStage rs : stages) {
			StageBean sb = new StageBean(rs.getIgniteTime(),
					rs.getRefeDiameter(), rs.getMass0(),
					rs.getSpecImpuGround(), rs.getSpecImpuVacuum(),
					rs.getFuelMassFlow(0), rs.getBurnTime(),
					rs.getBurnChamPressure(0), rs.getEpsilon(),
					rs.getCharacteristicVeleocity(),
					rs.getStageSeparationTime(), this.getAeroDataNamesArray());

			sb.setSelectedAeroData(rs.getAeroDataLists()
					.get(rs.getAeroDataListIndex()).getName());
			sb.setSelectedImpulsCalculationMode(rs.getSpecificImpulseModel()
					.getName());

			ret.add(sb);
		}

		return ret;
	}

	public String getCurrentRocketName() {
		return projectModel.getRocket().getName();
	}

	public List<String[]> getAeroDataLists() {
		List<String[]> ret = new ArrayList<String[]>();
		AeroDataList adl = this.projectModel
				.getRocket()
				.getStage(0)
				.getAeroDataLists()
				.get(this.projectModel.getRocket().getStage(0)
						.getAeroDataListIndex());

		for (AeroDataEntry ade : adl.getAeroDataList()) {
			String[] entry = new String[4];
			entry[0] = Double.toString(ade.mach);
			entry[1] = Double.toString(ade.CD0EngOFF);
			entry[2] = Double.toString(ade.CD0EngON);
			entry[3] = Double.toString(ade.CLalpha);

			ret.add(entry);
		}
		return ret;
	}

	public List<String[]> getChamberPressureAndFuelMassFlowList(int index) {
		List<String[]> data = new ArrayList<String[]>();
		List<DoubleVector2> chamberPressure = this.projectModel.getRocket()
				.getStage(index).getChamberPressureList();
		List<DoubleVector2> fuelMassFlow = this.projectModel.getRocket()
				.getStage(index).getFuelMassFlowList();

		for (int i = 0; i < chamberPressure.size(); i++) {
			data.add(new String[] {
					Double.toString(chamberPressure.get(i).getTime()),
					Double.toString(chamberPressure.get(i).getValue()),
					Double.toString(fuelMassFlow.get(i).getValue()) });
		}

		return data;
	}

	public List<String[]> getThetaDataList() {
		List<String[]> ret = new ArrayList<String[]>();
		ThetaList tl = this.projectModel.getRocket().getThetaLists()
				.get(this.projectModel.getRocket().getThetaListIndex());

		for (ThetaEntry te : tl.list) {
			String[] entry = new String[4];
			entry[0] = Double.toString(te.t);
			entry[1] = Double.toString(te.theta);

			ret.add(entry);

		}
		return ret;
	}

	/**
	 * @param index
	 *            referes to the index of the stage (in the list)
	 * @param text
	 *            encodes the new ignite time. Is / Needs-to-be pre-checked!
	 */
	public void setNewIgniteTime(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setIgniteTime(Double.valueOf(text));
	}

	public void setNewDiameter(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setDiameter(Double.valueOf(text));
	}

	public void setNewMass(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setStartMass(Double.valueOf(text));
		this.updateRocketMass();
	}

	private void updateRocketMass() {
		this.rocketPanel.setAbsoluteMass();
	}

	public void setNewBurntime(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setBurnTime(Double.valueOf(text));
	}

	public void setNewGroundImpuls(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setSpecImpuGround(Double.valueOf(text));
	}

	public void setNewVakuumImpuls(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setSpecImpuVacuum(Double.valueOf(text));
	}

	public void setNewEpsilon(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setEpsilon(Double.valueOf(text));
	}

	public void setNewCharacteristicVelocity(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setCharacteristicVeleocity(Double.valueOf(text));
	}

	public void setNewStageSeperationTime(int index, String text) {
		this.projectModel.getRocket().getStage(index)
				.setStageSeparationTime(Double.valueOf(text));

	}

	public void setNewRocket(String modelName) {
		// Match name to model:
		for (Rocket m : this.rocketStore.getRockets()) {
			if (m.getName().equals(modelName)) {
				this.aeroDataStore = new AeroDataStore(m.getStage(0)
						.getAeroDataLists());

				this.projectModel.getRocket().deleteObserver(rocketPanel);
				this.projectModel.setRocket(m);
				this.projectModel.getRocket().addObserver(rocketPanel);

				this.projectController.setNewCalculationRocket(m);

				return;
			}
		}
	}

	public void newRocket(String rocketName) {
		Rocket newRocket = this.rocketStore.createNewRocket(rocketName);

		this.projectModel.getRocket().deleteObserver(rocketPanel);
		this.projectModel.setRocket(newRocket);
		this.projectModel.getRocket().addObserver(rocketPanel);
	}

	public void setNewThetaCalculationMode(String modeName) {
		// Match name to model:
		for (ThetaModel m : this.thetInterpolatorStore.getModels()) {
			if (m.getName().equals(modeName)) {
				this.projectModel.getRocket().setThetaModel(m);
				return;
			}
		}

	}

	public void setNewAeroCalculationMode(String modeName) {
		// Match name to model:
		for (AeroModel m : this.aeroInterpolatorStore.getModels()) {
			if (m.getName().equals(modeName)) {
				this.projectModel.getRocket().setAeroModel(m);
				return;
			}
		}
	}

	public void setNewAeroData(String modeName, int index) {
		// Match name to model:
		for (AeroDataList m : this.aeroDataStore.getModels()) {
			if (m.getName().equals(modeName)) {
				this.projectModel.getRocket().setAeroData(m, index);
				return;
			}
		}
	}

	public void setNewSpecificImpulseCalculationMode(String modeName, int index) {
		// Match name to model:
		for (SpecificImpulseModel m : this.specImpulsStore.getModels()) {
			if (m.getName().equals(modeName)) {
				this.projectModel.getRocket()
						.setSpecificImpulseCalculationMode(m, index);
				return;
			}
		}

	}

	public void setNewThetaData(String modelName) {
		// Match name to model:
		for (ThetaList l : this.projectModel.getRocket().getThetaLists()) {
			if (l.getName().equals(modelName)) {
				this.projectModel.getRocket().setThetaData(l);
				return;
			}
		}
	}

	public void setNewFuelMassFlowVector(List<Double[]> validVector, int index) {
		LinkedList<DoubleVector2> vacuumImpVector = new LinkedList<DoubleVector2>();
		for (Double[] a : validVector) {
			vacuumImpVector.add(new DoubleVector2(a[0], a[1]));
		}

		this.projectModel.getRocket().getStage(index)
				.setFuelMassFlow(vacuumImpVector);
		this.updateRocket();
	}

	public void setNewAeroDataList(AeroDataList validVector) {
		this.projectModel.getRocket().getStage(0).addAeroDataList(validVector);

		if (this.aeroDataStore.getModels().size() > 1)
			rocketPanel.enableAeroDataDelete();

		aeroDataStore.addAeroDataList(validVector);
		rocketPanel.updateValues();
		this.disableDeleteButtons();

	}

	public void setNewChamberPressureAndFuelMassFlowVector(
			List<Double[]> validVector, int index) {
		LinkedList<DoubleVector2> chamberPressureVector = new LinkedList<DoubleVector2>();
		LinkedList<DoubleVector2> fuelMassFlowVector = new LinkedList<DoubleVector2>();
		for (Double[] a : validVector) {
			chamberPressureVector.add(new DoubleVector2(a[0], a[1]));
			fuelMassFlowVector.add(new DoubleVector2(a[0], a[2]));
		}

		this.projectModel.getRocket().getStage(index)
				.setChamberPressure(chamberPressureVector);
		this.projectModel.getRocket().getStage(index)
				.setFuelMassFlow(fuelMassFlowVector);
		this.updateRocket();
	}

	public void setNewThetaDataList(ThetaList newList) {
		for (ThetaList tl : this.projectModel.getRocket().getThetaLists()) {
			if (tl.getName().equals(newList.getName())) {
				this.projectModel.getRocket().replaceThetaDataList(tl, newList);
				rocketPanel.updateValues();
				this.disableDeleteButtons();
				return;
			}
		}

		this.projectModel.getRocket().addThetaDataList(newList);
		rocketPanel.updateValues();
		this.disableDeleteButtons();
	}

	public List<Double[]> checkAeroDataList(String name, List<String[]> data)
			throws InvalidDataException {
		// //1. Check if Name already exists
		// for(String n : aeroDataStore.getNames()){
		// if(n.equals(name))
		// throw new InvalidDataException();
		// }

		return checkDataList(data, new Comparator<Double[]>() {

			@Override
			public int compare(Double[] o1, Double[] o2) {
				if (o1[0] < o2[0])
					return -1;
				else if (o1[0] == o2[0])
					return 0;
				else
					return 1;
			}
		});
	}

	public List<Double[]> checkThetaDataList(String listName,
			List<String[]> data) throws InvalidDataException {
		// //1. Check if Name already exists
		// for(int i=0; i <
		// this.projectModel.getRocket().getThetaLists().size(); i++){
		// if( listName.equals(
		// this.projectModel.getRocket().getThetaLists().get(i).getName() ) )
		// throw new InvalidDataException();
		// }

		return checkDataList(data, new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				if (o1[0] < o2[0])
					return -1;
				else if (o1[0] == o2[0])
					return 0;
				else
					return 1;
			}
		});
	}

	private void removeNullLines(List<String[]> data) {
		boolean textLine;
		for (int i = 0; i < data.size(); i++) {
			textLine = false;
			for (int j = 0; j < data.get(i).length; j++) {
				if (!(data.get(i)[j] == null || data.get(i)[j].equals("")))
					textLine = true;
			}
			if (!textLine)
				data.remove(i--);
		}
	}

	private void checkForPairs(List<String[]> data) throws InvalidDataException {
		for (int i = 0; i < data.size(); i++) {
			try {
				for (int j = 0; j < data.get(i).length; j++)
					data.get(i)[j].equals("null");
			} catch (NullPointerException e) {

				throw new InvalidDataException();
			}
		}
	}

	private List<Double[]> convertToDouble(List<String[]> data)
			throws InvalidDataException {
		List<Double[]> transformedData = new ArrayList<Double[]>();
		for (String[] e : data) {
			try {
				Double[] entry = new Double[e.length];
				for (int i = 0; i < e.length; i++)
					entry[i] = Double.valueOf(e[i]);

				transformedData.add(entry);
			} catch (NumberFormatException ex) {
				throw new InvalidDataException();
			}
		}

		return transformedData;
	}

	private void checkForDoubledEntries(List<Double[]> data)
			throws InvalidDataException {
		for (int i = 0; i < data.size() - 1; i++) {
			if (data.get(i)[0] == data.get(i + 1)[0])
				throw new InvalidDataException();
		}
	}

	private List<Double[]> checkDataList(List<String[]> data,
			Comparator<Double[]> compare) throws InvalidDataException {
		removeNullLines(data);

		// 3. Check if there are always pairs of data.
		checkForPairs(data);

		// 4. Check if all Entries could be converted to Double
		List<Double[]> transformedData = convertToDouble(data);

		// 5.1 sort the transformed Data after its time
		Collections.sort(transformedData, compare);

		// 5.2 check, if there are doubled time:
		checkForDoubledEntries(transformedData);

		return transformedData;
	}

	public String getDefaultThetaCalculationModeName() {
		return this.thetInterpolatorStore.getDefaultModel().getName();
	}

	public String getDefaultAeroCalculationModeName() {
		return this.aeroInterpolatorStore.getDefaultModel().getName();
	}

	public String getDefaultThetaDataName() {
		return this.projectModel.getRocket().getThetaLists().get(0).getName(); // Since
																				// there
																				// is
																				// no
																				// Store
																				// for
																				// ThetaData,
																				// we
																				// will
																				// use
																				// this
																				// as
																				// default
	}

	public String getDefaultSpecificImpulsCalculationModeName() {
		return this.specImpulsStore.getDefaultModel().getName();
	}

	public String getDefaultAeroDataName() {
		return this.aeroDataStore.getDefaultModel().getName();
	}

	public String getDescription() {
		return this.projectModel.getRocket().getDescription();
	}

	public void setNewRocketDescription(String text) {
		this.projectModel.getRocket().setDescription(text);
	}

	public String getSelectedThetaDataListName() {
		return this.projectModel.getRocket().getThetaLists()
				.get(projectModel.getRocket().getThetaListIndex()).getName();
	}

	public String getSelectedAeroDataName(int index) {
		return this.projectModel
				.getRocket()
				.getStage(index)
				.getAeroDataLists()
				.get(this.projectModel.getRocket().getStage(index)
						.getAeroDataListIndex()).getName();
	}

	public void deleteThetaData(String s) {

		if (!s.equals("Default shot") && !s.equals("Gravity turn"))
			this.projectModel.rocket.deleteThetaData(s);

		disableDeleteButtons();
	}

	public void deleteAeroData(String s) {
		if (s.equals("Default AeroData")) // probhibit deletion of default aero
											// Table
			return;

		List<AeroDataList> ad = this.projectModel.getRocket().getStage(0)
				.getAeroDataLists();

		// find corresponding Object
		AeroDataList list = null;
		for (AeroDataList adl : ad) {
			if (adl.getName().equals(s)) {
				list = adl;
				break;
			}
		}

		// delete list:
		if (list != null) {
			this.aeroDataStore.deleteAeroDataList(list);
			this.projectModel.getRocket().deleteAeroData(list);
		}

		disableDeleteButtons();
	}

	public double getAbsoluteRocketMass() {
		this.projectModel.rocket.initStageSeparationParameters();
		return this.projectModel.rocket.getRocketMass();
	}
}
