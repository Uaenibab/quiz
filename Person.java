package CS2043_W23_Team_8;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class describes a person who belongs to the school system
 * @author Tyler Babineau 3722773
 *
 */

public class Person implements Comparable<Person>, Serializable{
	
	/**
	 * The unique 8 digit numeric identifier of a person
	 */
	private final int ID;
	
	/**
	 * The first and last name of the person, separated by a space
	 */
	private String name;
	
	/**
	 * An array list of the students marks
	 */
	private ArrayList<Double> marks;
	
	private ArrayList<Person> students;
	
	/**
	 * true if the person is a teacher, false if not;
	 */
	private boolean isTeacher;
	
	/**
	 * Creates an instance of a person class, ArrayList of marks is null to start
	 * @param id the unique 8 digit identifier
	 * @param name the persons name
	 * @param isTeacher true if they are a teacher, false if not
	 * @throws IllegalArgumentException
	 */
	public Person(int id, String name, boolean isTeacher) throws IllegalArgumentException{
		this.ID = id;
		this.name = name;
		this.isTeacher = isTeacher;
		if(ID < 0) {
			throw new IllegalArgumentException("ID value cannot be negative");
		}
		
		if((""+ID).length() != 8) {
			throw new IllegalArgumentException("ID must be 8 digits long");
		}
		
		if(name.length() == 0) {
			throw new IllegalArgumentException("Name value cannot be null");
		}
		if {
			marks = new ArrayList<>();
		}
	}
	
	/**
	 * Returns the persons name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the persons ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns the students marks as an array of doubles. If person is a teacher or no marks exist, returns null
	 */
	public double[] getMarks() {
		int n = marks.size();
		
		if(n == 0) {
			return null;
		}
		
		double markA[] = new double[n];
		
		for(int i = 0; i < n; i++) {
			markA[i] = marks.get(i);
		}
		return markA;
	}
	
	public void addMark(double input) {
		marks.add(input);
	}
	
	/**
	 * returns true if the person is a teacher, false if not
	 */
	public boolean isTeacher() {
		return isTeacher;
	}
	
	/**
	 * changes the name set for a person
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Compares people based on their ID's, in increasing order
	 * @param other the other person to compare with
	 * @return a negative integer if the ID ranks higher than the other, a zero if they are the same, and a positive integer if the ID ranks lower than the other
	 */
	public int compareTo(Person other) {
		return this.ID - other.ID;
	}
	
	/**
	 * returns true if the two people are equal, false if they are not
	 * @override equals
	 */
	public boolean equals(Person other) {
		return((this.ID == other.ID) && (this.name.equals(other.name)) && (this.isTeacher == other.isTeacher));
	}
	
	/**
	 * prints out a person object
	 * @override toString
	 */
	public String toString() {
		return ID + ":\t" + name;
	}
}
