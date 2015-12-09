package hr.fer.zemris.java.hw11.notepadpp.local;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class used to provide the program with the adequate Strings depending on the
 * active localization.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class LocalizationProvider extends AbstractLocalizationProvider {
	
	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider myLocalizationProvider;
	
	/**
	 * Class constructor. Constructor takes no arguments. Constructor is private because the class is
	 * a singleton.
	 */
	private LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.notepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Method used to acquire the only instance of this class.
	 * 
	 * @return returns the only instance of this class
	 */
	public static LocalizationProvider getInstance() {
		if(myLocalizationProvider == null) {
			myLocalizationProvider = new LocalizationProvider();
		}
		return myLocalizationProvider;
	}
	
	/**
	 * Method used to select the active language.
	 * 
	 * @param language currently active language
	 */
	public void setLanguage(String language) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.notepadpp.local.prijevodi", locale);
		fire();
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.AbstractLocalizationProvider#getString(java.lang.String)
	 */
	@Override
	public String getString(String key) {
		try {
			return new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return bundle.getString(key);
		}
	}
}
