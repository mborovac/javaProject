package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet used to create a Pie Chart based on the current vote state.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method reads the file with vote state and creates a pie chart.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
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
		
		String bandInfoFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<String, String> bandInfo = new LinkedHashMap<>();
		in = new BufferedReader(
				new InputStreamReader(new FileInputStream(bandInfoFile), "UTF-8"));
		
		while((line = in.readLine()) != null) {
			String[] splitLine = line.split("\t");
			bandInfo.put(splitLine[0], splitLine[1]);
		}
		in.close();
		
		Map<String, Integer> mapOfVotes = new HashMap<>();
		for(String bandID: bandInfo.keySet()) {
			mapOfVotes.put(bandInfo.get(bandID), bandVotes.get(bandID));
		}
		
		// This will create the dataset 
        PieDataset dataset = createDataset(mapOfVotes);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "");
        BufferedImage image = chart.createBufferedImage(400, 400, new ChartRenderingInfo());
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	private  PieDataset createDataset(Map<String, Integer> mapOfVotes) {
		DefaultPieDataset result = new DefaultPieDataset();
		for(String band: mapOfVotes.keySet()) {
			result.setValue(band, mapOfVotes.get(band));
		}
		return result;
	}
	
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
