package rat.controller.dataIO;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import rat.calculation.math.DoubleVector2;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.aero.AeroDataEntry;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.theta.ThetaEntry;
import rat.calculation.rocket.theta.ThetaList;

/**
 * Class that loads Rocket from its XML-Files
 * 
 * @author Nils Vissman
 */
public class RocketXMLInputAdapter {

	// Version to seperate between different XML-Versions
	protected static final String ProgramVersion = "1.0";

	/**
	 * Creates a Rocket from a file
	 * 
	 * @param path
	 *            Path to the Rocket-XML-File
	 * 
	 * @return A Rocket-Object if successful, or null
	 */
	public static Rocket getRocketFromFile(String path) {

		if (!isPathOk(path)) {
			return null;
		}

		Rocket rocket = new Rocket();
		File rocketFile = new File(path);

		SAXBuilder builder = new SAXBuilder();
		try {
			Document rocketXML = builder.build(rocketFile);
			Element root = rocketXML.getRootElement();

			// Check Version
			Element version = root.getChild("Version");
			if (!version.getText().equals(ProgramVersion)) {
				return null;
			}

			Element name = root.getChild("Name");
			rocket.setName(name.getText());

			Element description = root.getChild("Description");
			rocket.setDescription(description.getText());

			// Create the stages from the XML-File
			Element stages = root.getChild("Stages");
			LinkedList<RocketStage> rocketStages = new LinkedList<RocketStage>();
			for (Element stage : stages.getChildren()) {
				RocketStage rocketStage = new RocketStage(parseInteger(stage
						.getAttributeValue("id")));

				rocketStage.setRefeDiameter(parseDouble(stage
						.getChildText("RefeDiameter")));
				rocketStage.setMass0(parseDouble(stage.getChildText("Mass")));

				rocketStage.setSpecImpuGround(parseDouble(stage
						.getChildText("SpecImpuls0")));
				rocketStage.setSpecImpuVacuum(parseDouble(stage
						.getChildText("SpecImpulsVacu")));

				Element fuelMassFlow = stage.getChild("FuelMassFlow");
				LinkedList<DoubleVector2> fuelMassFlowList = new LinkedList<DoubleVector2>();
				for (Element listEntry : fuelMassFlow.getChildren()) {
					double time = parseDouble(listEntry
							.getAttributeValue("time"));
					double value = parseDouble(listEntry
							.getAttributeValue("value"));

					DoubleVector2 entry = new DoubleVector2(time, value);
					fuelMassFlowList.add(entry);
				}
				rocketStage.setFuelMassFlowList(fuelMassFlowList);

				Element burnChamPressure = stage
						.getChild("BurnChamberPressure");
				LinkedList<DoubleVector2> burnChamPressureList = new LinkedList<DoubleVector2>();
				for (Element listEntry : burnChamPressure.getChildren()) {
					double time = parseDouble(listEntry
							.getAttributeValue("time"));
					double value = parseDouble(listEntry
							.getAttributeValue("value"));

					DoubleVector2 entry = new DoubleVector2(time, value);
					burnChamPressureList.add(entry);
				}
				rocketStage.setBurnChamPressureList(burnChamPressureList);

				rocketStage.setBurnTime(parseDouble(stage
						.getChildText("BurnTime")));
				rocketStage.setStageIgnitionTime(parseDouble(stage
						.getChildText("StageIgnitionTime")));
				rocketStage.setStageSeparationTime(parseDouble(stage
						.getChildText("StageSeparationTime")));
				rocketStage
						.setEpsilon(parseDouble(stage.getChildText("Epsilon")));
				String therDeg = stage.getChildText("TherDegreeOfFreedom");
				if (therDeg != null) {
					rocketStage.setTherDegrOfFreedom(parseDouble(therDeg));
				}
				String carVelo = stage.getChildText("CharateristicVelocity");
				if (carVelo != null) {
					rocketStage.setCharacteristicVeleocity(parseDouble(carVelo));
				}
				rocketStage.setEta0Bull(parseDouble(stage
						.getChildText("ETA0Bull")));
				rocketStage.setPrecBull(parseDouble(stage
						.getChildText("PrecisionBull")));
				rocketStage.setAeroKFactor(parseDouble(stage
						.getChildText("kFactor")));
				rocketStage.setBullIterations(parseInteger(stage
						.getChildText("BullIterations")));

				// Get Aero-Table
				Element aeroTables = stage.getChild("AeroTables");
				for (Element aeroTable : aeroTables.getChildren()) {
					LinkedList<AeroDataEntry> stageAeroTable = new LinkedList<AeroDataEntry>();
					AeroDataList list = new AeroDataList(aeroTable.getChild(
							"AeroTableName").getValue());

					for (Element aeroTableEntry : aeroTable.getChildren("line")) {
						double mach = parseDouble(aeroTableEntry
								.getAttributeValue("mach"));
						double cd0EngOn = parseDouble(aeroTableEntry
								.getAttributeValue("CD0EngON"));
						double cd0EngOff = parseDouble(aeroTableEntry
								.getAttributeValue("CD0EngOFF"));
						double clAlpha = parseDouble(aeroTableEntry
								.getAttributeValue("CLalpha"));

						AeroDataEntry entry = new AeroDataEntry(mach, cd0EngOn,
								cd0EngOff, clAlpha);
						stageAeroTable.add(entry);
					}

					list.setAeroDataList(stageAeroTable);
					rocketStage.getAeroDataLists().add(list);
				}

				// add rocket stage
				rocketStages.add(rocketStage);
			}

			rocket.setStagesList(rocketStages);

			// Create the PhiTables from the XML-File
			Element phiTables = root.getChild("ThetaTables");
			for (Element thetaTable : phiTables.getChildren()) {
				String thetaTableName = thetaTable.getAttributeValue("Name");
				ThetaList thetaDataTable = new ThetaList(thetaTableName);
				for (Element entry : thetaTable.getChildren()) {
					double t = parseDouble(entry.getAttributeValue("t"));
					double phi = parseDouble(entry.getAttributeValue("theta"));
					ThetaEntry rocketPhiEntry = new ThetaEntry(t, phi);
					thetaDataTable.list.add(rocketPhiEntry);
				}
				rocket.addThetaDataList(thetaDataTable);
			}

		} catch (JDOMException e) {
			System.out.println("[ERROR] XML-File seems not to mach our Format");
			return null;
		} catch (IOException e) {
			System.out.println("[ERROR] IO-Operation failed");
			return null;
		}

		return rocket;
	}

	/**
	 * Checks the path of a given Rocket-File
	 * 
	 * @param p
	 *            Path to check
	 * @return "True" if path is OK, else "false"
	 */
	private static boolean isPathOk(String p) {
		// TODO: Check whether path points to a file
		return true;
	}

	private static double parseDouble(String value) {
		return Double.parseDouble(value);
	}

	private static int parseInteger(String value) {
		return Integer.parseInt(value);
	}
}
