package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Class used to implement a servlet context listener. Listener listens to servlet startup and destruction and
 * sets/deletes the time when servlet container has been initialized.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ServletListener implements ServletContextListener {
	
	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().removeAttribute("creationTime");
	}
	
	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		event.getServletContext().setAttribute("creationTime", System.currentTimeMillis());
	}
}
