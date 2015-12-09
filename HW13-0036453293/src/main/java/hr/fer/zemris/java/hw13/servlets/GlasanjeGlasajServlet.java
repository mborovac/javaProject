package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to register a vote cast for a band in the voting application.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeGlasajServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method reads the request parameter, increases the corresponding band's vote count and writes
	 * the updated count in to the file.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Map<String, Integer> bandVotes = new LinkedHashMap<>();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		String line;
		
		while((line = in.readLine()) != null) {
			String[] splitLine = line.split("\t");
			bandVotes.put(splitLine[0], Integer.parseInt(splitLine[1]));
		}
		in.close();
		
		int votes = bandVotes.get(req.getParameter("id"));
		votes += 1;
		bandVotes.put(req.getParameter("id"), votes);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(fileName), "UTF-8"));
		for(String id: bandVotes.keySet()) {
			if(id.equals(req.getParameter("id"))) {
				out.write(id + "	" + votes + "\n");
			} else {
				out.write(id + "	" + bandVotes.get(id) + "\n");
			}
			out.flush();
		}
		out.close();
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
