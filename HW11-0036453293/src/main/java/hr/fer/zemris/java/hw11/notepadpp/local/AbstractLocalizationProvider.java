package hr.fer.zemris.java.hw11.notepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class serving as a localization provider. It returns the localization specific Strings based on
 * the given key.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	private List<ILocalizationListener> listeners;
	
	/**
	 * Class constructor. Constructor takes no arguments.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationProvider#addLocalizationListener(
	 * hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener)
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if(!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationProvider#removeLocalizationListener(
	 * hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener)
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Method used to inform all the listeners about the localization change.
	 */
	public void fire() {
		for(ILocalizationListener listener: listeners) {
			listener.localizationChanged();
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationProvider#getString(java.lang.String)
	 */
	@Override
	public abstract String getString(String key);
}
