package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class used to run and maintain a HTTP server able to run smart scripts.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SmartHttpServer {
	
	private String address;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private ServerSocket serverSocket;
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	private Random sessionRandom = new Random();
	
	/**
	 * Class constructor. Constructor takes a single argument, the path to the configuration
	 * file.
	 * 
	 * @param configFileName the path to the configuration file
	 */
	public SmartHttpServer(String configFileName) {
		if(configFileName == null || configFileName.isEmpty())  {
			throw new IllegalArgumentException("Configuration file name can not be null or empty.");
		}
		Properties properties = new Properties();
		 BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(configFileName), "UTF8"));
		} catch (FileNotFoundException e) {
			System.out.println("Can not find server configuration file " + configFileName + ", exiting...");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding, exiting...");
			System.exit(-1);
		}
		try {
			properties.load(br);
		} catch (IOException e) {
			System.out.println("Can not read server configuration file " + configFileName + ", exiting...");
			System.exit(-1);
		}
		this.address = properties.getProperty("server.address");
		this.port = Integer.parseInt(properties.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
		readMimeTypes(properties.getProperty("server.mimeConfig"));
		this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		readWorkers(properties.getProperty("server.workers"));
		this.start();
	}
	
	/**
	 * Method used to read the mime type properties from the mime type configuration file.
	 * 
	 * @param mimePropertiesPath path to the mime type configuration file
	 */
	private void readMimeTypes(String mimePropertiesPath) {
		Properties mimeProperties = new Properties();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(mimePropertiesPath), "UTF8"));
		} catch (FileNotFoundException e) {
			System.out.println("Can not find server mime configuration file " + mimePropertiesPath + ", exiting...");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding, exiting...");
			System.exit(-1);
		}
		try {
			mimeProperties.load(br);
		} catch (IOException e) {
			System.out.println("Can not read server mime configuration file " + mimePropertiesPath + ", exiting...");
			System.exit(-1);
		}
		Enumeration<Object> mimeEnum = mimeProperties.keys();
		while(mimeEnum.hasMoreElements()) {
			String string = (String) mimeEnum.nextElement();
			this.mimeTypes.put(string, mimeProperties.getProperty(string));
		}
	}
	
	/**
	 * Method used to read the worker properties from the worker configuration file.
	 * 
	 * @param workersPath path to the worker configuration file
	 */
	private void readWorkers(String workersPath) {
		Properties workerProperties = new Properties();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(workersPath), "UTF8"));
		} catch (FileNotFoundException e) {
			System.out.println("Can not find server worker configuration file " + workersPath + ", exiting...");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding, exiting...");
			System.exit(-1);
		}
		try {
			workerProperties.load(br);
		} catch (IOException e) {
			System.out.println("Can not read server worker configuration file " + workersPath + ", exiting...");
			System.exit(-1);
		}
		Enumeration<Object> workerEnum = workerProperties.keys();
		while(workerEnum.hasMoreElements()) {
			String path = (String) workerEnum.nextElement();
			String fqcn = workerProperties.getProperty(path);
			Class<?> referenceToClass;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				workersMap.put(path, (IWebWorker) newObject);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method used to start the server.
	 */
	protected synchronized void start() {
		serverThread = new ServerThread();
		this.threadPool = Executors.newFixedThreadPool(workerThreads);
		this.threadPool.submit(serverThread);
	}
	
	/**
	 * Method used to stop the server.
	 */
	protected synchronized void stop() {
		this.threadPool.shutdown();
		try {
			serverSocket.close();
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Private class representing a main server thread. It maintains a server socket which is a socket
	 * open for requests from clients. Once a request is accepted a new socket is created to handle that
	 * request and server socket is free to listen for incoming requests again.
	 */
	private class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Can not create server socket, exiting...");
				System.exit(-1);
			}
			while(true) {
				Socket client;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					System.out.println("Can not accept the client, continuing...");
					continue;
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
		}
	}
	
	/**
	 * Private class used to handle client requests.
	 */
	private class ClientWorker implements Runnable {
		
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> permParams = null;
		private List<RCCookie> outputCookies = new ArrayList<>();
		private String SID;
		
		/**
		 * Class constructor. Constructor takes a single argument, the client socket.
		 * 
		 * @param csocket the client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			
			int responseStatus = 200;
			
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
			} catch (IOException e) {
				System.out.println("Can not acquire input stream from socket, stopping this thread.");
				return;
			}
			try {
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				System.out.println("Can not acquire output stream from socket, stopping this thread.");
				return;
			}
			List<String> request = readRequest();
			if(request.size() < 1) {
				responseStatus = 400;
			}
			String firstLine = request.get(0);
			String[] firstLineParameters = firstLine.split(" ");
			method = firstLineParameters[0];
			String requestedPath = firstLineParameters[1];
			version = firstLineParameters[2];
			String path;
			String paramString;
			if (requestedPath.contains("?")) {
				String[] pathParameters = requestedPath.split("\\?");
				path = pathParameters[0];
				paramString = pathParameters[1];
				if(!paramString.isEmpty()) {
					parseParameters(paramString);
				}
			} else {
				path = requestedPath;
			}
			if(!method.equals("GET")) {
				responseStatus = 400;
			}
			if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
				responseStatus = 400;
			}
			synchronized (SmartHttpServer.this) {
				checkSession(request);
			}
			File requestedFile = documentRoot.resolve(path.substring(1)).toFile();
			try {
				if(!requestedFile.getCanonicalPath().startsWith((documentRoot.toFile().getCanonicalPath()))) {
					System.out.println("Forbidden path!");
					responseStatus = 403;
					RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
					rc.setStatusCode(responseStatus);
					rc.setStatusText("Forbidden");
					rc.setMimeType("text/plain");
					rc.write("");
					csocket.close();
					return;
				}
			} catch (IOException e) {
				System.out.println("Can not resolve one of the paths.");
				return;
			}
			
			if(path.matches("/ext/.+")) {
				Class<?> referenceToClass;
				try {
					String className = path.substring(path.lastIndexOf("/") + 1, path.length());
					referenceToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers."
							+ className);
					Object  newObject = referenceToClass.newInstance();
					IWebWorker worker = (IWebWorker) newObject;
					worker.processRequest(new RequestContext(ostream, params, permParams, outputCookies));
					csocket.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			if(workersMap.containsKey(path)) {
				IWebWorker worker = workersMap.get(path);
				worker.processRequest(new RequestContext(ostream, params, permParams, outputCookies));
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			String extension = new String("");
			if(!requestedFile.isFile() || !requestedFile.canRead() && responseStatus == 200) {
				responseStatus = 404;
			} else {
				String fileName = requestedFile.getName();
				extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			}
			if(extension.equals("smscr")) {
				runSmartScript(requestedFile);
				try {
					csocket.close();
				} catch (IOException e) {
					System.err.println("IO Error occured.");
					System.exit(-1);;
				}
				return;
			} 
			String mimeType = null;
			if(mimeTypes.containsKey(extension)) {
				mimeType = mimeTypes.get(extension);
			} else {
				mimeType = "application/octet-stream";
			}
			RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
			rc.setMimeType(mimeType);
			rc.setStatusCode(responseStatus);
			try {
				byte[] fileContents = Files.readAllBytes(requestedFile.toPath());
				rc.write(fileContents);
				csocket.close();
			} catch (FileNotFoundException e) {
				rc.setStatusCode(404);
				rc.setStatusText("File not found.");
				try {
					rc.write("<html><head><title>Error 404</title></head><body>Error 404 File not found!</body></html>");
					csocket.close();
				} catch (IOException e1) {
					System.err.println("IO Error occurred.");
					return;
				}
			} catch (IOException e2) {
				System.out.println("IO Error occurred.");
				return;
			}
		}
		
		/**
		 * Method used to read the whole request and pars it line by line.
		 * 
		 * @return returns a list of request lines
		 */
		private List<String> readRequest() {
			List<String> requestLines = new ArrayList<>();
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(istream);
			String line = scanner.nextLine();
			while (!line.isEmpty()) {
				requestLines.add(line);
				line = scanner.nextLine();
			}
			return requestLines;
		}
		
		/**
		 * Method used to parse parameters passed to the server via HTTP request.
		 * 
		 * @param paramString string representing the parameters
		 */
		private void parseParameters(String paramString) {
			String[] parameters;
			if(paramString.contains("&")) {
				parameters = paramString.split("\\&");
				
			} else {
				parameters = new String[1];
				parameters[0] = paramString;
			}
			for(int i = 0; i < parameters.length; i++) {
				if(parameters[i].contains("=")) {
					String[] parsedParam = parameters[i].split("\\=");
					if(parsedParam.length != 2) {
						throw new IllegalArgumentException("Ill-formed request parameter.");
					}
					String key = parsedParam[0];
					String value = parsedParam[1];
					params.put(key, value);
				} else {
					throw new IllegalArgumentException("Request parameter should be formed 'key'='value'.");
				}
			}
		}
		
		/**
		 * Method used to execute a corresponding smart script.
		 * 
		 * @param file the script that will be executed
		 */
		private void runSmartScript(File file) {
			byte[] fileContents = null;
			try {
				fileContents = Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				System.out.println("IO Error occurred.");
				return;
			}
			String documentBody;
			try {
				documentBody = new String(fileContents, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported encoding.");
				return;
			}
			RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
			rc.setMimeType("text/plain");
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		}
		
		/**
		 * Method used to check whether the request is from an old session or a new one.
		 * 
		 * @param request list of strings representing the request
		 */
		private void checkSession(List<String> request) {
			String sidCandidate = null;
			for(String line : request) {
				if(!line.startsWith("Cookie:")) continue;
				if(line.contains("sid")) {
					sidCandidate = line.substring(line.indexOf("sid") + 5, line.indexOf("sid") + 25);
				}
			}
			if(sidCandidate != null) {
				SessionMapEntry sessionEntry = sessions.get(sidCandidate);
				if(sessionEntry != null) {
					Date now = new Date();
					if(sessionEntry.validUntil < now.getTime()) {
						sessions.remove(sidCandidate);
						sidCandidate = null;
					} else {
						sessionEntry.validUntil = now.getTime() + sessionTimeout*1000;
						sessions.put(sessionEntry.sid, sessionEntry);
						outputCookies.add(new RCCookie("sid", sessionEntry.sid, null, address.toString(), "/"));
					}
					permParams = sessionEntry.map;
				} else {
					Date now = new Date();
					sessionEntry = new SessionMapEntry(sidCandidate, now.getTime() + sessionTimeout*1000, new ConcurrentHashMap<String, String>());
					sessions.put(sidCandidate, sessionEntry);
					permParams = sessionEntry.map;
					outputCookies.add(new RCCookie("sid", sessionEntry.sid, null, address.toString(), "/"));
				}
			} 
			if(sidCandidate == null) {
				StringBuilder sb = new StringBuilder(20);
				for (int i = 0; i < 20; i++) {
					sb.append((char)(sessionRandom.nextInt(26) + 65));
				}
				SID = sb.toString();
				Date now = new Date();
				SessionMapEntry sessionEntry = new SessionMapEntry(SID, now.getTime() + sessionTimeout*1000, new ConcurrentHashMap<String, String>());
				sessions.put(SID, sessionEntry);
				outputCookies.add(new RCCookie("sid", sessionEntry.sid, null, address.toString(), "/"));
				permParams = sessionEntry.map;
			}
			return;
		}
	}
	
	/**
	 * Method main used to run the server. Program takes a single argument, the path to 
	 * the server configuration file.
	 * 
	 * @param args the path to the server configuration file
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException("A path to server configuration file was expected.");
		}
		new SmartHttpServer(args[0]);
	}
	
	/**
	 * Private class representing a single session entry. It is used to keep track of request sessions.
	 */
	private static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String, String> map;
		
		public SessionMapEntry(String sid, long validUntil,
				Map<String, String> map) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}
	}
}
