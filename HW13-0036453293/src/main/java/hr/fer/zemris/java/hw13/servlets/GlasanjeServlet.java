package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to prepare the info about the bands available for voting. The info is shown to the
 * user by the glasanjeIndex jsp.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method reads the band info file and prepares the information for the jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<Integer, String> bandNames = new LinkedHashMap<>();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		String line;
		
		while ((line = in.readLine()) != null) {
			String[] splitLine = line.split("\t");
			bandNames.put(Integer.parseInt(splitLine[0]), splitLine[1]);
		}
		in.close();
		
		req.setAttribute("bands", bandNames);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
