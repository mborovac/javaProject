package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadionicaBaza;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class used to transport the information needed between servlets and JSP. It is used to 
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RadionicaForm {
	
	private String id;
	private String naziv;
	private String datum;
	private List<String> oprema = new ArrayList<>();
	private String trajanje;
	private List<String> publika = new ArrayList<>();
	private String maksPolaznika;
	private String email;
	private String dopuna;
	
	Map<String, String> greske = new HashMap<>();
	
	/**
	 * Class constructor.
	 */
	public RadionicaForm() {
		this.id = "";
		this.naziv = "";
		this.datum = "";
		this.maksPolaznika = "";
		this.trajanje = "";
		this.email = "";
		this.dopuna = "";
	}
	
	/**
	 * Returns an error if it exists.
	 * 
	 * @param ime name of the error
	 * @return returns the error connected to the given name if it exists
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	/**
	 * Checks whether there have been any errors.
	 * 
	 * @return returns true if there have been errors, false otherwise
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	/**
	 * Checks whether the given name has an error connected to it.
	 * 
	 * @param ime name of the error
	 * @return returns true if the given name has an error connected to it, false otherwise
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}
	
	/**
	 * FIlls the form based on the info from the HTTP request.
	 * 
	 * @param request HTTP request
	 */
	public void popuniIzHttpRequesta(HttpServletRequest request) {
		this.id = pripremi(request.getParameter("id"));
		this.naziv = pripremi(request.getParameter("naziv"));
		this.datum = pripremi(request.getParameter("datum"));
		this.maksPolaznika = pripremi(request.getParameter("maksPolaznika"));
		this.trajanje = pripremi(request.getParameter("trajanje"));
		this.email = pripremi(request.getParameter("email"));
		this.dopuna = pripremi(request.getParameter("dopuna"));
		String[] odabranaOprema = request.getParameterValues("oprema");
		if(odabranaOprema != null) {
			for(int i = 0; i < odabranaOprema.length; i++) {
				System.out.println("Oprema: " + odabranaOprema[i]);
				this.oprema.add(odabranaOprema[i]);
			}
		}
		String[] odabranaPublika = request.getParameterValues("publika");
		if(odabranaPublika != null) {
			for(int i = 0; i < odabranaPublika.length; i++) {
				this.publika.add(odabranaPublika[i]);
			}
		}
	}
	
	/**
	 * Prepares the given string so it doesn't contain null elements.
	 * 
	 * @param value given string
	 * @return returns a new string without null elements
	 */
	private String pripremi(String value) {
		if(value == null) {
			return "";
		} else {
			return value.trim();
		}
	}
	
	/**
	 * Fills the form based on the info from the given workshop.
	 * 
	 * @param r given workshop
	 */
	public void popuniIzRadionice(Radionica r) {
		if(r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		this.naziv = r.getNaziv();
		this.datum = r.getDatum();
		this.maksPolaznika = Integer.toString(r.getMaksPolaznika());
		this.trajanje = r.getTrajanje().getID();
		this.email = r.getEmail();
		this.dopuna = r.getDopuna();
		List<Opcija> tempList = new ArrayList<>(r.getOprema());
		for(Opcija opcija: tempList) {
			this.oprema.add(opcija.getID());
		}
		tempList = new ArrayList<>(r.getPublika());
		for(Opcija opcija: tempList) {
			this.publika.add(opcija.getID());
		}
	}
	
	/**
	 * Fills a workshop based on the info from the form.
	 * 
	 * @param r workshop to be filled
	 * @param baza database
	 */
	public void popuniURadionicu(Radionica r, RadionicaBaza baza) {
		if(this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}
		r.setDatum(this.datum);
		r.setDopuna(this.dopuna);
		r.setEmail(this.email);
		r.setMaksPolaznika(Integer.valueOf(this.maksPolaznika));
		r.setNaziv(this.naziv);
		r.setTrajanje(baza.getMapaTrajanja().get(this.trajanje));
		System.out.println(oprema);
		final List<Opcija> radOprema = new ArrayList<>();
		for (final String key: oprema) {
			System.out.println("Adding: " + baza.getMapaOpreme().get(key));
			radOprema.add(baza.getMapaOpreme().get(key));
		}
		r.setOprema(radOprema);
		final List<Opcija> radPublika = new ArrayList<>();
		for (final String key: publika) {
			radPublika.add(baza.getMapaPublike().get(key));
		}
		r.setPublika(radPublika);
	}
	
	/**
	 * Validates the form info based on the database.
	 * 
	 * @param baza the database
	 */
	public void validiraj(RadionicaBaza baza) {
		greske.clear();
		if(!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch(NumberFormatException ex) {
				greske.put("id", "Vrijednost identifikatora nije valjanja.");
			}
		}
		if(this.naziv == null || this.naziv.isEmpty()) {
			greske.put("naziv", "Naziv je obavezan.");
		}
		if(this.datum == null || this.datum.isEmpty()) {
			greske.put("datum", "Datum je obavezan.");
		} else {
			DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date.parse(datum);
			} catch (ParseException e) {
				greske.put("datum", "Datum mora biti formata yyyy-MM-dd.");
			}
		}
		if(this.maksPolaznika == null || this.maksPolaznika.isEmpty()) {
			greske.put("maksPolaznika", "Broj polaznika je obavezan.");
		} else {
			try {
				final Long value = Long.parseLong(maksPolaznika);
				if (value < 10 || value > 50) {
					greske.put("maksPolaznika",
							"Broj polaznika mora biti u intervalu [10, 50].");
				}
			} catch (final NumberFormatException e) {
				greske.put("maksPolaznika", "Broj polaznika mora biti broj.");
			}
		}
		if(this.trajanje == null || this.trajanje.isEmpty()) {
			greske.put("trajanje", "Trajanje je obavezno.");
		} else {
			if(!baza.getMapaTrajanja().containsKey(trajanje)) {
				greske.put("trajanje", "Označeno je nepostojeće trajanje.");
			}
		}
		if(this.email.isEmpty()) {
			greske.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf("@");
			if(l < 3 || p == -1 || p == 0 || p == l - 1) {
				greske.put("email", "EMail nije ispravnog formata.");
			}
		}
		
		if (trajanje.isEmpty()) {
			greske.put("trajanje", "Trajanje je obavezno.");
		}
		
		if (publika.isEmpty()) {
			greske.put("publika", "Barem jedan tip publike je obavezan.");
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the naziv
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * @return the datum
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * @return the maksPolaznika
	 */
	public String getMaksPolaznika() {
		return maksPolaznika;
	}

	/**
	 * @return the trajanje
	 */
	public String getTrajanje() {
		return trajanje;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the dopuna
	 */
	public String getDopuna() {
		return dopuna;
	}

	/**
	 * @return the greske
	 */
	public Map<String, String> getGreske() {
		return greske;
	}

	/**
	 * @return the oprema
	 */
	public List<String> getOprema() {
		return oprema;
	}

	/**
	 * @return the publika
	 */
	public List<String> getPublika() {
		return publika;
	}
}
