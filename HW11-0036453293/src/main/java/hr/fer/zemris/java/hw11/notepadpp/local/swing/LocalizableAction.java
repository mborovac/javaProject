package hr.fer.zemris.java.hw11.notepadpp.local.swing;

import hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class representing a fully localizable action. It is a listener registered on the localization
 * provider and it changes it's name as the language changes.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction implements ILocalizationListener {
	
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider provider;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, key based on which it will get the 
	 * adequate String from the localization provider and the reference to the localization provider.
	 * 
	 * @param key key based on which the LocalizableAction will get the adequate String from the localization provider
	 * @param provider the reference to the localization provider
	 */
	public LocalizableAction(final String key, final ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
			}
		});
		putValue(NAME, provider.getString(key));
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);
	
	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener#localizationChanged()
	 */
	@Override
	public void localizationChanged() {
		putValue(NAME, provider.getString(key));
	}
	
	/**
	 * Method used to acquire the current instance's key.
	 * 
	 * @return the key of the current instance
	 */
	public String getKey() {
		return this.key;
	}
}
