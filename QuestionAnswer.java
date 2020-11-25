package Core;

import java.util.HashMap;
import java.util.Map;

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
	private String[][] questionGrid;
	
	public QuestionAnswer(final int rows, final int numberDoors) {
		questionGrid = new String[rows][numberDoors];
		setQuestionMap(new HashMap<>());
		setHintsMap(new HashMap<>());
	}
   
	public String getDimensions() {
		return "" + questionGrid.length + ", " + questionGrid[0].length; 
	}
   

	public Map<String, String> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<String, String> questionMap) {
		this.questionMap = questionMap;
	}
	
	public Map<String, String> getHintsMap() {
		return hintsMap;
	}

	public void setHintsMap(Map<String, String> hintsMap) {
		this.hintsMap = hintsMap;
	}

   
	public static void main(final String[][] theArgs) {
		QuestionAnswer qa = new QuestionAnswer(3, 5);
	    System.out.println(qa.getDimensions());
	}

}

