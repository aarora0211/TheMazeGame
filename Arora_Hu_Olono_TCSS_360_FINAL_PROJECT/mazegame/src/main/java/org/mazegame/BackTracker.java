/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import java.io.*;
import java.util.*;
import javafx.util.Pair;

/**
 * Determines movement restrictions and possibilities in a maze.
 * @author olono_arora_zixuan
 */
public class BackTracker implements Serializable {
	
	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1804245421304406610L;

	/** The visited doors. */
	private Set<Integer> visited = new HashSet<>();
	
	/**The doors. */
	private Map<Pair<Integer, Integer>, Boolean> doors = new HashMap<>();
	
	/**The width of the maze. */
	private final int width;
	
	/**The height of the maze. */
	private final int height;
	
	/**The possible paths within a maze. */
	private int paths[][] = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

	/**
	 * Constructor of BackTracker
	 * @param width The width of the maze.
	 * @param height The height of the maze.
	 * @param doors The doors found in a maze.
	 */
	BackTracker(int width, int height, int[][] doors) {
		this.width = width;
		this.height = height;
		for (int[] door : doors) {
			int room1 = door[0];
			int room2 = door[1];
			if (room1 > room2) {
				int temp = room1;
				room1 = room2;
				room2 = temp;
			}
			boolean ok = door[2] != 0;
			this.doors.put(new Pair<>(room1, room2), ok);

		}

	}
	
	/**
	 * Distance to a specific coordinate.
	 * @param x The X coordinate, column.
	 * @param y The Y coordinate, row.
	 * @return The distance to a room.
	 */
	private int toIndex(int x, int y) {
		return y * this.width + x + 1;
	}

	/**
	 * Determines whether if maze traversal is possible to a room from a given
	 * location in the maze.
	 * @param x The current X coordinate, column.
	 * @param y The current Y coordinate, row.
	 * @param destx The X coordinate of the desired room, column.
	 * @param desty The Y coordinate of the desired room, row.
	 * @return Whether traversal to the desired room is possible.
	 */
	private boolean canGo(int x, int y, int destx, int desty) {
		if (destx < 0 || destx >= width) {
			return false;
		}
		if (desty < 0 || desty >= height) {
			return false;
		}
		int room1 = toIndex(x, y);
		int room2 = toIndex(destx, desty);
		if (room1 > room2) {
			int temp = room1;
			room1 = room2;
			room2 = temp;
		}

		Pair<Integer, Integer> path = new Pair<>(room1, room2);
		if (!this.doors.containsKey(path)) {
			return false;
		}

		return this.doors.get(path);

	}

	/**
	 * Determines whether reaching the end is possible in the maze.
	 * @param x The current X coordinate of the room currently occupied.
	 * @param y The current Y coordinate of the room currently occupied.
	 * @return Whether the end may be reached from this room.
	 */
	public boolean canReachEnd(int x, int y) {
		// backtrack done here
		if (x == this.width - 1 && y == this.height - 1) {
			return true;
		}

		for (int[] path : this.paths) {
			int newx = x + path[0], newy = y + path[1];
			int newLoc = toIndex(newx, newy);

			if (this.visited.contains(newLoc)) {
				continue;
			}
			if (this.canGo(x, y, newx, newy)) {
				this.visited.add(newLoc);
				if (canReachEnd(newx, newy))
					return true;
				this.visited.remove(newLoc);
			}
		}
		return false;
	}
}