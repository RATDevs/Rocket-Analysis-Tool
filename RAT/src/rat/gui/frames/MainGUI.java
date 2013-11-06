package rat.gui.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import rat.controller.MainController;
import rat.controller.project.ProjectController;
import rat.exceptions.noProjectSelectedException;
import rat.gui.StaticVariables;
import rat.gui.menu.mainFrameElements.MenuBar;
import rat.gui.panels.ProjectPanel;
import rat.gui.panels.tabPanels.TabPanelLabel;
import rat.language.GlobalConfig;

/**
 * Creates and controls the MainGui
 * 
 * @author Manuel Schmidt, Nils Vißmann
 * 
 */
public class MainGUI {

	private static final int Y_START_SIZE = 600;
	private static final int X_START_SIZE = 1200;
	private static final String WINDOW_TITLE = "RAT - Rocket Analysis Tool";
	private static final Dimension WINDOW_MINIMUM_SIZE = new Dimension(800, 400);

	private JFrame frame;
	private JTabbedPane tabbedPane;

	private MenuBar menuBar;
	private ArrayList<ProjectPanel> projectPanelList;
	private int numberOfCalculations = 0;

	private MainController controller;

	public MainGUI(MainController mainController) {
		this.controller = mainController;
		this.projectPanelList = new ArrayList<ProjectPanel>();

		this.frame = createMainFrame();

		this.initComponents();
	}

	/**
	 * Creates a frame for the MainFrame to display and does some general
	 * settings
	 * 
	 * @return Returns the JFrame that holds the mainGUI of the Application
	 */
	private JFrame createMainFrame() {
		JFrame ret = new JFrame();

		ret.setMinimumSize(WINDOW_MINIMUM_SIZE);
		ret.setSize(X_START_SIZE, Y_START_SIZE);

		ret.setTitle(WINDOW_TITLE);

		ret.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		ret.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				controller.askForClose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		ret.setLayout(new GridBagLayout());

		ret.setVisible(true);

		return ret;
	}

	/**
	 * Initializes the standard-components of the MainGUI Like the menuBar or
	 * the TabPane
	 */
	private void initComponents() {
		// MenuBar
		this.menuBar = new MenuBar(controller);
		this.frame.setJMenuBar(this.menuBar);

		// TabBar
		this.tabbedPane = new JTabbedPane();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = StaticVariables.INIT_X_SIZE_MAINFRAME; // TODO: Isn't it
															// easier to have
															// those constants
															// in this class and
															// delete the
															// StaticVariable-Class?
		gbc.ipady = StaticVariables.INIT_Y_SIZE_MAINFRAME;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.frame.add(this.tabbedPane, gbc);
	}

	/**
	 * Add a "new Calculation" Tab
	 * 
	 * @param projectController
	 *            Project-Controller for the new Calculation-Tab
	 */
	public void createCalculationTab(ProjectController projectController) {

		ProjectPanel tab = projectController.getProjectPanel();
		this.projectPanelList.add(tab);
		this.tabbedPane.addTab(" ", tab);
		this.tabbedPane.setTabComponentAt(
				numberOfCalculations,
				new TabPanelLabel(this, tab, tabbedPane, GlobalConfig
						.getMessage("CalculationTabName")
						+ " "
						+ (this.numberOfCalculations + 1)));

		this.tabbedPane.addTab("", new JPanel());
		JPanel addPanel = new JPanel();
		addPanel.add(new JLabel("+"));
		addPanel.addMouseListener(new addCalculation());
		this.tabbedPane
				.setTabComponentAt(++this.numberOfCalculations, addPanel);
	}

	public void addCalculation(ProjectController pc) {
		tabbedPane.remove(numberOfCalculations);
		createCalculationTab(pc);
		tabbedPane.setSelectedIndex(numberOfCalculations - 1);
	}

	/**
	 * Repacks the MainGUI
	 */
	public void repack() {
		frame.pack();
	}

	/**
	 * returns selected ProjectPanel
	 */
	public ProjectPanel getSelectedProjectPanel()
			throws noProjectSelectedException {
		if (tabbedPane.getSelectedComponent() instanceof ProjectPanel)
			return (ProjectPanel) tabbedPane.getSelectedComponent();
		throw new noProjectSelectedException();
	}

	/**
	 * removes the closed calculation
	 */
	public void removeCalculationTab(ProjectPanel projectPanel) {
		int index = projectPanelList.indexOf(projectPanel);
		System.out.println(index);
		tabbedPane.remove(projectPanel);
		projectPanelList.remove(index);
		numberOfCalculations--;
		for (int i = index; i < tabbedPane.getTabCount() - 1; i++) {
			((TabPanelLabel) tabbedPane.getTabComponentAt(i))
					.setTitle(GlobalConfig.getMessage("CalculationTabName")
							+ " " + (i + 1));
		}
	}

	/**
	 * adds an Calculation
	 * 
	 */
	private class addCalculation implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			/* Remove old "+" Tab, add a new one and afterwards add a new "+"
			 * Tab */
			controller.addNewCalculation();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	/**
	 * updates the language
	 */
	public void updateLanguage() {
		for (int i = 0; i < tabbedPane.getTabCount() - 1; i++) {
			((TabPanelLabel) tabbedPane.getTabComponentAt(i))
					.setTitle(GlobalConfig.getMessage("CalculationTabName")
							+ " " + (i + 1));
		}
	}
}
