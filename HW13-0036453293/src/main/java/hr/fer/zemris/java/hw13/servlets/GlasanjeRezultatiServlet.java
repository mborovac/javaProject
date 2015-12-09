package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to prepare the info about vote state for the jsp.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method reads the files containing the band info and vote state and prepares all needed
	 * info for the jsp and stores it as request attributes.
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
		
		List<Integer> listOfVotes = new ArrayList<>(bandVotes.values());
		Collections.sort(listOfVotes);
		Collections.reverse(listOfVotes);
		Set<Integer> sortedVotesSet = new LinkedHashSet<>();
		for(Integer votes: listOfVotes) {
			sortedVotesSet.add(votes);
		}
		
		List<String> sortedBands = new ArrayList<>();
		for(Integer votes: sortedVotesSet) {
			for(String bandID: bandVotes.keySet()) {
				if(bandVotes.get(bandID).equals(votes)) {
					sortedBands.add(bandID);
				}
			}
		}	
		
		String bandInfoFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<String, String> bandInfo = new LinkedHashMap<>();
		Map<String, String> mapOfLinks = new HashMap<>();
		in = new BufferedReader(
				new InputStreamReader(new FileInputStream(bandInfoFile), "UTF-8"));
		
		while((line = in.readLine()) != null) {
			String[] splitLine = line.split("\t");
			bandInfo.put(splitLine[0], splitLine[1]);
			mapOfLinks.put(splitLine[0], splitLine[2]);
		}
		in.close();
		
		Map<String, Integer> mapOfVotes = new HashMap<>();
		for(String bandID: bandInfo.keySet()) {
			mapOfVotes.put(bandInfo.get(bandID), bandVotes.get(bandID));
		}
		
		String highestVotedBand = sortedBands.get(0);
		int highestVotes = mapOfVotes.get(bandInfo.get(highestVotedBand));
		Map<String, String> topBandSong = new HashMap<>();
		for(String band: sortedBands) {
			if(mapOfVotes.get(bandInfo.get(band)) == highestVotes) {
				topBandSong.put(bandInfo.get(band), mapOfLinks.get(band));
			}
		}
		
		req.setAttribute("info", bandInfo);
		req.setAttribute("votes", bandVotes);
		req.setAttribute("sortedBands", sortedBands);
		req.setAttribute("namesAndVotes", mapOfVotes);
		req.setAttribute("links", topBandSong);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
