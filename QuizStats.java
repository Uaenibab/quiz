package CS2043_W23_Team_8;

import java.util.HashMap;
import java.util.Map;

public class QuizStats {

    private Map<String, Integer> correctCount;
    private Map<String, Integer> incorrectCount;
    private Map<String, Double> averageTime;
    private Map<String, Double> percentCorrect;

    public QuizStats() {
        this.correctCount = new HashMap<>();
        this.incorrectCount = new HashMap<>();
        this.averageTime = new HashMap<>();
        this.percentCorrect = new HashMap<>();
    }

    public void recordAnswer(String questionId, boolean isCorrect, double time) {
        if (isCorrect) {
            correctCount.put(questionId, correctCount.getOrDefault(questionId, 0) + 1);
        } else {
            incorrectCount.put(questionId, incorrectCount.getOrDefault(questionId, 0) + 1);
        }
        double currAvgTime = averageTime.getOrDefault(questionId, 0.0);
        int count = correctCount.getOrDefault(questionId, 0) + incorrectCount.getOrDefault(questionId, 0);
        averageTime.put(questionId, (currAvgTime * (count - 1) + time) / count);
    }

    public void calculatePercentCorrect() {
        int totalQuestions = correctCount.keySet().size() + incorrectCount.keySet().size();
        for (String questionId : correctCount.keySet()) {
            double percent = ((double) correctCount.get(questionId) / totalQuestions) * 100;
            percentCorrect.put(questionId, percent);
        }
        for (String questionId : incorrectCount.keySet()) {
            double percent = ((double) correctCount.getOrDefault(questionId, 0) / totalQuestions) * 100;
            percentCorrect.put(questionId, percent);
        }
    }

    public Map<String, Integer> getCorrectCount() {
        return correctCount;
    }

    public Map<String, Integer> getIncorrectCount() {
        return incorrectCount;
    }

    public Map<String, Double> getAverageTime() {
        return averageTime;
    }

    public Map<String, Double> getPercentCorrect() {
        return percentCorrect;
    }

    public void displayStatistics() {
        System.out.println("Question Statistics:");
        for (String questionId : correctCount.keySet()) {
            System.out.println("Question " + questionId + ":");
            System.out.println("\tNumber of times answered correctly: " + correctCount.get(questionId));
            System.out.println("\tNumber of times answered incorrectly: " + incorrectCount.getOrDefault(questionId, 0));
            System.out.printf("\tAverage time to answer: %.2f seconds\n", averageTime.getOrDefault(questionId, 0.0));
            System.out.printf("\tPercentage of users who answered correctly: %.2f%%\n", percentCorrect.getOrDefault(questionId, 0.0));
            System.out.println();
        }
    }
}
