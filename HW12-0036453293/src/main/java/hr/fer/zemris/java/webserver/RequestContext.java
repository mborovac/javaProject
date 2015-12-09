package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class representing the context of a HTTP request.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RequestContext {
	
	private OutputStream outputStream;
	private Charset charset;
	
	private String encoding = "UTF-8";
	private int statusCode = 200;
	private String statusText = "OK";
	private String mimeType = "text/html";
	
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters = new HashMap<>();
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated = false;
	
	/**
	 * Class constructor. Constructor takes 4 arguments, a reference to an output stream, map of parameters,
	 * map of persistent parameters and a list of cookies.
	 * 
	 * @param outputStream reference to an output stream
	 * @param parameters map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies list of cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if(outputStream == null) {
			throw new IllegalArgumentException("Output stream can not be null!");
		}
		this.outputStream = outputStream;
		if(parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		if(persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if(outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
	}
	
	/**
	 * Method used to set the encoding.
	 * 
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Encoding can not be changed any more.");
		}
		this.encoding = encoding;
	}
	
	/**
	 * Method used to set the status code.
	 * 
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Status code can not be changed any more.");
		}
		this.statusCode = statusCode;
	}
	
	/**
	 * Method used to set the status text.
	 * 
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Status text can not be changed any more.");
		}
		this.statusText = statusText;
	}
	
	/**
	 * Method used to set the mime type.
	 * 
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Mime type can not be changed any more.");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Method used to get a specific parameter.
	 * 
	 * @param name name of the parameter
	 * @return returns the value associated to the given name
	 */
	public String getParameter(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		return this.parameters.get(name);
	}
	
	/**
	 * Method used to retrieve all the names in the parameter map.
	 * 
	 * @return returns a set of all the names in the parameter map
	 */
	public Set<String> getParameterNames() {
		return new HashSet<>(this.parameters.keySet());
	}
	
	/**
	 * Method used to get a specific persistent parameter.
	 * 
	 * @param name name of the parameter
	 * @return returns the value associated to the given name
	 */
	public String getPersistentParameter(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		return this.persistentParameters.get(name);
	}
	
	/**
	 * Method used to retrieve all the names in the persistent parameter map.
	 * 
	 * @return returns a set of all the names in the persistent parameter map
	 */
	public Set<String> getPersistentParameterNames() {
		return new HashSet<>(this.persistentParameters.keySet());
	}
	
	/**
	 * Method used to put an entry in to the persistent parameter map.
	 * 
	 * @param name name to be stored
	 * @param value value to be stored
	 */
	public void setPersistentParameter(String name, String value) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		if(value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Value can not be null or empty.");
		}
		this.persistentParameters.put(name, value);
	}
	
	/**
	 * Method used to remove a specific parameter from the persistent parameter map.
	 * 
	 * @param name name of the parameter
	 */
	public void removePersistentParameter(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		this.persistentParameters.remove(name);
	}
	
	/**
	 * Method used to get a specific temporary parameter.
	 * 
	 * @param name name of the parameter
	 * @return returns the value associated to the given name
	 */
	public String getTemporaryParameter(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		return this.temporaryParameters.get(name);
	}
	
	/**
	 * Method used to retrieve all the names in the temporary parameter map.
	 * 
	 * @return returns a set of all the names in the v parameter map
	 */
	public Set<String> getTemporaryParameterNames() {
		return new HashSet<>(this.temporaryParameters.keySet());
	}
	
	/**
	 * Method used to put an entry in to the temporary parameter map.
	 * 
	 * @param name name to be stored
	 * @param value value to be stored
	 */
	public void setTemporaryParameter(String name, String value) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		if(value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Value can not be null or empty.");
		}
		this.temporaryParameters.put(name, value);
	}
	
	/**
	 * Method used to remove a specific parameter from the temporary parameter map.
	 * 
	 * @param name name of the parameter
	 */
	public void removeTemporaryParameter(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty.");
		}
		this.temporaryParameters.remove(name);
	}
	
	/**
	 * Method used to add a cookie to the list of cookies.
	 * 
	 * @param cookie cookie to be added
	 */
	public void addRCCookie(RCCookie cookie) {
		if(cookie == null) {
			throw new IllegalArgumentException("Cookie can not be null or empty.");
		}
		if(headerGenerated) {
			throw new RuntimeException("Cookies can not be changed any more.");
		}
		outputCookies.add(cookie);
	}
	
	/**
	 * Method used to write to the output stream.
	 * 
	 * @param text string representing the text that should be written
	 * @return returns a reference to this request context
	 * @throws IOException if it can not write
	 */
	public RequestContext write(String text) throws IOException {
		this.charset = Charset.forName(encoding);
		byte[] data = text.getBytes(this.charset);
		return write(data);
	}
	
	/**
	 * Method used to write to the output stream.
	 * 
	 * @param data array of bytes representing the text that should be written
	 * @return returns a reference to this request context
	 * @throws IOException if it can not write
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Method used to generate the response header.
	 * 
	 * @throws IOException if header can not be generated
	 */
	private void generateHeader() throws IOException {
		headerGenerated = true;
		this.charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " +  mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding + "\r\n");
		} else {
			sb.append("\r\n");
		}
		for(RCCookie cookie: outputCookies) {
			sb.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
			if(cookie.domain != null) {
				sb.append("; Domain=" + cookie.domain + "");
			}
			if(cookie.path != null) {
				sb.append("; Path=" + cookie.path + "");
			}
			if(cookie.maxAge != null) {
				sb.append("; Max-Age=" + cookie.maxAge + "");
			}
			sb.append("; HttpOnly\r\n");
		}
		sb.append("\r\n");
		byte[] data = sb.toString().getBytes();
		outputStream.write(data);
	}

	/**
	 * Class used to represent a cookie.
	 */
	public static class RCCookie {
		
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		
		/**
		 * Class constructor. Constructor takes 5 arguments, parameter name and value, time the cookie
		 * is valid, cookie's domain and path.
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			if(name == null || name.isEmpty()) {
				throw new IllegalArgumentException("Name can not be null or empty.");
			}
			if(value == null || value.isEmpty()) {
				throw new IllegalArgumentException("Value can not be null or empty.");
			}
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

		/**
		 * Method used to acquire the cookie's name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Method used to acquire the cookie's value.
		 * 
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Method used to acquire the cookie's domain.
		 * 
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Method used to acquire the cookie's path.
		 * 
		 * @return the path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Method used to acquire how long the cookie is valid.
		 * 
		 * @return the maxAge
		 */
		public int getMaxAge() {
			return maxAge;
		}
	}
}
