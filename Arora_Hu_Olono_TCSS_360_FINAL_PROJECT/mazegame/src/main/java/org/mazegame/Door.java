/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import java.io.Serializable;

/**
 * A door object users will pass through when attempting entry into a room.
 * @author olono_arora_zixuan
 */
public class Door implements Serializable {
	
	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 565824254999766646L;

	/**Whether the question may be asked.*/
	private boolean mayAsk;
	
	/**Whether the door is locked.*/
	private boolean myLock;
	
	/**The ID of the door. */
	private int myId;
	
	/**
	 * Constructor for Door object.
	 * @param theId The ID of the Door.
	 */
	public Door(int theId){
		setMyLock(true);
		myId = theId;
		mayAsk = true;
	}
	
	/**
	 * A getter retrieving whether the door is locked.
	 * @return Door lock status.
	 */
	public boolean isMyLock() {
		return myLock;
	}
	
	/**
	 * A setter which will set the lock.
	 * @param theLock The lock to be set.
	 */
	public void setMyLock(boolean theLock) {
		this.myLock = theLock;
	}
	
	/**
	 * A getter which determines whether the question may be asked at this 
	 * door.
	 * @return Whether the question may be asked.
	 */
	public boolean canAsk() {
		return mayAsk;
	}
	
	/**
	 * A setter that will disallow the ability to ask a question.
	 */
	public void denyAsk() {
		mayAsk = false;
	}
	
	/**
	 * A getter which retrieves the doors ID.
	 * @return The Id of the door.
	 */
	public int getId() {
		return myId;
	}
}
