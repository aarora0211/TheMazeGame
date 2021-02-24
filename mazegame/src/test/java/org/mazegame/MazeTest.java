/*
 * Group Project Trivia Maze
 * Fall 2020
 * 
 * Since the questions are randomly assigned to the doors, it is not
 */



package org.mazegame;



import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MazeTest {
	private Maze myMaze;
	@Before
	public void setUp() throws Exception {
		myMaze = new Maze();
	}
	
	/**
	 * Test constructor.
	 */
	@Test
	public void test() {
		assertEquals(0, myMaze.getX());
		assertEquals(0, myMaze.getY());
	}
	/**
	 * Testing canContinue
	 */
	@Test
	public void testcanContinue() {
		assertEquals(true, myMaze.canContinue());
	}
	
	/**
	 * Testing getX
	 */
	@Test
	public void testgetX() {
		assertEquals(0, myMaze.getX());
		myMaze.move(0, 1, DBGenerator.Direction.RIGHT);
		assertEquals(1, myMaze.getX());	
	}
	
	/**
	 * Testing getY
	 */
	@Test
	public void testgetY() {
		assertEquals(0, myMaze.getY());
		myMaze.move(1, 0, DBGenerator.Direction.BOTTOM);
		assertEquals(1, myMaze.getY());
	}
	
	/**
	 * Testing getMaze
	 */
	@Test
	public void testgetMaze() {
		//Testing whether the maze retrieved does indeed have a room id of 1 
		//the structure.
		assertEquals(1, myMaze.getMaze()[myMaze.getY()][myMaze.getY()].getRoomId());
	}
	
	/**
	 * Testing firstR {
	 */
	@Test
	public void testfirstR() {
		//Comparing door facing TOP directions
		assertEquals( myMaze.getMaze()[0][0].getRoom().get(0), myMaze.firstR(myMaze.getY(), myMaze.getX()));
		assertEquals( myMaze.getMaze()[1][0].getRoom().get(0), myMaze.firstR(1, 0));
	}
	
	/**
	 * Testing firstC {
	 */
	@Test
	public void testfirstC() {
		//Comparing door facing LEFT directions
		assertEquals( myMaze.getMaze()[0][0].getRoom().get(1), myMaze.firstC(myMaze.getY(), myMaze.getX()));
		assertEquals( myMaze.getMaze()[1][0].getRoom().get(1), myMaze.firstC(1, 0));
	}
	
	
	/**
	 * Testing move
	 */
	@Test
	public void testmove() {
		//off the bat, the game begins at 0 0
		//Moving to room to the right at 0, 1
		myMaze.move(0, 1, DBGenerator.Direction.RIGHT);
		assertEquals(1, myMaze.getX());
		assertEquals(0, myMaze.getY());
		
		//Making two moves from 0,1 to 1,1 then to 1,0
		myMaze.move(1, 1, DBGenerator.Direction.BOTTOM);
		myMaze.move(1, 0, DBGenerator.Direction.LEFT);
		assertEquals(0, myMaze.getX());
		assertEquals(1, myMaze.getY());
	}

}
