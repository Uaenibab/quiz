package CS2043_W23_Team_8;
import CS2043_W23_Team_8.IDHelper;
import java.io.FileNotFoundException;
/**
 * @author Anh Tu Pham 3719316
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.*;
import java.io.*;


public class Quiz {
    
    private String uniqueId;
    private ArrayList<Question> questions;
    private ArrayList<Person> IDList;
    private QuizStats quiz;
    
    public Quiz(String uniqueId, ArrayList<Question> questions) {
        this.uniqueId = uniqueId;
        this.questions = questions;
        this.quiz = new QuizStats();
        IDList = IDHelper.loadPeople();
        if(IDList.size() ==0) {
        	Scanner scan = new Scanner(System.in);
        	System.out.print("No ID list found. Please enter teacher name: ");
        	String name = scan.nextLine();
        	
        	boolean correct = false;
        	while(!correct) {
        		System.out.print("Please enter an 8-digit ID for " + name + ": ");
        		int ID;
        		try {
        			ID = Integer.parseInt(scan.next());
        			if((ID+"").length() == 8) {
        				correct = true;
        				Person teacher = new Person(ID, name, true);
        				IDHelper.savePerson(teacher);
        			}
        		}
        		catch(NumberFormatException e) {}
        		if(!correct) {
        			System.out.println("\nIncorrect ID format, must be an 8-digit number");
        		}
        	}
        	System.out.println("\n\n");
        }
    }
    
    public void runQuiz() {
        Scanner scanner = new Scanner(System.in);
        Person currentPerson = null;
        int count = 0;
        System.out.println("Welcome to the quiz!");
        
        boolean correct = false;
        while(!correct && count < 3) {
        	System.out.print("Please enter your 8-digit ID: ");
        	try {
        		int StudentID = Integer.parseInt(scanner.next());
        		currentPerson = IDHelper.doesExist(StudentID);
        	}
        	catch(NumberFormatException e) {};
        	if(currentPerson != null) {
        		correct = true;
        	}
        	else {
        		System.out.println("Invalid ID");
        		count++;
        	}
        }
        if(!correct) {
        	System.exit(1);
        }
        
        System.out.println("Welcome " + currentPerson.getName());
        if (!currentPerson.isTeacher()) {
            System.out.print("Which type of questions would you like to answer? (MCQ/TF) ");
            String questionType = scanner.next();
            ArrayList<Question> selectedQuestions = new ArrayList<>();
            
            for (Question question : questions) {
                if (question.getQuestionType().equalsIgnoreCase(questionType)) {
                    selectedQuestions.add(question);
                }
            }
            
            Collections.shuffle(selectedQuestions);
            ArrayList<Character> answers = new ArrayList<>();
            ArrayList<long> currentTime = new ArrayList<>();
            
            long startTime = System.currentTimeMillis(); // start the timer
            
            for (int i = 0; i < 10; i++) {
                Question question = selectedQuestions.get(i);
                System.out.println("Question " + (i+1) + ": " + question.getText());
                question.printOptions();
                System.out.print("Answer: ");

                char answer = (scanner.next()).charAt(0);
                answers.add(Character.toUpperCase(answer));
            }
            
            long endTime = System.currentTimeMillis(); // end the timer
            
            int correctAnswers = 0;
            for (char answer : answers) {
            	int i = 0;
                if (answer == selectedQuestions.get(i).getAnswer()) {
                    correctAnswers++;
                    double time;
                    if(i == 0){
                        time = currentTime.get(i) - start;
                    }
                    else{
                        time = currentTime.get(i) - currentTime.get(i-1);   
                    }
                    quiz.recordAnswer(selectedQuestions.get(i).getQuestionId(), true, time);
                }
                else{
                    quiz.recordAnswer(selectedQuestions.get(i).getQuestionId(), false, time);
                }
            }
            
            double score = ((double)correctAnswers / 10) * 100;
            ArrayList<Person> students = loadPeople();
    	    if (peopleList != null) {
    	        for (Person student : students) {
    	            if (student.getID() == studentId) {
    	                student.addMark(score);
    	            }
    	        }
    	    }
            long duration = endTime - startTime; // calculate duration of the quiz in milliseconds
            System.out.println("Your final score is: " + score + "%");
            System.out.println("Time taken: " + duration / 1000 + " seconds");
            
        } 
        else {
        	TeacherOptions.display(this);
        } 
    }
    
    /**
     * returns the list of questions from the quiz, may be different than the file
     * @return the ArrayList of questions
     */
    public ArrayList<Question> getQuestions(){
    	return questions;
    }
    
    /**
     * returns the list of people in the system, may be different than the file
     * @return the ArrayList of people
     */
    public ArrayList<Person> getIDList(){
    	return IDList;
    }
}
