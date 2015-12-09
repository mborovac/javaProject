package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadionicaBaza;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to list all of the available workshops.
 * It has a bug, once the database is updated it simply won't read the files eve though the files 
 * are named exactly the same and ar at the same position in the app. The database is updated
 * correctly.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ListajServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		RadionicaBaza baza = RadionicaBaza.ucitaj(req.getServletContext().getRealPath("/WEB-INF/baza"));
		List<Radionica> listaRadionica = new ArrayList<>(baza.getMapaRadionica().values());
		Collections.sort(listaRadionica, new Comparator<Radionica>() {

			@Override
			public int compare(Radionica r1, Radionica r2) {
				DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
				Date r1Date;
				try {
					r1Date = date1.parse(r1.getDatum());
				} catch (ParseException e) {
					System.out.println("Can not format date1");
					return 0;
				}
				DateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
				Date r2Date;
				try {
					r2Date = date2.parse(r2.getDatum());
				} catch (ParseException e) {
					System.out.println("Can not format date2");
					return 0;
				} 
				int temp = r1Date.compareTo(r2Date);
				if(temp != 0) {
					return temp;
				} else {
					return r1.getNaziv().compareTo(r2.getNaziv());
				}
			}
			
		});
		
		final Object entry = req.getSession().getAttribute("current.user");
		if (entry != null) {
			req.setAttribute("user", entry);
		} else {
			req.setAttribute("user", null);
		}
		req.setAttribute("radionice", listaRadionica);
		req.getRequestDispatcher("/WEB-INF/pages/listaj.jsp").forward(req, resp);
	}
}
