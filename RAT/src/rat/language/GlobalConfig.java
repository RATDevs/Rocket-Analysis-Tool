package rat.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Manuel Schmidt, Nils Viﬂmann
 * 
 */
public class GlobalConfig {

	// Set Default Locale
	private static Locale globalLocale = Locale.US;

	public static void setGlobalLocale(Locale l) {
		globalLocale = l;
	}

	public static Locale getGlobalLocale() {
		return globalLocale;
	}

	public static String getMessage(String name) {
		ResourceBundle b = ResourceBundle.getBundle("MessageBundle",
				globalLocale);

		return b.getString(name);
	}

}
