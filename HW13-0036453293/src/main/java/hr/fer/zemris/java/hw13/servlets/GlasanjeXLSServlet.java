package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	 * Method reads the files containing the voting results and band information and creates a Microsoft
	 * excel file.
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
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Band votes");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Band name");
		rowhead.createCell(1).setCellValue("Number of votes");
		int i = 1;
		for(String band: mapOfVotes.keySet()) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(band);
			row.createCell(1).setCellValue(mapOfVotes.get(band));
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
