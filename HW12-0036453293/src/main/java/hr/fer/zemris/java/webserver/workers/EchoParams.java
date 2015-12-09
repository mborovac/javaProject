package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class representing a web worker writing the parameters it was given in a table.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class EchoParams implements IWebWorker {
	
	/**
	 * @see hr.fer.zemris.java.webserver.IWebWorker#processRequest(hr.fer.zemris.java.webserver.RequestContext)
	 */
	@Override
	public void processRequest(RequestContext context) {
		Set<String> parameters = context.getParameterNames();
		try {
			context.write("<html><head><t>Contents table</t></head><body><table border=\"1\"");
			for(String parameter: parameters) {
				context.write("<tr><td>" + parameter + "</td><td>" + context.getParameter(parameter) + "</td></tr>");
			}
			context.write("</table></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
