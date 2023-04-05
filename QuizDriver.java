package CS2043_W23_Team_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class QuizDriver {
    private static Scanner scanner = new Scanner(System.in);
    private static int numQuestions;
    private static int numCorrect;
    private static int numAttempted;
    private static Question[] questions;

    public static void main(String[] args) {
        try {
            questions = loadQuestionsFromFile("Questions.txt");
            numQuestions = questions.length;
            numCorrect = 0;
            numAttempted = 0;
            System.out.println("Welcome to the quiz!\n");
            for (int i = 0; i < numQuestions; i++) {
                System.out.println("Question " + (i+1) + ":");
                System.out.println(questions[i].getText());
                questions[i].printOptions();
                char answer = readAnswerFromUser();
                if (questions[i].checkAnswer(answer)) {
                    System.out.println("Correct!");
                    numCorrect++;
                } else {
                    System.out.println("Incorrect!");
                }
                questions[i].incrementNumAttempted();
                numAttempted++;
                System.out.println();
            }
            System.out.println("Quiz complete! You got " + numCorrect + " out of " + numAttempted + " questions correct.");
            updatePercentCorrect();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Questions file not found.");
        } finally {
            scanner.close();
        }
    }

    private static Question[] loadQuestionsFromFile(String filename) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filename));
        int numQuestions = Integer.parseInt(fileScanner.nextLine());
        Question[] questions = new Question[numQuestions];
        for (int i = 0; i < numQuestions; i++) {
            String[] parts = fileScanner.nextLine().split("-");
            int id = Integer.parseInt(parts[0]);
            String text = parts[1].substring(1, parts[1].length()-1); // remove quotes
            boolean isMultipleChoice = Boolean.parseBoolean(parts[2]);
            HashMap<Character, String> options = new HashMap<>();
            String[] optionStrings = parts[3].split(",");
            for (String optionString : optionStrings) {
                String[] optionParts = optionString.trim().split(":");
                options.put(optionParts[0].charAt(0), optionParts[1].substring(1, optionParts[1].length()-1)); // remove quotes
            }
            char answer = parts[4].charAt(0);
            int numAttempted = Integer.parseInt(parts[5]);
            int numCorrect = Integer.parseInt(parts[6]);
            questions[i] = new Question(id, text, isMultipleChoice, options, answer, numAttempted, numCorrect);
        }
        fileScanner.close();
        return questions;
    }

    private static char readAnswerFromUser() {
        char answer = ' ';
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("Enter your answer: ");
                answer = scanner.next().toUpperCase().charAt(0);
                if (!Character.isLetter(answer)) {
                    throw new InputMismatchException();
                }
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a single letter (A, B, C, or D).");
                scanner.nextLine(); // consume remaining input
            }
        }
        return answer;
    }

    
private static void updatePercentCorrect() {
double percentCorrect = ((double)numCorrect / numAttempted) * 100;
System.out.printf("You got %.2f%% of the questions correct.%n", percentCorrect);
for (Question question : questions) {
question.updatePercentCorrect();
}
}
}
