/**
 * Package holding all the classes and methods for the student database assignment of 4th Java HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to create and maintain a small database of student records.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class StudentDatabase {
	
	private List<StudentRecord> listOfStudents;
	private Map<Integer, StudentRecord> indexOfStudents;
	
	/**
	 * Class constructor. Constructor takes a list of strings representing records of students. Each student is
	 * represented by 4 parameters, all divided by a tabulator. Parameters are in this order: JMBAG, last name, 
	 * first name and the final grade.
	 * 
	 * @param students list of strings representing students, can not be null, empty or contain null elements
	 */
	public StudentDatabase(List<String> students) {
		if(students == null || students.isEmpty() || students.contains(null)) {
			throw new IllegalArgumentException("Student list is invalid");
		}
		
		this.listOfStudents = createListOfStudents(students);
		this.indexOfStudents = createIndexOfStudents(students);
	}
	
	/**
	 * Private method used to create the list of students.
	 * 
	 * @param students list of strings representing students, can not be null, empty or contain null elements
	 * @return returns a list of student records
	 */
	private List<StudentRecord> createListOfStudents(List<String> students) {
		List<StudentRecord> listOfStudents = new ArrayList<StudentRecord>();
		for(String student: students) {
			String[] splitStudent = student.split("\t");
			if(splitStudent.length != 4) {
				throw new IllegalArgumentException("Database file is corrupt!");
			}
			StudentRecord newStudent = new StudentRecord(Integer.parseInt(splitStudent[0]), 
					splitStudent[1], splitStudent[2], Integer.parseInt(splitStudent[3]));
			listOfStudents.add(newStudent);
		}
		return listOfStudents;
	}
	
	/**
	 * Private method used to create an index of students for fast access based on student's JMBAG.
	 * 
	 * @param students list of strings representing students, can not be null, empty or contain null elements
	 * @return returns a map of student records with their JMBAGs as keys
	 */
	private Map<Integer, StudentRecord> createIndexOfStudents(List<String> students) {
		Map<Integer, StudentRecord> indexOfStudents = new HashMap<Integer, StudentRecord>();
		for(StudentRecord student: listOfStudents) {
			indexOfStudents.put(student.getJmbag(), student);
		}
		return indexOfStudents;
	}
	
	/**
	 * Method for acquiring a student record based on the JMBAG of the student.
	 * 
	 * @param jmbag JMBAG of the sought student
	 * @return returns the sought record if it exists in the index, null otherwise
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(jmbag == null || jmbag.isEmpty()) {
			throw new IllegalArgumentException("JMBAG can not be empty!");
		}
		return indexOfStudents.get(Integer.parseInt(jmbag));
	}
	
	/**
	 * Method for filtering the student records based on a previously defined filter.
	 * 
	 * @param filter previously defined filter, can not be null
	 * @return returns a list of the records that have passed the filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		if(filter == null) {
			throw new IllegalArgumentException("Filter can not be null");
		}
		List<StudentRecord> filteredRecords = new ArrayList<StudentRecord>();
		for(StudentRecord student: this.listOfStudents) {
			if(filter.accepts(student)) {
				filteredRecords.add(student);
			}
		}
		return filteredRecords;
	}

	/**
	 * Method for acquiring the list of the student records.
	 * 
	 * @return returns the list of the student records
	 */
	public List<StudentRecord> getListOfStudents() {
		return listOfStudents;
	}

	/**
	 * Method for acquiring the index of the student records.
	 * 
	 * @return returns the index of the student records
	 */
	public Map<Integer, StudentRecord> getIndexOfStudents() {
		return indexOfStudents;
	}
}
