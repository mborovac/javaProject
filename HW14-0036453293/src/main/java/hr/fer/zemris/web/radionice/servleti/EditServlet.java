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
 * Servlet called when a workshop should be edited.
 * Only registered users can do it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class EditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (req.getSession().getAttribute("current.user") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req,
					resp);
			return;
		}
		
		Long id = Long.valueOf(req.getParameter("id"));
		RadionicaBaza baza = RadionicaBaza.ucitaj(req.getServletContext().getRealPath("/WEB-INF/baza"));
		Radionica r = baza.getMapaRadionica().get(Long.toString(id));
		
		if (r == null) {
			req.setAttribute("poruka", "Tražena radionica ne postoji.");
			req.getRequestDispatcher("/WEB-INF/pages/greska.jsp").forward(req,
					resp);
			return;
		}
		
		List<Opcija> listaOpreme = new ArrayList<>(baza.getMapaOpreme().values());
		List<Opcija> listaPublike = new ArrayList<>(baza.getMapaPublike().values());
		List<Opcija> listaTrajanja = new ArrayList<>(baza.getMapaTrajanja().values());
		req.setAttribute("oprema", listaOpreme);
		req.setAttribute("publika", listaPublike);
		req.setAttribute("trajanje", listaTrajanja);
		
		RadionicaForm f = new RadionicaForm();
		f.popuniIzRadionice(r);
		req.setAttribute("zapis", f);
		req.getRequestDispatcher("/WEB-INF/pages/formular.jsp").forward(req, resp);
	}
}
