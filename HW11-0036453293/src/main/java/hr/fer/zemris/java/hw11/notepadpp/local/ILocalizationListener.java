package hr.fer.zemris.java.hw11.notepadpp.local;

/**
 * Interface used to define the only method required to implement a Localization Listener.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface ILocalizationListener {
	
	/**
	 * Method that will be called by the subject to inform all of it's listeners about the 
	 * localization change.
	 */
	void localizationChanged();
}
