package hr.fer.zemris.java.hw11.notepadpp.local;

import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used as a bridge between the localization provider and the registered listeners. It is used
 * to keep the list of listeners so that the localization provider doesn't keep the references to them.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationListener {
	
	private boolean connected;
	private LocalizationProvider provider;
	private List<ILocalizationListener> listeners;
	
	/**
	 * Class constructor. Constructor takes a single argument, the reference to the localization provider.
	 * 
	 * @param provider the reference to the localization provider
	 */
	public LocalizationProviderBridge(LocalizationProvider provider) {
		this.provider = provider;
		this.connected = false;
		provider.addLocalizationListener(this);
		
		listeners = new ArrayList<>();
	}
	
	/**
	 * Method used to disconnect the bridge from the localization provider.
	 */
	public void disconnect() {
		provider.removeLocalizationListener(this);
	}
	
	/**
	 * Method used to connect the bridge to the localization provider.
	 */
	public void connect() {
		if(connected) {
			throw new AlreadyConnectedException();
		}
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
			}
		});
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.AbstractLocalizationProvider#getString(java.lang.String)
	 */
	@Override
	public String getString(String key) {
		return this.provider.getString(key);
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener#localizationChanged()
	 */
	@Override
	public void localizationChanged() {
		for(ILocalizationListener listener: listeners) {
			listener.localizationChanged();
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.AbstractLocalizationProvider#addLocalizationListener(
	 * hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener)
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if(!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}
}
