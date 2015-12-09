package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to create a demonstration Pie Chart for Java 13th homework.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PieChartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method creates a pie chart of OS usage and returns it as a response.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
		// This will create the dataset 
        PieDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "");
        BufferedImage image = chart.createBufferedImage(500, 270, new ChartRenderingInfo());
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	private  PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;
		}
	
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
