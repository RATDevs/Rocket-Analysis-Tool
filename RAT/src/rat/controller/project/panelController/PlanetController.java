package rat.controller.project.panelController;

import java.util.List;

import rat.calculation.Project;
import rat.calculation.planet.Planet;
import rat.calculation.planet.atmosphere.AtmosphereModel;
import rat.calculation.planet.gravitation.GravitationModel;
import rat.controller.MainController;
import rat.controller.objectStores.AtmosphereStore;
import rat.controller.objectStores.GravitationStore;
import rat.controller.project.GeneralProjectController;
import rat.gui.panels.tabPanels.values.PlanetPanel;

/**
 * Controller for the Planet Panel.
 * 
 * @author Nils Vißmann
 * 
 */

public class PlanetController extends GeneralProjectController {

	private PlanetPanel planetPanel;

	private GravitationStore gravStore;
	private AtmosphereStore atStore;

	public PlanetController(Project project, MainController mainController) {
		super(mainController, project);

		this.gravStore = new GravitationStore();
		this.atStore = new AtmosphereStore();

		lastInitSteps();
	}

	public PlanetController(Project project, MainController mainController,
			Planet planet) {
		super(mainController, project);

		this.gravStore = new GravitationStore(planet.gravitationModel);
		this.atStore = new AtmosphereStore(planet.atmosphereModel);

		lastInitSteps();

	}

	private void lastInitSteps() {
		this.projectModel.getPlanet().setAtmosphereModel(
				atStore.getDefaultModel());
		this.projectModel.getPlanet().setGravityModel(
				gravStore.getDefaultModel());

		this.planetPanel = new PlanetPanel(this);
	}

	public PlanetPanel getPlanetPanel() {
		return this.planetPanel;
	}

	public void updateValues() {
		Planet planet = projectModel.getPlanet();

		planetPanel.initValues(planet.getMajorAxis(), planet.getMinorAxis(),
				planet.getFlattering(), planet.getFirstEccentricity(),
				planet.getGravitationalConstant(),
				planet.getLinearEccentricity(), planet.getOmega());
	}

	public void setEnabled(boolean b) {
		planetPanel.setEnabled(b);
	}

	public void setNewGravitationModel(String modelName) {
		// Match name to model:
		for (GravitationModel m : this.gravStore.getModels()) {
			if (m.getName().equals(modelName)) {
				this.projectModel.planet.setGravityModel(m);
				return;
			}
		}
	}

	public String[] getGravitationModelNamesArray() {
		List<String> names = this.gravStore.getNames();
		String[] ret = new String[names.size()];

		for (int i = 0; i < names.size(); i++)
			ret[i] = names.get(i);

		return ret;
	}

	public void setNewAtmosphereModel(String modelName) {
		// Match name to model:
		for (AtmosphereModel m : this.atStore.getModels()) {
			if (m.getName().equals(modelName)) {
				this.projectModel.planet.setAtmosphereModel(m);
				return;
			}
		}
	}

	public String[] getAtmosphereModelNamesArray() {
		List<String> names = this.atStore.getNames();
		String[] ret = new String[names.size()];

		for (int i = 0; i < names.size(); i++)
			ret[i] = names.get(i);

		return ret;
	}

	public String getGravitationDefaultModelName() {
		return gravStore.getDefaultModel().getName();
	}

	public String getAtmosphereDefaultModelName() {
		return atStore.getDefaultModel().getName();
	}

	public void setRotationEnabled(boolean b) {
		this.projectModel.getPlanet().setRotationEnabled(b);
	}
}
