package hr.fer.zemris.web.radionice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a workshop. It contains the workshop's ID, name, date, equipment, duration,
 * appropriate audience, maximum number of audience, contact e-mail and a description string.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Radionica implements Comparable<Radionica> {
	
	private Long id;
	private String naziv;
	private String datum;
	private List<Opcija> oprema = new ArrayList<>();
	private Opcija trajanje;
	private List<Opcija> publika = new ArrayList<>();
	private Integer maksPolaznika;
	private String email;
	private String dopuna;
	private String originalnaDopuna;
	
	/**
	 * Class constructor.
	 */
	public Radionica() {
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param id workshop's ID
	 * @param naziv workshop's name
	 * @param datum workshop's date
	 * @param maksPolaznika workshop's max audience number
	 * @param trajanje workshop's duration
	 * @param email workshop's contact e-mail
	 * @param dopuna workshop's description
	 * @param radioniceOprema workshop's all possible equipment
	 * @param radionicePublika workshop's all possible audience
	 */
	public Radionica(Long id, String naziv, String datum, Integer maksPolaznika, Opcija trajanje,
			String email, String dopuna, List<Opcija> radioniceOprema, List<Opcija> radionicePublika) {
		if(id < 0) {
			System.out.println("ID can not be lower than 0.");
			return;
		}
		if(naziv == null || naziv.isEmpty() || naziv.length() > 40) {
			System.out.println("Naziv can not be null or empty or longer than 40 characters.");
			return;
		}
		if(datum == null || datum.isEmpty()) {
			System.out.println("Datum can not be null or empty.");
			return;
		}
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date.parse(datum);
		} catch (ParseException e) {
			System.out.println("Date must be formatted yyyy-MM-dd.");
			return;
		}
		if(email == null || email.isEmpty()) {
			System.out.println("EMail can not be null or empty.");
			return;
		}
		if(!email.contains("@")) {
			System.out.println("EMail is ill-formed.");
			return;
		}
		if(maksPolaznika == null) {
			System.out.println("MaksPolaznika can not be null.");
			return;
		}
		if(maksPolaznika < 10 || maksPolaznika > 50) {
			System.out.println("MaksPolaznika can not be lower than 10 or greater than 50.");
			return;
		}
		if(trajanje == null) {
			System.out.println("Trajanje can not be null.");
			return;
		}
		if(!trajanje.getVrijednost().equalsIgnoreCase("Dvosatna radionica") &&
				!trajanje.getVrijednost().equalsIgnoreCase("Poludnevna radionica") &&
				!trajanje.getVrijednost().equalsIgnoreCase("Cjelodnevna radionica")) {
			System.out.println("Trajanje is not a valid value.");
			return;
		}
		if(radionicePublika.isEmpty()) {
			System.out.println("At least one type of audience needs to be specified.");
			return;
		}
		this.id = id;
		this.naziv = naziv;
		this.datum = datum;
		this.maksPolaznika = maksPolaznika;
		this.trajanje = trajanje;
		this.email = email;
		this.dopuna = dopuna;//.replaceAll("\\\\n", "\\\n").replaceAll("\\\\t", "\\\t").replaceAll("\\\\", "\\");
		this.originalnaDopuna = dopuna;
		this.oprema = new ArrayList<>(radioniceOprema);
		this.publika = new ArrayList<>(radionicePublika);
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
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
	 * @return the oprema
	 */
	public List<Opcija> getOprema() {
		return oprema;
	}
	
	/**
	 * @return the trajanje
	 */
	public Opcija getTrajanje() {
		return trajanje;
	}
	
	/**
	 * @return the publika
	 */
	public List<Opcija> getPublika() {
		return publika;
	}
	
	/**
	 * @return the maksPolaznika
	 */
	public Integer getMaksPolaznika() {
		return maksPolaznika;
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
	 * @return the originalnaDopuna
	 */
	public String getOriginalnaDopuna() {
		return originalnaDopuna;
	}
	
	/**
	 * @param naziv the naziv to set
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	/**
	 * @param datum the datum to set
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	/**
	 * @param oprema the oprema to set
	 */
	public void setOprema(List<Opcija> oprema) {
		this.oprema = oprema;
	}
	
	/**
	 * @param trajanje the trajanje to set
	 */
	public void setTrajanje(Opcija trajanje) {
		this.trajanje = trajanje;
	}
	
	/**
	 * @param publika the publika to set
	 */
	public void setPublika(List<Opcija> publika) {
		this.publika = publika;
	}
	
	/**
	 * @param maksPolaznika the maksPolaznika to set
	 */
	public void setMaksPolaznika(Integer maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @param dopuna the dopuna to set
	 */
	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * Compares by ID.
	 */
	@Override
	public int compareTo(Radionica o) {
		return Long.valueOf(id).compareTo(Long.valueOf(o.id));
	}
}
