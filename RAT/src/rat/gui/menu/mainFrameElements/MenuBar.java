package rat.gui.menu.mainFrameElements;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import rat.controller.MainController;
import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	private MainController controller;
	Map<JMenuItem, String[]> menuItems;
	Map<JMenu, String[]> menus;

	public MenuBar(MainController c) {
		this.controller = c;
		menus = new HashMap<JMenu, String[]>();
		menuItems = new HashMap<JMenuItem, String[]>();
		createMenu();
		updateLanguage();
	}

	/**
	 * initiates the MenuBar and it's Items
	 */
	private void createMenu() {
		JMenuItem menuItem;
		JMenu menu;

		menu = new JMenu();
		menus.put(menu, new String[] { "CalculationMenuBarName",
				"CalculationMenuBarToolTip" });
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "NewCalculationMenuName",
				"NewCalculationMenuToolTip" });
		menuItem.addActionListener(new newCalculationListener());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "LoadCalculationMenuName",
				"LoadCalculationMenuToolTip" });
		menuItem.addActionListener(new loadCalculationListener());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				KeyEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "SaveCalculationMenuName",
				"SaveCalculationMenuToolTip" });
		menuItem.addActionListener(new saveCalculationListener());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "CloseCalculationMenuName",
				"CloseCalculationMenuToolTip" });
		menuItem.addActionListener(new removeCalculationTabListener());
		menu.add(menuItem);
		menu.add(new JSeparator());
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "ExitMenuName",
				"ExitMenuToolTip" });
		menuItem.addActionListener(new exitListener());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_MASK));
		menu.add(menuItem);
		this.add(menu);

		menu = new JMenu();
		menus.put(menu, new String[] { "LanguageMenuBarName",
				"LanguageMenuBarToolTip" });
		menuItem = new JMenuItem("English");
		menuItem.addActionListener(new englishLanguageListener());
		menu.add(menuItem);
		menuItem = new JMenuItem("Deutsch");
		menuItem.addActionListener(new germanLanguageListener());
		menu.add(menuItem);
		this.add(menu);

		menu = new JMenu();
		menus.put(menu,
				new String[] { "HelpMenuBarName", "HelpMenuBarToolTip" });
		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "HelpMenuName",
				"HelpMenuToolTip" });
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuItem.addActionListener(new linkListener(
				"https://code.google.com/p/rocketanalysistool/w/list"));
		menu.add(menuItem);		

		menuItem = new JMenuItem();
		menuItems.put(menuItem,
 new String[] { "ReportBug",
 "ReportBugToolTip" });
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		menuItem.addActionListener(new linkListener(
				"https://code.google.com/p/rocketanalysistool/issues/list"));
		menu.add(menuItem);

		menuItem = new JMenuItem();
		menuItems.put(menuItem, new String[] { "AboutName", "AboutToolTip" });
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		menu.add(menuItem);
		menuItem.addActionListener(new AboutListener());

		this.add(menu);
		
		
	}

	/**
	 * called when other language is selected
	 */
	private void updateLanguage() {
		for (JMenu m : menus.keySet()) {
			m.setText(GlobalConfig.getMessage(menus.get(m)[0]));
			m.setToolTipText(GlobalConfig.getMessage(menus.get(m)[1]));
		}
		for (JMenuItem m : menuItems.keySet()) {
			m.setText(GlobalConfig.getMessage(menuItems.get(m)[0]));
			m.setToolTipText(GlobalConfig.getMessage(menuItems.get(m)[1]));
		}
	}

	/**
	 * Called when About is selected in menuBar
	 */
	private class AboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String aboutTitle = "About RAT";
			String aboutMessage = "RAT - Rocket Analysis Tool\n"
					+ "Version: 0.9.1\n\n"
					+ "This programm is FREE and OPEN SOURCE.\n"
					+ "Published with the Apache License Version 2.0."// TODO:
					+ "\nhttps://code.google.com/p/rocketanalysistool/\n"
					+ "\nCopyright \u00a9 2014\nGerhard Mesch, Michael Sams, Manuel Schmidt, Nils Vissmann\n"
					+ "with friendly support from Schmucker Technologie\n";
			JOptionPane.showMessageDialog(null, aboutMessage, aboutTitle,
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Called when Help is selected in menuBar
	 */
	private class linkListener implements ActionListener {

		private String uriName;

		public linkListener(String uri) {
			this.uriName = uri;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			URI uri;
			try {
				uri = new URI(uriName);
				open(uri);
			} catch (URISyntaxException e1) {
			}

		}

		private void open(URI uri) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e) {
				}
			} else {

			}
		}
	}

	/**
	 * Called when newCalculation is selected in menuBar
	 */
	private class newCalculationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.addNewCalculation();
		}
	}

	/**
	 * Called when removeCalculation is selected in menuBar
	 */
	private class removeCalculationTabListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.removeSelectedCalculation();
		}
	}

	/**
	 * Called when loadCalculation is selected in menuBar
	 */
	private class loadCalculationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.loadCalculation();
		}
	}

	/**
	 * Called when saveCalculation is selected in menuBar
	 */
	private class saveCalculationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.saveSelectedCalculation();
		}
	}

	/**
	 * Called when exit is selected in menuBar
	 */
	private class exitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.askForClose();
		}
	}

	/**
	 * Called when English is selected as Language
	 */
	private class englishLanguageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			GlobalConfig.setGlobalLocale(Locale.US);
			// update locally
			updateLanguage();
			// update rest
			controller.updateLanguage();

		}
	}

	/**
	 * Called when German is selected as Language
	 */
	private class germanLanguageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			GlobalConfig.setGlobalLocale(Locale.GERMANY);
			// update locally
			updateLanguage();
			// update rest
			controller.updateLanguage();
		}
	}

}
