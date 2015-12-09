/**
 * Package holding all the classes and methods for the student database assignment of 4th Java HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Interface defining a single method required in a student filter.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface IFilter {
	
	/**
	 * Method for accepting or declining a record of a student.
	 * 
	 * @param record the record of a student
	 * @return return true if the record is accepted, false otherwise
	 */
	boolean accepts(StudentRecord record);
}
