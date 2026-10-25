package rat.controller.dataIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import rat.calculation.math.DoubleVector2;
import rat.calculation.rocket.Rocket;
import rat.calculation.rocket.RocketStage;
import rat.calculation.rocket.aero.AeroDataEntry;
import rat.calculation.rocket.aero.AeroDataList;
import rat.calculation.rocket.theta.ThetaEntry;
import rat.calculation.rocket.theta.ThetaList;

/**
 * Rocket XML Output adapter.
 * 
 * @author Nils Vissmann
 */
public class RocketXMLOutputAdapter implements IDataOutputAdapter {

	@Override
	public void writeData(Object Data, String path) throws IOException {

		/* Sanity checks first. If it really is a rocket and if the path is a
		 * valid one */
		if (!(Data instanceof Rocket))
			throw new ClassCastException("Object is no Rocket");

		if (!isPathOK(path))
			throw new FileNotFoundException("Cannot save to that Path");

		Rocket rocket = (Rocket) Data;
		File outputFile = new File(path);

		Document rocketXML = new Document(new Element("MyRocket"));
		Element root = rocketXML.getRootElement();

		Element name = new Element("Name");
		name.addContent(rocket.getName());
		root.addContent(name);

		Element version = new Element("Version");
		version.addContent(RocketXMLInputAdapter.ProgramVersion);
		root.addContent(version);

		Element description = new Element("Description");
		description.addContent(rocket.getDescription());
		root.addContent(description);

		// Get Stages from Rocket and transfer the information into
		// XML-Elements:
		Element stages = new Element("Stages");
		root.addContent(stages);

		for (int i = 0; i < rocket.getStages().size(); i++) {
			RocketStage rocketStage = rocket.getStages().get(i);

			Element stage = new Element("Stage");
			stage.setAttribute("id", Integer.toString(i));
			stages.addContent(stage);

			Element refeDiameter = new Element("RefeDiameter");
			refeDiameter.addContent(Double.toString(rocketStage
					.getRefeDiameter()));
			stage.addContent(refeDiameter);

			Element mass = new Element("Mass");
			mass.addContent(Double.toString(rocketStage.getMass0()));
			stage.addContent(mass);

			Element specImpu0 = new Element("SpecImpuls0");
			specImpu0.addContent(Double.toString(rocketStage
					.getSpecImpuGround()));
			stage.addContent(specImpu0);

			Element specImpuVacu = new Element("SpecImpulsVacu");
			specImpuVacu.addContent(Double.toString(rocketStage
					.getSpecImpuVacuum()));
			stage.addContent(specImpuVacu);

			Element fuelMassFlow = new Element("FuelMassFlow");
			stage.addContent(fuelMassFlow);
			for (int j = 0; j < rocketStage.getFuelMassFlowList().size(); j++) {
				Element element = new Element("line");
				fuelMassFlow.addContent(element);
				DoubleVector2 entry = rocketStage.getFuelMassFlowList().get(j);
				element.setAttribute("time", Double.toString(entry.t));
				element.setAttribute("value", Double.toString(entry.value));
			}

			Element burnTime = new Element("BurnTime");
			burnTime.addContent(Double.toString(rocketStage.getBurnTime()));
			stage.addContent(burnTime);

			Element stageIgnitionTime = new Element("StageIgnitionTime");
			stageIgnitionTime.addContent(Double.toString(rocketStage
					.getStageIgniteTime()));
			stage.addContent(stageIgnitionTime);

			Element stageSeparationTime = new Element("StageSeparationTime");
			stageSeparationTime.addContent(Double.toString(rocketStage
					.getStageSeparationTime()));
			stage.addContent(stageSeparationTime);

			Element burnChamPressure = new Element("BurnChamberPressure");
			stage.addContent(burnChamPressure);
			for (int j = 0; j < rocketStage.getBurnChamPressureList().size(); j++) {
				Element element = new Element("line");
				burnChamPressure.addContent(element);
				DoubleVector2 entry = rocketStage.getBurnChamPressureList()
						.get(j);
				element.setAttribute("time", Double.toString(entry.t));
				element.setAttribute("value", Double.toString(entry.value));
			}

			Element epsilon = new Element("Epsilon");
			epsilon.addContent(Double.toString(rocketStage.getEpsilon()));
			stage.addContent(epsilon);

			Element therDegreeOfFreedom = new Element("TherDegreeOfFreedom");
			therDegreeOfFreedom.addContent(Double.toString(rocketStage
					.getTherDegrOfFreedom()));
			stage.addContent(therDegreeOfFreedom);

			Element charateristicVelocity = new Element("CharateristicVelocity");
			charateristicVelocity.addContent(Double.toString(rocketStage
					.getCharacteristicVeleocity()));
			stage.addContent(charateristicVelocity);

			Element eta0Bull = new Element("ETA0Bull");
			eta0Bull.addContent(Double.toString(rocketStage.getEta0Bull()));
			stage.addContent(eta0Bull);

			Element precBull = new Element("PrecisionBull");
			precBull.addContent(Double.toString(rocketStage.getPrecBull()));
			stage.addContent(precBull);

			Element bullIterations = new Element("BullIterations");
			bullIterations.addContent(Integer.toString(rocketStage
					.getBullIterations()));
			stage.addContent(bullIterations);

			Element kFac = new Element("kFactor");
			kFac.addContent(Double.toString(rocketStage.getAeroKFactor()));
			stage.addContent(kFac);

			Element aeroTables = new Element("AeroTables");
			stage.addContent(aeroTables);
			for (AeroDataList aeroList : rocketStage.getAeroDataLists()) {
				if (aeroList.getName().equals("Default AeroData"))
					continue;

				// Print the Aerotable to the Stage
				Element aerotable = new Element("AeroTable");
				aeroTables.addContent(aerotable);
				// add table name
				Element ename = new Element("AeroTableName");
				ename.addContent(aeroList.getName());
				aerotable.addContent(ename);

				// iterate through the entries
				for (int j = 0; j < aeroList.getAeroDataList().size(); j++) {
					Element element = new Element("line");
					aerotable.addContent(element);

					AeroDataEntry entry = aeroList.getAeroDataList().get(j);

					element.setAttribute("mach", Double.toString(entry.mach));
					element.setAttribute("CD0EngON",
							Double.toString(entry.CD0EngON));
					element.setAttribute("CD0EngOFF",
							Double.toString(entry.CD0EngOFF));
					element.setAttribute("CLalpha",
							Double.toString(entry.CLalpha));
				}
			}
		}

		// Get the Phi-table(s) and put them into the XML
		Element phiTables = new Element("ThetaTables");
		root.addContent(phiTables);

		for (ThetaList pl : rocket.getThetaLists()) {
			if (pl.thetaListName.equals("Gravity turn")
					|| pl.thetaListName.equals("Default shot")
					|| pl.thetaListName.equals("Satellite launch try"))
				continue;
			Element thetaTable = new Element("ThetaTable");
			phiTables.addContent(thetaTable);

			thetaTable.setAttribute("Name", pl.thetaListName);

			for (int i = 0; i < pl.list.size(); i++) {
				ThetaEntry pe = pl.list.get(i);

				Element entry = new Element("Entry");
				entry.setAttribute("t", Double.toString(pe.t));
				entry.setAttribute("theta", Double.toString(pe.theta));

				thetaTable.addContent(entry);
			}
		}
		FileOutputStream fos = new FileOutputStream(outputFile);

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(rocketXML, fos);

		// close Filestream
		fos.close();
	}

	/**
	 * Check if the
	 * 
	 * @param path
	 *            is one where we can write our File.
	 * 
	 * @return
	 */
	private boolean isPathOK(String path) {
		// TODO
		return true;
	}

}
