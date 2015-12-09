package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used to read the given parameters and create a Microsoft excel file based on those parameters.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PowersServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method requires 3 parameters: a, b and n. It creates a Microsoft excel file with n sheets,
	 * each sheet containing 2 columns. 1st Column contains numbers from a to b and the 2nd column
	 * consists of the 1st column number's power depending on the sheet number.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		Integer n = null;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		if(a < -100 || a > 100) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		if(b < -100 || b > 100) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		try {
			n = Integer.valueOf(req.getParameter("n"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		if(n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		for(int i = 0; i < n; i++) {
			HSSFSheet sheet =  hwb.createSheet("Sheet "+ (i + 1));
			HSSFRow rowhead=   sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Value");
			rowhead.createCell(1).setCellValue("Power " + (i + 1));
			for(int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j - a + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i + 1));
			}	
		}
		
		resp.setContentType("application/vnd.ms-excel; charset=UTF-8");
		resp.setHeader("Content-Disposition", "inline");
		
		ServletOutputStream fileOut =  resp.getOutputStream();
		hwb.write(fileOut);
		fileOut.close();
	}
}
