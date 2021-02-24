package org.mazegame;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.*;

public class DBGeneratorTest {
	@Before
	public void init() {
		DBGenerator.init();
	}
	
	@Test
	public void testgetRoomDetail() {
    	Map<String, String> room_data = DBGenerator.getRoomDetail(1);
    	
    	assertEquals(room_data.get("top_door_id"), "none");
    	assertEquals(room_data.get("bottom_door_id"), "1");
    	assertEquals(room_data.get("right_door_id"), "2");
    	assertEquals(room_data.get("left_door_id"), "none");
    	assertEquals(room_data.get("position"), "0, 0");
	}
	
	@Test
	public void testGetDoorDetail() {
    	Map<String, String> door_data = DBGenerator.getDoorDetail(1, DBGenerator.Direction.BOTTOM);
    			
		assertEquals(door_data.get("next_room_id"), "6");
    	assertEquals(door_data.get("status"), "CLOSED");
    	assertFalse(door_data.get("question").isEmpty());
    	assertFalse(door_data.get("question_id").isEmpty());

	}
	
	@Test
	public void testAnswerQuestion() {
    	DBGenerator.doorStatus status = DBGenerator.answerQuestion(1, "wrong answers");
    	assertEquals(status, DBGenerator.doorStatus.FAILED);
	}
	
	@Test
	public void testContinuePlay() {
		Boolean result = DBGenerator.continuePlay(0, 0);    	
    	assertTrue(result);
		
    	Map<String, String> right_door_data = DBGenerator.getDoorDetail(1, DBGenerator.Direction.RIGHT);
    	Map<String, String> bottom_door_data = DBGenerator.getDoorDetail(2, DBGenerator.Direction.BOTTOM);

		Integer question_right_id = Integer.parseInt(right_door_data.get("question_id"));
		Integer question_bottom_id = Integer.parseInt(bottom_door_data.get("question_id"));
		
		DBGenerator.answerQuestion(question_right_id, "wrong answer");
		DBGenerator.answerQuestion(question_bottom_id, "wrong answer");
		try{
		    Thread.sleep(500);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
	
    	Boolean after_result = DBGenerator.continuePlay(0, 0);    	
    	assertFalse(after_result);
	}
	
	@After
	public void destory() {
		DBGenerator.deleteDB();
	}
}
