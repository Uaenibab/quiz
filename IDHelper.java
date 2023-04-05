package CS2043_W23_Team_8;

import java.util.ArrayList;
import java.io.*;

public class IDHelper {
	
	
	/**
	 * Reads the people contained in a binary file and returns an array list of them
	 * @return an ArrayList of Person classes from the binary file
	 */
	public static ArrayList<Person> loadPeople(){
		ObjectInputStream fileIn = null;
		ArrayList<Person> personList = new ArrayList<Person>();
		
		try {
			try {
				fileIn = new ObjectInputStream(new FileInputStream("IDList.dat"));
			}
			catch(FileNotFoundException e){
				return personList;
			}
			catch(IOException e) {
				System.out.println("Error opening Obect Input Stream");
				System.exit(1);
			}
			
			boolean fileEnd = false;
			
			while(!fileEnd) {
				try {
					personList.add((Person)(fileIn.readObject()));
				}
				catch(EOFException e) {
					fileEnd = true;
				}
				catch(IOException e) {
					System.out.println("Problem reading file");
					System.exit(1);
				}
				catch(ClassNotFoundException e) {
					System.out.println("Fatal Error");
					System.exit(1);
				}
			}
			
			return personList;
		}
		
		finally {
			if(fileIn != null) {
				try {
					fileIn.close();
				}
				catch(IOException e) {
					System.out.println("Error closing Object Input Stream");
					System.exit(1);
				}
			}
		}
		
	}
	
	/**
	 * Saves an arrayList of people into a binary file, sorted by ID
	 * Must ensure to always load first, then save to ensure no people get deleted
	 * @param peopleList the list of people to be saved
	 */
	public static void savePeople(ArrayList<Person> peopleList) {
		ObjectOutputStream fileOut = null;
		
		try {
			try {
				fileOut = new ObjectOutputStream(new FileOutputStream("IDList.dat"));
				
				peopleList.sort(null);
				
				for(int i = 0; i < peopleList.size(); i++) {
					fileOut.writeObject(peopleList.get(i));
				}
			}
			catch(IOException e) {
				System.out.println("Issue with Object Output Stream");
				System.exit(1);
			}
		}
		finally {
			if(fileOut != null) {
				try {
					fileOut.close();
				}
				catch(IOException e) {
					System.out.println("Error Closing Object Output Stream");
					System.exit(1);
				}
			}
		}
	}	
	
	public static boolean verifyID(int id) {
	    ArrayList<Person> peopleList = loadPeople();
	    if (peopleList != null) {
	        for (Person person : peopleList) {
	            if (person.getID() == id) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public static void savePerson(Person p) {
		ArrayList<Person> pList = loadPeople();
		pList.add(p);
		savePeople(pList);
	}
	
	/**
	 * Checks the full list of people to see if the ID belongs to someone in the system
	 * @param ID The ID of the person we are looking for
	 * @return The person if they exist, and null if they don't
	 */
	public static Person doesExist(int ID) {
		ArrayList<Person> personList = loadPeople();
		
		for(int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getID() == ID) {
				return personList.get(i);
			}
		}
		
		return null;
	}
}
