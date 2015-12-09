/**
 * Package holding all the classes and methods for the student database assignment of 4th Java HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Class used to create and maintain a single student record in the database.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class StudentRecord {
	
	private int jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Class constructor.
	 * 
	 * @param jmbag JMBAG of the student, can not be lower than 0
	 * @param lastName last name of the student, can not be null or empty
	 * @param firstName first name of the student, can not be null or empty
	 * @param finalGrade final grade of the student, can not be lower than 1 greater than 5
	 */
	protected StudentRecord(int jmbag, String lastName, String firstName, int finalGrade) {
		
		if(jmbag < 1) {
			throw new IllegalArgumentException("JMBAG can not be lower than 1");
		}
		if(lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException("Last name can not be null or empty");
		}
		if(firstName == null || firstName.isEmpty()) {
			throw new IllegalArgumentException("First name can not be null or empty");
		}
		if(finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Final grade can not be lower than 1 or greater than 5");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Students are equal if their JMBAGs are equal.
	 */
	@Override
	public boolean equals(Object student) {
		if(!(student instanceof StudentRecord)) {
			throw new IllegalArgumentException("Can't compare something that isn't a student");
		}
		if(this.jmbag == ((StudentRecord) student).getJmbag()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * 
	 * Method returns a hash code of the student's JMBAG
	 */
	@Override
	public int hashCode() {
		return new Integer(this.jmbag).hashCode();
	}

	/**
	 * Getter method for JMBAG.
	 * 
	 * @return returns student's JMBAG
	 */
	public int getJmbag() {
		return jmbag;
	}

	/**
	 * Getter method for last name.
	 * 
	 * @return returns student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter method for first name.
	 * 
	 * @return returns student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter method for final grade.
	 * 
	 * @return returns student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
}
