/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The room object which will store doors
 * @author olono_arora_zixuan
 */
public class Room implements Serializable {
	
	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -7879707003136055810L;

	/**The room object holding enclosed doors.*/
	private ArrayList<Door> myRoom;
	
	/**The  room's ID.*/
	private int roomId;
	
	/**The rooms X coordinate, column.*/
	private int myX;
	
	/**The rooms Y coordinate, row. */
	private int myY;
	
	/**
	 * Constructor for room object.
	 * @param theId The ID assigned to the room.
	 * @param theY The row room is found in.
	 * @param theX The column room is found in.
	 */
	public Room(int theId, int theY, int theX) {
		myRoom = new ArrayList<Door>();
		roomId = theId;
		myX = theX;
		myY = theY;
	}
	
	
	/**
	 * Fills the room with some doors.
	 * @param theDoors The doors to be allocated in the room.
	 */
	public void populate(ArrayList<Door> theDoors) {
		myRoom = theDoors;
	}
	
	/**
	 * A getter that retrieves the room's ID.
	 * @return The room ID.
	 */
	public int getRoomId() {
		return roomId;
	}
	
	/**
	 * A getter that retrieves the X coordinate, column.
	 * @return The rooms column.
	 */
	public int getX() {
		return myX;
	}
	
	/**
	 * A getter that retrieves the Y coordinate, row.
	 * @return The rooms row.
	 */
	public int getY() {
		return myY;
	}
	
	/**
	 * A getter that retrieves the room object with doors enclosed.
	 * @return The room containing the doors within.
	 */
	public ArrayList<Door> getRoom() {
		return myRoom;
	}
}
