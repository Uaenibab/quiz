package CS2043_W23_Team_8;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class TeacherOptions {
	public static void display(Quiz quiz) {
		boolean finished = false;
		Scanner scanner = new Scanner(System.in);
		Question q = null;
		ArrayList<Question> questions = quiz.getQuestions();
		ArrayList<Person> pList = quiz.getIDList();
    	
    	while(!finished) {
    		System.out.println("See all available questions\t(Q)");
    		System.out.println("Edit a question\t\t\t(E)");
    		System.out.println("Edit a question's options\t(O)");
    		System.out.println("Add a question\t\t\t(A)");
    		System.out.println("See student grades\t\t(G)");
    		System.out.println("Add a person to the system\t(P)");
    		System.out.println("Close software\t\t\t(X)");
    		System.out.print("What would you like to do?: ");
    		char choice = Character.toUpperCase(scanner.next().charAt(0));
    		
    		switch(choice) {
    		
    		case 'Q':
    			
    			System.out.println("\n\n");
    			for(int i = 0; i < questions.size(); i++) {
    				Question temp = questions.get(i);
    				System.out.println("Question " + temp.getQuestionId() + ": " + (char)34 + temp.getText() + (char)34 + " IsMC: " + temp.getIsMC() + " Answer: " + temp.getAnswer() + " #Atttempted: " + temp.getNumAttempted() + " #Correct: " + temp.getNumCorrect() + " %Correct: " + temp.getPercentCorrect());
    				temp.printOptions();
    				System.out.println("\n");
    			}
    			System.out.println("\n\n");
    		break;
    		
    		case 'E':
    			
    			q = getQuestionID(scanner, questions);

    			
    			System.out.print("Would you like to modify the question's text? (Y/N): ");
	            String modify = scanner.next();
	            if (modify.equalsIgnoreCase("y")) {
	                System.out.println("Enter new text for question, followed by a period: ");
	                String newText = "";
	                String temp = "";
	                while(scanner.hasNext() && (temp = scanner.next()).charAt(temp.length()-1) != '.') {
	                	newText += temp + " ";
	                }
	                newText += temp.substring(0, temp.length() -1);
	                try {
	        			q.editQuestion(newText);
	        		}
	        		catch(IllegalArgumentException e) {
	        			System.out.println(e.getMessage());
	                }
	            }
	            
	            System.out.print("Would you like to modify the question's answer? (Y/N): ");
	            modify = scanner.next();
	            if (modify.equalsIgnoreCase("y")) {
	                System.out.println("Enter new answer for question: ");
	                String newText = scanner.next();
	                try {
	        			q.editAnswer(Character.toUpperCase(newText.charAt(0)));
	        		}
	        		catch(IllegalArgumentException e) {
	        			System.out.println(e.getMessage());
	                }
	            }
	            break;
	            
    		case 'O':
    			q = getQuestionID(scanner, questions);
    			
    			if(q != null) {
    				HashMap<Character, String> options = q.getOptions();
    				
    				System.out.println(q);
    				System.out.print("Would you like to edit one of the options? (Y/N): ");
    				modify = scanner.next();
    				if (modify.equalsIgnoreCase("y")) {
    					char letter;
    					System.out.print("Enter the letter corresponding to the option you want to edit: ");
    					letter = Character.toUpperCase(scanner.next().charAt(0));
    					if(!options.containsKey(letter)) {
    						System.out.println("Question does not contain option");
    						break;
    					}
    					else {
    						System.out.print("Enter new option text, followed by a period: ");
    						String newText = "";
    		                String temp = "";
    		                while(scanner.hasNext() && (temp = scanner.next()).charAt(temp.length()-1) != '.') {
    		                	newText += temp + " ";
    		                }
    		                newText += temp.substring(0, temp.length() -1);
    		                options.put(letter, newText);
    					}
	            	}
    				
    				System.out.print("Would you like to add an option? (Y/N): ");
    				modify = scanner.next();
    				if (modify.equalsIgnoreCase("y")) {
    					char letter = 65;
    					while(options.containsKey(letter) && letter < 91) {
    						letter++;
    					}
    					if(letter == 91) {
    						System.out.println("Cannot add more options");
    						break;
    					}
    					else {
    						System.out.print("Enter new option text, followed by a period: ");
    						String newText = "";
    		                String temp = "";
    		                while(scanner.hasNext() && (temp = scanner.next()).charAt(temp.length()-1) != '.') {
    		                	newText += temp + " ";
    		                }
    		                newText += temp.substring(0, temp.length() -1);
    		                
    		                options.put(letter, newText);
    					}
	            	}
	            }
	            
    			break;
    			
    		case 'A':
    			
    			System.out.println("Please enter the text of the question you want to add, followed by a period: ");
    			String text = "";
                String temp = "";
                while(scanner.hasNext() && (temp = scanner.next()).charAt(temp.length()-1) != '.') {
                	text += temp + " ";
                }
                text += temp.substring(0, temp.length() -1);
                System.out.print("Is this a multiple choice quesiton (MCQ) or a true or false question (TF)?: ");
                boolean isMCQ;
                
                temp = scanner.next();
                if(temp.equalsIgnoreCase("MCQ")) {
                	isMCQ = true;
                }
                else if(temp.equalsIgnoreCase("TF")) {
                	isMCQ = false;
                }
                else {
                	System.out.println("Not a valid option");
                	break;
                }
                
                HashMap<Character, String> options = new HashMap<>();
                char letter = 65;
                
                if(isMCQ) {
                	System.out.print("How many options would you like to add?: ");
                	int numOfOptions = scanner.nextInt();
                	for(int i = 0; i < numOfOptions; i++) {
                		System.out.println("Enter the text for the option, followed by a period: ");
                		String optionText = "";
                        while(scanner.hasNext() && (temp = scanner.next()).charAt(temp.length()-1) != '.') {
                        	optionText += temp + " ";
                        }
                        options.put(letter, optionText + temp.substring(0, temp.length() -1));
                        letter++;
                	}
                }
                else {
                	options.put('T', "True");
                	options.put('F', "False");
                }
                
                while(true) {
	                System.out.print("Which option is the answer?: ");
	                letter = Character.toUpperCase(scanner.next().charAt(0));
	                Question tempQuestion = new Question(questions.size() + 1, text, isMCQ, options, 'A', 0, 0);
	                tempQuestion.editAnswer(letter);
	                try {
	                	questions.add(tempQuestion);
	                	break;
	                }
	                catch(InputMismatchException e) {
	                	System.out.println(e.getMessage());
	                }
                }
                
    			break;
    			
    		case 'G':
    			
    			break;
    			
    		case 'P':
    			
    			System.out.println("Enter the name of the new person, followed by a period: ");
    			String name = "";
                String tempName = "";
                while(scanner.hasNext() && (tempName = scanner.next()).charAt(tempName.length()-1) != '.') {
                	name += tempName + " ";
                }
                name += tempName.substring(0, tempName.length() -1);
                
                int newID;
                
                while(true) {
                	try {
	                	System.out.print("Enter the 8-digit ID number for this person: ");
	                	newID = scanner.nextInt();
	                
	                	Person test = IDHelper.doesExist(newID);
	                	if(test == null) {
	                		break;
	                	}
	                	else {
	                		System.out.println("ID taken");
	                	}
                	}
                	catch(InputMismatchException e){}
                	System.out.println("Invalid ID");
                }
                
                System.out.print("Is this person a teacher? (Y/N): ");
                modify = scanner.next();
                if(Character.toUpperCase(modify.charAt(0)) == 'Y') {
                	pList.add(new Person(newID, name, true));
                }
                else {
                	pList.add(new Person(newID, name, false));
                }
                
    			break;
    			
    		case 'X':
    			shutdown(quiz);
    			break;
    			
    		default:
    			System.out.println("Invalid Choice");
    			break;
    		}   
    	}
	}

	private static Question getQuestionID(Scanner scanner, ArrayList<Question> questions) {
		int ID;
		Question q;
		System.out.println("Enter question ID:");
		ID = scanner.nextInt();
		q = null;
		for (Question question : questions) {
		    if (question.getQuestionId() == ID) {
		    	q = question;
		    	break;
		    }
		}
		return q;
	}
	
	public static void shutdown(Quiz q) {
    	System.out.println("Thank you for using the software, have a nice day!");
    	try {
    		QuestionFileHelper.writeAllToFile(q.getQuestions());
    		IDHelper.savePeople(q.getIDList());
    		System.exit(0);
    	}
    	catch(FileNotFoundException e) {
    		System.out.println("Error saving results");
    		System.exit(1);
    	}
    }

}
