/**
 * Package holding all the classes and methods for the student database assignment of 4th Java HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Class for filtering student records based on the student's last name. Class implements IFilter interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LastNameFilter implements IFilter {
	
	private String mask;
	
	/**
	 * Class constructor. Constructor accepts a single string, a mask representing the regex based on which the 
	 * class should filter the database records.
	 * 
	 * @param mask the regular expression based on which the class filters the database, can not be null or empty
	 */
	public LastNameFilter(String mask) {
		if(mask == null || mask.isEmpty()) {
			throw new IllegalArgumentException("Mask is invalid");
		}
		this.mask = mask;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw4.db.IFilter#accepts(hr.fer.zemris.java.tecaj.hw4.db.StudentRecord)
	 * 
	 * Method accepts the record if it's last name matches the given regular expression mask.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		if(record.getLastName().matches(this.mask)) {
			return true;
		}
		return false;
	}
}
