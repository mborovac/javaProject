package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used to create a Microsoft excel file with voting results.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeXLSServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method acquires the vote state from the database and creates a Microsoft excel file.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		if(pollID < 1) {
			req.setAttribute("error", "Nelegalan identifikator ankete! Identifikator ne može biti manji od 1.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		DAO dao = DAOProvider.getDao();
		List<PollOptionEntry> entrys = dao.getPollOptionsByPollID(pollID);
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Poll results");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Option name");
		rowhead.createCell(1).setCellValue("Number of votes");
		int i = 1;
		for(PollOptionEntry entry: entrys) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(entry.getOptionTitle());
			row.createCell(1).setCellValue(entry.getVotesCount());
			i++;
		}
		for(int j = 0; j < i; j++) {
			sheet.autoSizeColumn(j);
		}
		
		resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
		resp.setHeader("Content-Disposition", "inline");
		
		ServletOutputStream fileOut =  resp.getOutputStream();
		hwb.write(fileOut);
		fileOut.close();
	}
}
