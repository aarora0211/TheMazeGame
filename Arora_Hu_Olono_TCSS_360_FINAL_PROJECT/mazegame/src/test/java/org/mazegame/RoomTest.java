/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import junit.framework.TestCase;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class RoomTest extends TestCase {
	
	/**The test room.*/
	private Room myRoom;
	
	private static final int ROOMID = 2;
	
	private static final int DOORID1 = 1;
	
	private static final int DOORID2 = 2;
	
	private static final int DOORID3 = 3;
	
	private static final int DOORID4 = 4;
	
	private static final int X = 1;
	
	private static final int Y = 1;

	/**
	 * Sets up the test Room object before all the tests.
	 */
	@Before
	protected void setUp() throws Exception {
		myRoom = new Room(ROOMID, Y, X);
		
	}
	
	/**
	 * Testing the room constructor.
	 */
	@Test
	public void testRoom() {
		assertEquals(X, myRoom.getX());
		assertEquals(Y, myRoom.getY());
		assertEquals(ROOMID, myRoom.getRoomId());
	}
	
	/**
	 * Testing the populate method
	 */
	@Test
	public void testpopulate() {
		//We create an ArrayList of Doors.
		ArrayList<Door> temp = new ArrayList<Door>();
		temp.add(new Door(DOORID1));
		temp.add(new Door(DOORID2));
		temp.add(new Door(DOORID3));
		temp.add(new Door(DOORID4));
		
		//Populate the Room with doors.
		myRoom.populate(temp);
		
		//If room is properly populated the size should be 4.
		assertEquals(4, myRoom.getRoom().size());
	}
	
	/**
	 * Testing getRoomID
	 */
	@Test
	public void testgetRoomId() {
		assertEquals(ROOMID, myRoom.getRoomId());
	}
	
	/**
	 * Testing getX
	 */
	@Test
	public void testgetX() {
		assertEquals(X, myRoom.getX());
	}
	
	/**
	 * Testing getY
	 */
	@Test
	public void testgetY() {
		assertEquals(Y, myRoom.getY());
	}
	
	/**
	 * Testing getRoom
	 */
	@Test
	public void testgetRoom() {
		//We create an ArrayList of Doors.
		ArrayList<Door> temp = new ArrayList<Door>();
		temp.add(new Door(DOORID1));
		temp.add(new Door(DOORID2));
		temp.add(new Door(DOORID3));
		temp.add(new Door(DOORID4));
				
		//Populate the Room with doors.
		myRoom.populate(temp);
		myRoom.populate(temp);
		
		//Checking if the room returned is the same we populated.
		assertEquals(temp, myRoom.getRoom());
	}
	
}
