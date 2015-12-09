package hr.fer.zemris.java.hw16.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public class LogCreation {
	
	public static void createLog(HttpServletRequest req) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/Marko/Desktop/logFile.txt", true)));
		out.append(req.getRequestURI().substring(req.getContextPath().length()) + " " + detectBrowser(req) + "\n");
		out.close();
	}
	
	private static String detectBrowser(HttpServletRequest req) {
		String userAgent = req.getHeader("user-agent");
		
		if(userAgent.contains("Safari")) {
			return "safari";
		}
		if(userAgent.contains("Chrome")) {
			return "chrome";
		}
		if(userAgent.contains("Firefox")) {
			return "firefox";
		}
		if(userAgent.contains("Opera")) {
			return "opera";
		}
		return "ie";
	}
}
