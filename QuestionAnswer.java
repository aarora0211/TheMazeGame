import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.*;
import java.io.*;


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
      questionMap = new HashMap<>();
      hintsMap = new HashMap<>();
	}
   
   public String getDimensions() {
      return "" + questionGrid.length + ", " + questionGrid[0].length; 
   }
   
   public static void main(final String[][] theArgs) {
      QuestionAnswer qa = new QuestionAnswer(3, 5);
      System.out.println(qa.getDimensions());
   }
}
