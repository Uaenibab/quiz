package CS2043_W23_Team_8;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class QuestionTest {

	int count = 0;
	
	@Test
	void testEditQuestion() {
		Question q = createQuestion(10, 5);
		String newText = "How many doughnuts can I eat at once";
		
		try {
			q.editQuestion(newText);
			Assert.assertTrue(q.getText().equals(newText));
			
			q.editQuestion(null);
			Assert.fail("Should not allow null values for text");
		}
		catch(IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
		
	}

	@Test
	void testEditAnswer() {
		Question q = createQuestion(20, 4);
		
		try {
			q.editAnswer('C');
			Assert.assertTrue(q.getAnswer() == 'C');
			
			q.editAnswer('a');
			Assert.assertTrue(q.getAnswer() == 'A');
			
			q.editAnswer('!');
		}
		catch(IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
		catch(InputMismatchException e) {
			Assert.fail("Should not throw this exception");
		}
		
		try {
			q.editAnswer('F');
			Assert.fail("Should not reach this point");
		}
		catch(IllegalArgumentException e) {
			Assert.fail("Should not throw this exception");
		}
		catch(InputMismatchException e) {
			Assert.assertTrue(true);
		}
		
	}

	@Test
	void testGetAnswer() {
		Question q = createQuestion(5, 2);
		Assert.assertTrue(q.getAnswer() == 'B');
	}

	@Test
	void testGetText() {
		Question q = createQuestion(8, 3);
		Assert.assertTrue(q.getText().equals("How many planets are there in our solar system"));
	}

	@Test
	void testPrintOptions() {
		Question q = createQuestion(4, 1);
		q.printOptions();
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Did the text [A. 7, B. 8, C. 9, D. 3] just print out? (y/n): ");
		char input = scan.next().charAt(0);
		Assert.assertTrue(input == 'y');
		scan.close();
	}

	@Test
	void testAddToFile() {
		Question q = createQuestion(8, 1);
		Question q2 = createQuestion2(6, 3);
		Scanner scan = null;
		try {
			q.addToFile();
			q2.addToFile();
			Assert.assertTrue(true);
			
			scan = new Scanner(new File("Questions.txt"));
			scan.nextLine();
			String fileText = scan.nextLine();
			Assert.assertTrue(fileText.equals("1-" + (char)34 + "How many planets are there in our solar system" + (char)34 + "-true-B-8-1-12%"));
			
			scan.close();
			
			scan = new Scanner(new File("Questions.txt"));
			q.incrementNumAttempted();
			q.incrementNumAttempted();
			q.addToFile();
			scan.nextLine();
			fileText = scan.nextLine();
			Assert.assertTrue(fileText.equals("1-" + (char)34 + "How many planets are there in our solar system" + (char)34 + "-true-B-10-1-10%"));
		
		}
		catch(FileNotFoundException e){
			Assert.fail();
		}
		finally {
			if(scan != null) {
				scan.close();
			}
		}
	}

	@Test
	void testUpdatePercentCorrect() {
		Question q = createQuestion(6, 2);
		String percentStart = q.updatePercentCorrect();
		
		q.incrementNumAttempted();
		q.incrementNumAttempted();
		q.incrementNumAttempted();
		q.incrementNumAttempted();
		
		Assert.assertFalse(percentStart.equals(q.updatePercentCorrect()));
	}
	
	public Question createQuestion(int total, int pass) {
		HashMap<Character, String> s = new HashMap<>();
		s.put('A', "7");
		s.put('B', "8");
		s.put('C', "9");
		s.put('D', "10");
		
		
		count++;
		return new Question(count, "How many planets are there in our solar system", true, s, 'B', total, pass );
	}
	
	public Question createQuestion2(int total, int pass) {
		HashMap<Character, String> s = new HashMap<>();
		s.put('A', "1");
		s.put('B', "7");
		s.put('C', "3");
		s.put('D', "4");
		
		
		count++;
		return new Question(count, "How many planets are there in our solar system", true, s, 'B', total, pass );
	}

}
