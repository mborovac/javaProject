package hr.fer.zemris.web.radionice;

/**
 * Class representing a single application user.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class User {
	
	private final String login;
	private final String zaporka;
	private final String ime;
	private final String prezime;
	
	/**
	 * Class constructor.
	 * 
	 * @param login username
	 * @param zaporka password
	 * @param ime first name
	 * @param prezime last name
	 */
	public User(final String login, final String zaporka, final String ime,
			final String prezime) {
		super();
		this.login = login;
		this.zaporka = zaporka;
		this.ime = ime;
		this.prezime = prezime;
	}

	/**
	 * @return the username
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @return the password
	 */
	public String getZaporka() {
		return zaporka;
	}

	/**
	 * @return the first name
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * @return the last name
	 */
	public String getPrezime() {
		return prezime;
	}
	
	/**
	 * @returns the full name of the user
	 */
	public String getPotpuniNaziv() {
		return ime + " " + prezime;
	}
}
