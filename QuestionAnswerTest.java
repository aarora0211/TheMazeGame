/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Core.QuestionAnswer;

/**
 * @author aeora44
 *
 */
public class QuestionAnswerTest {

	private QuestionAnswer qa;
	
    @Before
    public void setUp() {
		qa = new QuestionAnswer(3);
    }
	/**
	 * Test method for {@link Core.QuestionAnswer#QuestionAnswer(int)}.
	 */
	@Test
	public void testQuestionAnswer() {
		assertEquals("The question grid is not initiated correctly", qa.getQuestionGrid().length, 3);
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#getDimensions()}.
	 */
	@Test
	public void testGetDimensions() {
		assertEquals("getDimensions() failed", "Number of doors in the room: 3", qa.getDimensions());
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#getQuestionMap()}.
	 */
	@Test
	public void testGetQuestionMap() {
		Map<String, String> questionMap = new HashMap<>();
		questionMap.put("In what country was Freddy Mercury Born?", "Tanzania");
		questionMap.put("What is Mercury's real name?", "Farrokh Bulsara");
		questionMap.put("In which year was Freddie Mercury born?", "1946");
		questionMap.put("What was the name of Freddie's first band?", "Hectics");
		questionMap.put("Which group was Freddie in before he joined Queen?", "Ibex");
		questionMap.put("What was the title of the last song Freddie ever wrote?", "A Winter's Tale");
		qa.setQuestionMap(questionMap);
		assertEquals("getQuestionMap() Failed", questionMap, qa.getQuestionMap());
		
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#setQuestionMap(java.util.Map)}.
	 */
	@Test
	public void testSetQuestionMap() {
		assertEquals("setQuestionMap() failed", 0, qa.getQuestionMap().size());
		Map<String, String> questionMap = new HashMap<>();
		questionMap.put("In what country was Freddy Mercury Born?", "Tanzania");
		questionMap.put("What is Mercury's real name?", "Farrokh Bulsara");
		questionMap.put("In which year was Freddie Mercury born?", "1946");
		questionMap.put("What was the name of Freddie's first band?", "Hectics");
		questionMap.put("Which group was Freddie in before he joined Queen?", "Ibex");
		questionMap.put("What was the title of the last song Freddie ever wrote?", "A Winter's Tale");
		qa.setQuestionMap(questionMap);
		assertEquals("setQuestionMap() failed", questionMap, qa.getQuestionMap());
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#getHintsMap()}.
	 */
	@Test
	public void testGetHintsMap() {
		Map<String, String> hintsMap = new HashMap<>();
		hintsMap.put("In what country was Freddy Mercury Born?", "Its a country in East Africa!");
		hintsMap.put("What is Mercury's real name?", "Starts with F and ends with B");
		hintsMap.put("In which year was Freddie Mercury born?", "The year is xxx6");
		qa.setHintsMap(hintsMap);
		assertEquals("getHintsMap() failed", hintsMap, qa.getHintsMap());
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#setHintsMap(java.util.Map)}.
	 */
	@Test
	public void testSetHintsMap() {
		assertEquals("setHintsMap() failed", 0, qa.getHintsMap().size());
		Map<String, String> hintsMap = new HashMap<>();
		hintsMap.put("In what country was Freddy Mercury Born?", "Its a country in East Africa!");
		hintsMap.put("What is Mercury's real name?", "Starts with F and ends with B");
		hintsMap.put("In which year was Freddie Mercury born?", "The year is xxx6");
		qa.setHintsMap(hintsMap);
		assertEquals("setHintsMap()", hintsMap, qa.getHintsMap());
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#checkAnswer(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCheckAnswer() {
		Map<String, String> questionMap = new HashMap<>();
		questionMap.put("In what country was Freddy Mercury Born?", "Tanzania");
		questionMap.put("What is Mercury's real name?", "Farrokh Bulsara");
		questionMap.put("In which year was Freddie Mercury born?", "1946");
		questionMap.put("What was the name of Freddie's first band?", "Hectics");
		questionMap.put("Which group was Freddie in before he joined Queen?", "Ibex");
		questionMap.put("What was the title of the last song Freddie ever wrote?", "A Winter's Tale");
		qa.setQuestionMap(questionMap);
		
		assertEquals("checkAnswer() failed", false, qa.checkAnswer("In which year was Freddie Mercury born?", "1950"));
		assertEquals("checkAnswer() failed", true, qa.checkAnswer("In which year was Freddie Mercury born?", "1946"));
	}

	/**
	 * Test method for {@link Core.QuestionAnswer#generateRandomQuestions()}.
	 */
	@Test
	public void testGenerateRandomQuestions() {
		Map<String, String> questionMap = new HashMap<>();
		questionMap.put("In what country was Freddy Mercury Born?", "Tanzania");
		questionMap.put("What is Mercury's real name?", "Farrokh Bulsara");
		questionMap.put("In which year was Freddie Mercury born?", "1946");
		questionMap.put("What was the name of Freddie's first band?", "Hectics");
		questionMap.put("Which group was Freddie in before he joined Queen?", "Ibex");
		questionMap.put("What was the title of the last song Freddie ever wrote?", "A Winter's Tale");
		qa.setQuestionMap(questionMap);
		
		for (int j = 0; j < 10; j++) {
			qa.generateRandomQuestions();
			System.out.println("Random Questions Generated Test " + (j + 1) +":");
			final String[] randomQuestions = qa.getQuestionGrid();
			for (int i = 0; i < randomQuestions.length; i++) {
				System.out.println(randomQuestions[i]);
			}
			System.out.println();
		}
	}
}