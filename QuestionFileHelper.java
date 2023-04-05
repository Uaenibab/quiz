package CS2043_W23_Team_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionFileHelper {
	
	/**
	 * Writes a specific question to a file
	 * @param q the question to be written to the file
	 * @throws FileNotFoundException
	 */
	public static void writeToFile(Question q) throws FileNotFoundException{
		int ID = q.getQuestionId();
		String text = q.getText();
		boolean isMC = q.getIsMC();
		HashMap<Character, String> options = q.getOptions();
		char answer = q.getAnswer();
		int numCorrect = q.getNumCorrect();
		int numAttempted = q.getNumAttempted();

		
		
		Object keys[] = options.keySet().toArray();
		String title = "QuestionID-Quesiton-isMCQ-Answer-#tried-#correct-%correct";
		String totalFile = title;
		char quoteMark = (char)34;
		String qToAdd = "\n"  + ID + "-" + quoteMark + text + quoteMark + "-" + isMC + "-" + answer + "-" + numAttempted + "-" + numCorrect + "-" + q.getPercentCorrect();
		for(int i = 0; i < options.size(); i++) {
			qToAdd += "\n-" + keys[i] + "-" + quoteMark + options.get(keys[i]) + quoteMark;
		}
				
		boolean qAdded = false;
		PrintWriter pw = null;
		Scanner fscan = null;
		Scanner qScan = null;
		q.updatePercentCorrect();
		File qFile = new File("Questions.txt");
		String temp = "";
		
		try {
			fscan = new Scanner(qFile);
			temp = fscan.nextLine();
			if(temp.equals(title) && fscan.hasNext()) {
				temp = fscan.nextLine();
			}
			
			while(fscan.hasNext()) {
				if(temp.charAt(0) == '-') {
					while(fscan.hasNext() && temp.charAt(0) == '-') {
						totalFile += "\n" + temp;
						temp = fscan.nextLine();
					}
				}
				qScan = new Scanner(temp).useDelimiter("-");
				
				if(qScan.next().equals(ID + "")){
					totalFile += qToAdd;
					qAdded = true;
										
					if(fscan.hasNext()) {
						temp = fscan.nextLine();
					}
					while(fscan.hasNext() && temp.charAt(0) == '-') {
						temp = fscan.nextLine();
					}
				}
				else{
					totalFile += "\n" + temp;
					if(fscan.hasNext()) {
						temp = fscan.nextLine();
					}
				}
				
				if(qScan != null) {
					qScan.close();
				}
			}
			if(!qAdded) {
				totalFile += qToAdd;
			}
		}
		catch(FileNotFoundException e) {
			totalFile = title + qToAdd;
		}
		
		try {
			pw = new PrintWriter(new File("Questions.txt"));
			pw.print(totalFile);
		}
		finally {
			if(fscan != null) {
				fscan.close();
			}
			if(pw != null) {
				pw.close();
			}
		}
	}
	
	/**
	 * loads the list of questions from the file
	 * @return the ArrayList of all the questions in the file
	 */
	public static ArrayList<Question> loadFromFile() {
		Scanner fileScan = null;
		ArrayList<Question>  qList = new ArrayList<>();
		
		try {
			fileScan = new Scanner(new File("Questions.txt"));
			fileScan.nextLine();
			String tempLine = fileScan.nextLine();
			
			while(fileScan.hasNext()) {
				int ID;
				String text;
				boolean isMC;
				char answer;
				HashMap<Character, String> options = new HashMap<>();
				int numAttempted;
				int numCorrect;
				
				Scanner lineScan = new Scanner(tempLine).useDelimiter("-");
				
				ID = lineScan.nextInt();
				text = (lineScan.next());
				text = text.substring(1, text.length()-1);
				isMC = lineScan.nextBoolean();
				answer = lineScan.next().charAt(0);
				numAttempted = lineScan.nextInt();
				numCorrect = lineScan.nextInt();
				
				boolean allAnswersFound = false;
				
				while(fileScan.hasNext() && !allAnswersFound) {
					char letter;
					String optionText;
					
					tempLine = fileScan.nextLine();
					if(tempLine.charAt(0) == '-') {
						lineScan = new Scanner(tempLine).useDelimiter("-");
						letter = lineScan.next().charAt(0);
						optionText = lineScan.next();
						optionText = optionText.substring(1, optionText.length() -1);
						options.putIfAbsent(letter, optionText);					
					}
					else {
						allAnswersFound = true;
					}
				}
				qList.add(new Question(ID, text, isMC, options, answer, numAttempted, numCorrect));
				
				if(lineScan != null) {
					lineScan.close();
				}
			}
			
		}
		catch(FileNotFoundException e) {
			return qList;
		}
		finally {
			if(fileScan != null) {
				fileScan.close();
			}
		}
		
		return qList;
	}
	
	/**
	 * Saves the ArrayList of all the questions
	 * @param qList the list of all questions
	 * @throws FileNotFoundException 
	 */
	public static void writeAllToFile(ArrayList<Question> qList) throws FileNotFoundException{
		for(int i = 0; i < qList.size(); i++) {
			writeToFile(qList.get(i));
		}
	}
}
