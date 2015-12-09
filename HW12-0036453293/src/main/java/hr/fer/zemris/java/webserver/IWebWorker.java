package hr.fer.zemris.java.webserver;

/**
 * Interface defining the only method needed to implement s web worker.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface IWebWorker {
	
	/**
	 * Method used to process a web worker request sent to the server.
	 * 
	 * @param context current request context
	 */
	public void processRequest(RequestContext context);
}
