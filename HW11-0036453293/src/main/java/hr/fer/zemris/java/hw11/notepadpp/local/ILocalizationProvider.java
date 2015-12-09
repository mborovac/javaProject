package hr.fer.zemris.java.hw11.notepadpp.local;

/**
 * Interface used to define method needed to implement a LocalizationProvider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface ILocalizationProvider {
	
	/**
	 * Method used to register a listener to the localization provider.
	 * 
	 * @param l listener that will be added.
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Method used to unregister a listener from the localization provider.
	 * 
	 * @param l listener that will be added.
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Method used to acquire the localization specific String based on the given key.
	 * 
	 * @param key
	 * @return
	 */
	String getString(String key);
}
