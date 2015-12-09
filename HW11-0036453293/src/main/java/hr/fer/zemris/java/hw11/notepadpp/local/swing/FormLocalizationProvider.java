package hr.fer.zemris.java.hw11.notepadpp.local.swing;

import hr.fer.zemris.java.hw11.notepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.notepadpp.local.LocalizationProviderBridge;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class used to connect the localization provider bridge to the localization provider
 * when the frame is created and to disconnect it when the frame is closed.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {
	
	/**
	 * Class constructor. Constructor takes 2 arguments, the reference to the localization
	 * provider and the reference to the frame it's listening on.
	 * 
	 * @param provider the reference to the localization provider
	 * @param frame the reference to the frame the FormLocalizationProvider is listening on
	 */
	public FormLocalizationProvider(LocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(this);
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		connect();
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// Do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		disconnect();
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// Do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// Do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// Do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// Do nothing
	}
}
