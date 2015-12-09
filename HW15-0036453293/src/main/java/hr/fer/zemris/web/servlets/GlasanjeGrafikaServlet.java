package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
	 * Method acquires the vote state from the database and creates the chart.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		if(pollID < 1) {
			req.setAttribute("error", "Nelegalan identifikator ankete! Identifikator ne može biti manji od 1.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		DAO dao = DAOProvider.getDao();
		List<PollOptionEntry> entrys = dao.getPollOptionsByPollID(pollID);
		
		// This will create the dataset 
        PieDataset dataset = createDataset(entrys);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, "");
        BufferedImage image = chart.createBufferedImage(400, 400, new ChartRenderingInfo());
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	/**
	 * Method creates the data set.
	 * 
	 * @param entrys database entries with vote state
	 * @return returns a new data set for the chart
	 */
	private  PieDataset createDataset(List<PollOptionEntry> entrys) {
		DefaultPieDataset result = new DefaultPieDataset();
		for(PollOptionEntry entry: entrys) {
			result.setValue(entry.getOptionTitle(), entry.getVotesCount());
		}
		return result;
	}
	
	/**
	 * Method used to create a pie chart.
	 * 
	 * @param dataset data set used to create the chart
	 * @param title chart title
	 * @return returns a new pie chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
