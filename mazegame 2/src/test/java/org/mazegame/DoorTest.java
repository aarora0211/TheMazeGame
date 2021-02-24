/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * Some tests for the Door class.
 * @author olono_arora_zixuan
 *
 */
public class DoorTest extends TestCase {
	
	private static final int DOORID = 3;
	
	
	
	private Door myDoor;

	/**
	 * Sets up the test Door object before all the tests.
	 */
	@Before
	protected void setUp() throws Exception {
		myDoor = new Door(DOORID);
	}
	
	
	/**
	 * Testing the Door constructor.
	 */
	@Test
	public void testDoor() {
		assertEquals(true, myDoor.canAsk());
		assertEquals(true, myDoor.isMyLock());
		assertEquals(3, myDoor.getId());
	}
	
	/**
	 * Testing the ID getter specifically.
	 */
	@Test
	public void testGetId() {
		assertEquals(DOORID, myDoor.getId());
	}
	
	/**
	 * Testing the setting of a lock.
	 */
	@Test
	public void testsetMyLock() {
		myDoor.setMyLock(true);
		assertEquals(true, myDoor.isMyLock());
		
		myDoor.setMyLock(false);
		assertEquals(false, myDoor.isMyLock());
	}
	
	/**
	 * Testing canAsk specifically.
	 */
	@Test
	public void testcanAsk() {
		assertEquals(true, myDoor.canAsk());
		myDoor.denyAsk();
		assertEquals(false, myDoor.canAsk());
	}
	
	/**
	 * Testing denyAsk
	 */
	@Test
	public void testdenyAsk() {
		myDoor.denyAsk();
		assertEquals(false, myDoor.canAsk());
	}
	
	
}
