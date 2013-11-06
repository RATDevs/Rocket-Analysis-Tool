package rat.gui.panels.tabPanels.results;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import rat.language.GlobalConfig;

/**
 * @author Manuel Schmidt
 */
@SuppressWarnings("serial")
public class ImportantResultPanel extends JPanel {

	private JLabel _lMaxSpeed;
	private String _lMaxSpeedValue;
	private JLabel _lMaxRange;
	private String _lMaxRangeValue;
	private JLabel _lMaxHeight;
	private String _lMaxHeightValue;
	private JLabel _lDuration;
	private String _lDurationValue;
	private DecimalFormat outputFormat;

	public ImportantResultPanel() {
		setLayout(new GridBagLayout());
		outputFormat = new DecimalFormat("0.000");
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		initComponents();
	}

	private void initComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		_lMaxSpeed = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(_lMaxSpeed, gbc);

		_lMaxHeight = new JLabel();
		gbc.gridy = 1;
		add(_lMaxHeight, gbc);

		_lMaxRange = new JLabel();
		gbc.gridy = 2;
		add(_lMaxRange, gbc);

		_lDuration = new JLabel();
		gbc.gridy = 3;
		add(_lDuration, gbc);
	}

	public void setMaxSpeed(double speed) {

		this._lMaxSpeedValue = outputFormat.format(speed);
		this._lMaxSpeed.setText(GlobalConfig.getMessage("ResultsPanelMaxSpeed")
				+ _lMaxSpeedValue + " m/s");
	}

	public void setRange(double range) {
		this._lMaxRangeValue = outputFormat.format(range / 1000);
		this._lMaxRange.setText(GlobalConfig.getMessage("ResultsPanelRange")
				+ _lMaxRangeValue + " km");

	}

	public void setMaxHeight(double height) {
		this._lMaxHeightValue = outputFormat.format(height / 1000);
		this._lMaxHeight
				.setText(GlobalConfig.getMessage("ResultsPanelMaxHeight")
						+ _lMaxHeightValue + " km");
	}

	public void setDuration(double duration) {
		this._lDurationValue = outputFormat.format(duration);
		this._lDuration.setText(GlobalConfig.getMessage("ResultsPanelDuration")
				+ _lDurationValue + " s");
	}

	public void updateLanguage() {
		if (_lMaxHeightValue != null && _lMaxRangeValue != null
				&& _lMaxHeightValue != null) {
			this._lMaxSpeed.setText(GlobalConfig
					.getMessage("ResultsPanelMaxSpeed")
					+ _lMaxSpeedValue
					+ " m/s");
			this._lMaxRange.setText(GlobalConfig
					.getMessage("ResultsPanelRange") + _lMaxRangeValue + " km");
			this._lMaxHeight.setText(GlobalConfig
					.getMessage("ResultsPanelMaxHeight")
					+ _lMaxHeightValue
					+ " km");
			this._lDuration.setText(GlobalConfig
					.getMessage("ResultsPanelDuration")
					+ _lDurationValue
					+ " s");
		}
	}

}
