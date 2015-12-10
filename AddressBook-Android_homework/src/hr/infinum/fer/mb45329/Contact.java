package hr.infinum.fer.mb45329;

/**
 * Class representing an address book contact.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Contact {
	
	private String name;
	private String phone;
	private String email;
	private String note;
	private String fbProfile;
	
	/**
	 * Class constructor.
	 * 
	 * @param name contact's name
	 * @param phone contact's phone number
	 * @param email contact's e-mail address
	 * @param note a note associated to the contact
	 * @param fbProfile contact's Facebook profile
	 */
	public Contact(String name, String phone, String email, String note, String fbProfile) {
		this.name = name;
		this.phone = phone;
		this.note = note;
		this.email = email;
		this.fbProfile = fbProfile;
	}
	
	/**
	 * Contact's name getter.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Contact's name setter.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Contact's phone number getter.
	 * 
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phone;
	}
	
	/**
	 * Contact's phone number setter.
	 * 
	 * @param contact the phone number to set
	 */
	public void setPhoneNumber(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Contact's note getter.
	 * 
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Contact's note setter.
	 * 
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Contact's e-mail getter.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Contact's e-mail setter.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Contact's Facebook profile getter.
	 * 
	 * @return the facebook profile
	 */
	public String getFbProfile() {
		return fbProfile;
	}
	
	/**
	 * Contact's Facebook profile setter.
	 * 
	 * @param fbProfile the Facebook profile to set
	 */
	public void setFbProfile(String fbProfile) {
		this.fbProfile = fbProfile;
	}
}
