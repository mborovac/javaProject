package hr.fer.zemris.java.hw11.notepadpp.local.swing;

import hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationProvider;

import javax.swing.JToolBar;

/**
 * Class representing a fully localizable JToolBar. It is a listener registered on the localization
 * provider and it changes it's text as the language changes.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LJToolBar extends JToolBar implements ILocalizationListener  {
	
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider provider;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, key based on which it will get the 
	 * adequate String from the localization provider and the reference to the localization provider.
	 * 
	 * @param key key based on which the JToolBar will get the adequate String from the localization provider
	 * @param provider the reference to the localization provider
	 */
	public LJToolBar(final String key, final ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		setName(this.provider.getString(key));
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
			}
		});
	}

	/**
	 * @see hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener#localizationChanged()
	 */
	@Override
	public void localizationChanged() {
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
