/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class acts as the overall structure encompassing the Maze, rooms, 
 * doors, and all.
 * @author olono_arora_zixuan
 */
public class Maze implements Serializable {
	
	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 6914463255432522224L;


	/**Maze itself */
	private Room[][] myMaze; 
	
	
	/**Dimensions of the Maze */
	private static final int ROWS = 6;
	private static final int COLUMNS = 5;
	private static final int MAX_Q = 49;
	
	/**The current row of the player within the maze. */
	private int curRow;
	
	/**The current column within the maze. */
	private int curCol;
	
	/**The proposed column to move to */
	private int propX;
	
	/**The proposed row to move to. */
	private int propY;
	
	/**The current direction player is facing. */
	private DBGenerator.Direction curDir;
	
	/**The number of doors */
	private int myDoorCnt;
	
	/**The current door*/
	Map<String, String> curDoor;

	/**The collection of all doors in the maze. */
	private ArrayList<Door> myDoors; 
	
	/**The collection of all the rooms in the maze.*/
	private HashMap<Integer, Room> myRooms;
	        
	/**Constructor for initializing the maze object. */
	public Maze() {
		DBGenerator.deleteDB();
		DBGenerator.init();
		myMaze = new Room[ROWS][COLUMNS];
		curRow = 0;
		curCol = 0;
		propX = 0;
		propY = 0;
		curDir = DBGenerator.Direction.TOP;
		curDoor = new HashMap<>();
		myDoorCnt = 1;
		myDoors = new ArrayList<>();
		myRooms = new HashMap<Integer, Room>();
		generate();
		loadMaze();
	}
	
