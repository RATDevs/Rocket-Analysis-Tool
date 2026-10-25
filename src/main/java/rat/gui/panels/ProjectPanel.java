package rat.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import rat.controller.project.ProjectController;
import rat.gui.panels.tabPanels.TabCloseLabelGraph;
import rat.gui.panels.tabPanels.TabPanelMap;
import rat.gui.panels.tabPanels.TabPanelPlanetAndSettings;
import rat.gui.panels.tabPanels.TabPanelRocketAndCalculation;
import rat.gui.panels.tabPanels.results.TabPanelGraph;
import rat.gui.panels.tabPanels.results.TabPanelResults;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt, Nils Vi�mann
 */
@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {

	private ProjectController projectController;
	private JTabbedPane tab;
	private TabPanelPlanetAndSettings ps;
	private TabPanelRocketAndCalculation rc;
	private TabPanelMap mp;
	private TabPanelResults rp;
	private Map<TabPanelGraph, String[]> tpg_map;

	private List<TabPanelGraph> tpg_list;

	public ProjectPanel(ProjectController controller) {
		this.projectController = controller;
		this.tpg_list = new ArrayList<TabPanelGraph>();
		this.tpg_map = new HashMap<TabPanelGraph, String[]>();

		this.setLayout(new GridBagLayout());
		this.initComponents();
	}

	public ProjectController getProjectController() {
		return this.projectController;
	}

	private void initComponents() {
		GridBagConstraints gbc = new GridBagConstraints();

		tab = new JTabbedPane();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(tab, gbc);

		// Planet and General Settings
		ps = new TabPanelPlanetAndSettings(
				projectController.getPlanetController(),
				projectController.getSettingController());
		tab.addTab("Values (1)", ps);

		// Rocket and Calculation
		rc = new TabPanelRocketAndCalculation(
				projectController.getRocketController(),
				projectController.getCalculationController());
		tab.addTab("Values (2)", rc);

		// Maps
		mp = new TabPanelMap(this.projectController);
		this.projectController.setMapPanel(mp);
		tab.addTab("Map", mp);

		// Results
		rp = new TabPanelResults(this.projectController);
		this.projectController.setResultPanel(rp);
		tab.addTab("Results", rp);
		tab.setEnabledAt(3, false);
	}

	/**
	 * Creates the GraphTab
	 * 
	 * @param title
	 * @param x_axsis_data
	 * @param i
	 * @param y_axis_data
	 * @param j
	 */
	public void createGraphTab(String title, double[] x_axsis_data, String i,
			double[] y_axis_data, String j) {
		TabPanelGraph pg = new TabPanelGraph(i, j, x_axsis_data, y_axis_data,
				projectController);
		tpg_list.add(pg);
		tpg_map.put(pg, new String[] { i, j });
		this.tab.addTab("", pg);
		this.tab.setTabComponentAt(3 + tpg_list.size(), new TabCloseLabelGraph(
				pg, this, title));
		this.tab.setSelectedIndex(this.tab.getTabCount() - 1);
	}

	/**
	 * Enables the result Tab
	 */
	public void setResultsEnabled() {
		this.tab.setEnabledAt(3, true);
		this.tab.setSelectedIndex(3);
	}

	/**
	 * @return Returns the current Tab
	 */
	public JTabbedPane getTab() {
		return tab;
	}

	/**
	 * 
	 * @param tpg
	 *            The Tab that should be removed
	 */
	public void listRemoveTab(TabPanelGraph tpg) {
		tab.remove(tpg);
		tpg_list.remove(tpg);
	}

	/**
	 * updates the Tabtitles when languageChange occurs
	 */
	public void updateLanguage() {
		// rename Tabs
		tab.setTitleAt(0, GlobalConfig.getMessage("TabNameFirst"));
		tab.setTitleAt(1, GlobalConfig.getMessage("TabNameSecond"));
		tab.setTitleAt(2, GlobalConfig.getMessage("TabNameThird"));
		tab.setTitleAt(3, GlobalConfig.getMessage("TabNameFourth"));
		// rename Graphs
		for (int i = 0; i < tpg_list.size(); i++) {
			System.out.println(i + 4);
			TabPanelGraph tpg = tpg_list.get(i);
			tpg.updateLanguage();
			String[] title = tpg_map.get(tpg);
			System.out.println(title[0]);
			((TabCloseLabelGraph) tab.getTabComponentAt(i + 4))
					.updateTitleAndToolTop(GlobalConfig.getMessage(title[0])
							+ " / " + GlobalConfig.getMessage(title[1]));
		}

		// update Panels
		ps.updateLanguage();
		rc.updateLanguage();
		rp.updateLanguage();
	}

}
