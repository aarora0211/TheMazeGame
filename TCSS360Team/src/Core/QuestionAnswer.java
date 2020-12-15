package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class QuestionAnswer {
	
	/**
	 * 
	 */
	private Map<String, String> questionMap;
	
	/**
	 * 
	 */
	private Map<String, String> hintsMap;
	
	/**
	 * 
	 */
	private String[] questionGrid;
	
	/**
	 * 
	 * @param rows
	 */
	public QuestionAnswer(final int numDoors) {
		questionGrid = new String[numDoors];
		setQuestionMap(new HashMap<>());
		setHintsMap(new HashMap<>());
	}
   
	/**
	 * 
	 * @return
	 */
	public String getDimensions() {
		return "Number of doors in the room: " + questionGrid.length;
	}
   
	public String[] getQuestionGrid() {
		generateRandomQuestions();
		return this.questionGrid;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, String> getQuestionMap() {
		return questionMap;
	}

	/**
	 * 
	 * @param questionMap
	 */
	public void setQuestionMap(Map<String, String> questionMap) {
		this.questionMap = questionMap;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getHintsMap() {
		return hintsMap;
	}

	/**
	 * 
	 * @param hintsMap
	 */
	public void setHintsMap(Map<String, String> hintsMap) {
		this.hintsMap = hintsMap;
	}

	/**
	 * 
	 * @param theQuestion
	 * @param theAnswer
	 * @return
	 */
	public boolean checkAnswer(String theQuestion, String theAnswer) {
		if (questionMap.containsKey(theQuestion)) {
			if (questionMap.get(theQuestion).equals(theAnswer)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param question
	 * @return
	 */
	public String getHint(final String question) {
		if(hintsMap.containsKey(question)) {
			return hintsMap.get(question);
		}
		return "";
	}
	
	/**
	 * 
	 */
	public void generateRandomQuestions() {
		Map<String, String> tempQuestionMap = getQuestionMap();
		final Set<String> keys = tempQuestionMap.keySet();
        final Iterator<String> itr = keys.iterator();
        Set<Integer> randomQuestionsSet = new HashSet<>();
    	Random rand = new Random();
    	int randomQuestion = rand.nextInt(tempQuestionMap.size());
    	//System.out.println("Random Question Number " + randomQuestion);
        while (itr.hasNext()) {
        	while (randomQuestionsSet.contains(randomQuestion)) {
        		randomQuestion = rand.nextInt(tempQuestionMap.size());
        	}
        	//System.out.println("Random Question Number " + randomQuestion);
        	randomQuestionsSet.add(randomQuestion);
        	if (randomQuestionsSet.size() == questionGrid.length) {
        		break;
        	}
        }
        final Iterator<Integer> randQuestionIterator = randomQuestionsSet.iterator();
        int i = 0;
    	List<String> randomQuestionList = new ArrayList<>(keys);
        while(randQuestionIterator.hasNext()) {
        	int questionNumber = randQuestionIterator.next();
        	questionGrid[i] = randomQuestionList.get(questionNumber);
        	i += 1;
        }
	}
}