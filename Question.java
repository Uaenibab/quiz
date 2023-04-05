package CS2043_W23_Team_8;
/**
 * @author Tyler Babineau 3722772
 * This class represents a question that will be a part of a larger quiz
 */
import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
public class Question{
	/**
	 * The questions identification number
	 */
	private int ID;
	
	/**
	 * The actual question being asked
	 */
	private String text;
	
	/**
	 * True if it's a multiple choice question, false if it's a true/false question
	 */
	private boolean isMC;
	
	/**
	 * The list of the question options
	 */
	private HashMap<Character, String> options;
	
	/**
	 * The answer to the question
	 */
	private char answer;
	
	/**
	 * the number of correct answers given for the question
	 */
	private int numCorrect;
	
	/**
	 * the number of times a question has appeared in a quiz
	 */
	private int numAttempted;
	
	/**
	 * the percentage of people who got the question correct
	 */
	private String percentCorrect;
	
	/**
	 * Creates an instance of a question
	 * @param ID the question ID
	 * @param text the text of the question
	 * @param isMC true if it is a multiple choice question, false if true or false question
	 * @param options the map of response options
	 * @param answer the correct answer to the question, as a char
	 * @param numAttempted the number of times the question has appeared on a quiz
	 * @param numCorrect the number of times the question has been answered correctly
	 */
	public Question(int ID, String text, boolean isMC, HashMap<Character, String> options, char answer, int numAttempted, int numCorrect){
		this.ID = ID;
		this.text = text;
		this.isMC = isMC;
		this.options = options;
		this.answer = answer;
		this.numCorrect = numCorrect;
		this.numAttempted = numAttempted;
		this.updatePercentCorrect();
		
		if(numCorrect > numAttempted) {
			throw new IllegalArgumentException("A quesiton cannot have been answered correct more times than it has appeared");
		}
	}
	
	/**
	 * Allows a teacher to edit the question text from the UI
	 */
	public void editQuestion(String newQuestion) throws IllegalArgumentException{
		if(newQuestion == null){
			throw new IllegalArgumentException("The question must contain text");
		}
		text = newQuestion;
		System.out.println("The updated quesiton is: " + text);
		try {
			addToFile();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find question file");
			System.exit(1);
		}
	}
	
	/**
	 * Allows a teacher to update the answer from the UI
	 */
	public void editAnswer(char newAnswer) throws IllegalArgumentException, InputMismatchException{
		
		if('z' >= newAnswer && newAnswer >= 'a') {
			newAnswer -= 32;
		}
		if(newAnswer < 'A' || 'Z' < newAnswer) {
			throw new IllegalArgumentException("Alphabet character expected");
		}
		
		if(!options.containsKey(newAnswer)) {
			throw new InputMismatchException("No option found that matches the input character");
		}
		else {
			answer = newAnswer;
		}
		
		try {
			addToFile();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find question file");
			System.exit(1);
		}
	}
	
	/**
	 * gets the answer to the question
	 * @return the answer to the question
	 */
	public char getAnswer() {
		return answer;
	}
	
	/**
	 * gets the Question text
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	
	/**
	 * gets the question ID
	 * @return the Question ID
	 */
	public int getQuestionId() {
		return ID;
	}
	
	/**
	 * returns the question type, as a boolean
	 * @return true if the questions is multiple choice, false if it is true/false
	 */
	public boolean getIsMC() {
		return isMC;
	}
	
	/**
	 * Returns the question type, as a string
	 * @return MCQ if it is a multiple choice question, TF if it is a true/false question
	 */
	public String getQuestionType() {
		if(isMC) {
			return "MCQ";
		}
		return "TF";
	}
	
	/**
	 * returns the map of options
	 * @return the response options, as a map
	 */
	public HashMap<Character, String> getOptions(){
		return options;
	}
	
	/**
	 * returns the number of times the question has been attempted
	 * @return numAttempted
	 */
	public int getNumAttempted() {
		return numAttempted;
	}
	
	/**
	 * returns the number of times the question has been answered properly
	 * @return numCorrect
	 */
	public int getNumCorrect() {
		return numCorrect;
	}
	
	public String getPercentCorrect() {
		updatePercentCorrect();
		return percentCorrect;
	}
	/**
	 * increments the number of times a question has appeared by one
	 */
	public void incrementNumAttempted() {
		numAttempted++;
		updatePercentCorrect();
	}
	
	/**
	 * increments the number of times a question has been answered correctly by one
	 */
	public void incrementNumCorrect() {
		numCorrect++;
		updatePercentCorrect();
	}
	
	/**
	 * prints out all the question response options to the console
	 */
	public void printOptions() {
		Object[] keys = options.keySet().toArray();
		for(int i = 0; i < options.size(); i++) {
			System.out.println(keys[i] + ": " + (char)34 + options.get(keys[i]) + (char)34);
		}
	}
	
	/**
	 * Takes in the entire file as a String, adds the question where needed, and prints it back to the file
	 * @throws FileNotFoundException
	 */
	public void addToFile() throws FileNotFoundException{
		QuestionFileHelper.writeToFile(this);	
	}
	
	
	/**
	 * Updates the percentCorrect variable to be accurate
	 */
	public String updatePercentCorrect() {
		if(numAttempted  == 0) {
			percentCorrect  = "NA";
		}
		else {
			percentCorrect = (((numCorrect * 100)/(numAttempted))) + "%";
		}
		return percentCorrect;
	}
	
	/**
	 * @Override toString
	 * @return the question to be printed out
	 */
	public String toString() {
		String temp ="Question " + ID + ": " + text;
		
		Object[] keys = options.keySet().toArray();
		for(int i = 0; i < options.size(); i++) {
			temp += "\n" + keys[i] + ": " + options.get(keys[i]);
		}
		
		return temp;
	}
}
