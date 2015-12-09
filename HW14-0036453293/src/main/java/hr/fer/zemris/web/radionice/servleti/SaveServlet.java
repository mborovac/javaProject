package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadionicaBaza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet called when the new/edit form is filled and sent. t is used to update the database.
 * Only registered users can do it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SaveServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		if (req.getSession().getAttribute("current.user") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req,
					resp);
			return;
		}
		
		final String metoda = req.getParameter("metoda");
		if(!metoda.equals("Pohrani")) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/listaj");
			return;
		}
		final String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/baza");
		final RadionicaBaza baza = RadionicaBaza.ucitaj(fileName);
		final RadionicaForm f = new RadionicaForm();
		f.popuniIzHttpRequesta(req);
		System.out.println("Nova oprema: " + f.getOprema());
		f.validiraj(baza);
		
		if (f.imaPogresaka()) {
			List<Opcija> listaOpreme = new ArrayList<>(baza.getMapaOpreme().values());
			List<Opcija> listaPublike = new ArrayList<>(baza.getMapaPublike().values());
			List<Opcija> listaTrajanja = new ArrayList<>(baza.getMapaTrajanja().values());
			req.setAttribute("oprema", listaOpreme);
			req.setAttribute("publika", listaPublike);
			req.setAttribute("trajanje", listaTrajanja);
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/formular.jsp").forward(req, resp);
			return;
		}
		
		final Radionica r = new Radionica();
		f.popuniURadionicu(r, baza);
		System.out.println(r.getOprema());
		baza.snimi(r);
		baza.snimi(fileName);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}
}
