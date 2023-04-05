package CS2043_W23_Team_8;

import java.util.ArrayList;

public class QuizDriver2{
	public static void main(String[] args) {
		
		ArrayList<Question> questionList = QuestionFileHelper.loadFromFile();
		Quiz q = new Quiz("1", questionList);
		q.runQuiz();
	}
}