	/**
	 * Populates the maze with rooms and doors.
	 */
	public void loadMaze() {
		int idGen = 1;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j< COLUMNS; j++) {
				Room temp = new Room(idGen, i, j); 
				temp.populate(doorsInRoom(i, j));
				myRooms.put(idGen, temp);
				myMaze[i][j] = temp;
				idGen++;
			}
		}
	}
	
	/**
	 * Populates the global collection of doors with the current door being
	 * created.
	 */
	public void generate(){
		for (int i = 0; i <= MAX_Q; i++) {
			myDoors.add(new Door(i));
		}
	}
	
	/**
	 * Returns the current column, X coordinate.
	 * @return X coordinate, column.
	 */
	public int getX() {
		return curCol;
	}
	
	/**
	 * Retrieves the current row, Y coordinate.
	 * @return Y coordinate, row.
	 */
	public int getY() {
		return curRow;
	}
	
	/** 
	 * A getter which retrieves the 2d maze structure.
	 * @return Maze encompassing the rooms and doors.
	 */
	public Room[][] getMaze() {
		return myMaze;
	}
	
	/**
	 * Retrieves the doors in a given room
	 * @param theRow the Y coordinate of the room
	 * @param theCol the X coordinate of the room
	 * @return ArrayList of doors in specified room.
	 */
	public ArrayList<Door> doorsInRoom(int theRow, int theCol) {
		ArrayList<Door> temp = new ArrayList<Door>();
		temp.add(firstR(theRow, theCol));
		temp.add(firstC(theRow, theCol));
		temp.add(lastR(theRow, theCol));
		temp.add(lastC(theRow, theCol));
		return temp;
	}
	
	/**
	 * Checks first door in a room
	 * @param theRow The Y coordinate of the room
	 * @param theCol The X coordinate of the room
	 * @return Bottom door of room above
	 */
	public Door firstR(int theRow, int theCol) {
		Door temp;
		if (theRow == 0) {
			temp = myDoors.get(0);
		} else { 
			temp = myMaze[theRow - 1][theCol].getRoom().get(2);
		}
		return temp;
	}
	
	/**
	 * Checks second door in a room and returns right door of left room
	 * @param theRow The Y coordinate of the room
	 * @param theCol The X coordinate of the room.
	 * @return Right door of room to the left
	 */
	public Door firstC(int theRow, int theCol) {
		Door temp;
		if (theCol == 0) {
			temp = myDoors.get(0);
		} else { //Get right door of room to the left..
			temp = myMaze[theRow][theCol - 1].getRoom().get(3);
		}
		return temp;
	}
	
	/**
	 * Checks third door in a room and returns top room in room below
	 * @param theRow The Y coordinate of the room.
	 * @param theCol THe X coordinate of the room.
	 * @return Top room in room below
	 */
	public Door lastR(int theRow, int theCol) {
		Door temp;
		if (theRow == ROWS - 1) {
			temp = myDoors.get(0);
		} else { //Get next numbered door.
			temp = myDoors.get(myDoorCnt);
			myDoorCnt++;
		}
		return temp;
	}
	
	/**
	 * Checks fourth door in a room and returns left door of room to the right
	 * @param theRow
	 * @param theCol
	 * @return Left door of room to the right
	 */
	public Door lastC(int theRow, int theCol) {
		Door temp;
		if (theCol == COLUMNS - 1) {
			temp = myDoors.get(0);
		} else { 
			temp = myDoors.get(myDoorCnt);
			myDoorCnt++;
		}
		return temp;
	}
	
	/**
	 * Determines whether there are paths available for traversal in the maze.
	 * @return Whether the end may be reached.
	 */
	public boolean canContinue() {
		try        
		{
		    Thread.sleep(500);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		return DBGenerator.continuePlay(curRow, curCol);
	}
	
	/**
	 * Determines whether given answer is correct. 
	 * @param theResponse
	 * @return Whether user submitted answer is correct.
	 */
	public boolean isCorrect(Object theResponse) {
		boolean pass  = true;
		String uAnswer= (String) theResponse;
		if (DBGenerator.doorStatus.OPEN == DBGenerator.answerQuestion(Integer.parseInt(curDoor.get("question_id")), uAnswer.toLowerCase())) {
			pass = true;
			move(propY, propX, curDir);
		} else if (DBGenerator.doorStatus.FAILED == DBGenerator.answerQuestion(Integer.parseInt(curDoor.get("question_id")), uAnswer.toLowerCase())) {
			pass = false;
		}
		return pass;
	}
	
	/**
	 * Will move to the desired coordinates in the specified direction.
	 * @param theRow Desired Y coordinate, row.
	 * @param theCol Desired X coordinate, column.
	 * @param theDir The direction of desired room.
	 */
	public void move(int theRow, int theCol, DBGenerator.Direction theDir) {
		curRow = theRow;
		curCol = theCol;
	}
	
	/**
	 * Returns the Question and possible answers for a given question.
	 * @param current Current rooms ID.
	 * @param proposed Desired rooms ID.
	 * @return Map with the question mapped to the answers.
	 */
	public Map<String, String[]> displayCoords(int theCurrent, int theProposed) {
		Map<String, String[]> qandas = new HashMap<String, String[]>();
		curCol = myRooms.get(theCurrent).getX();
		curRow = myRooms.get(theCurrent).getY();
		Map<String, String> te = DBGenerator.getRoomDetail(myMaze[curRow][curCol].getRoomId());
		propX = myRooms.get(theProposed).getX();
		propY = myRooms.get(theProposed).getY();
		if (propY > curRow) {
			curDir = DBGenerator.Direction.BOTTOM;
		} else if (propY < curRow) {
			curDir = DBGenerator.Direction.TOP;
		} else {
			if (propX > curCol) {
				curDir = DBGenerator.Direction.RIGHT;
			} else if(propX < curCol) {
				curDir = DBGenerator.Direction.LEFT;	
			}
		}
		String temp = te.get(curDir.toString().toLowerCase() + "_door_id");
		curDoor = DBGenerator.getDoorDetail(Integer.parseInt(temp), curDir);
		if(curDoor.get("status").compareTo(DBGenerator.doorStatus.OPEN.toString()) == 0) {
			qandas.put("Help", new String[0]);
			move(propY, propX, curDir);
		} else if(curDoor.get("status").compareTo(DBGenerator.doorStatus.CLOSED.toString()) == 0) {
			if ((curDoor.get("answers").split(",").length == 4) || curDoor.get("answers").split(",").length == 2) {
				qandas.put(curDoor.get("question"), curDoor.get("answers").split(","));
			} else if(curDoor.get("answers").split(",").length == 1) {
				qandas.put(curDoor.get("question"), new String[0]);
			}
		} else if(curDoor.get("status").compareTo(DBGenerator.doorStatus.FAILED.toString()) == 0) {
			qandas.put("Hello", new String[0]);
			qandas.put("Heyo", new String[0]);
		}
		return qandas;
	}
}
