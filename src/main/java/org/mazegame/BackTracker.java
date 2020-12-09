package org.mazegame;

import java.io.*;
import java.util.*;
import javafx.util.Pair;

public class BackTracker {
	private Set<Integer> visited = new HashSet<>();
	private Map<Pair<Integer, Integer>, Boolean> doors = new HashMap<>();
	private final int width;
	private final int height;
	private int paths[][] = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

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

	private int toIndex(int x, int y) {
		return y * this.width + x + 1;
	}

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

	public static void main(String[] args) {

		System.out.println("Hello Java");
		int doors[][] = { { 1, 2, 1 }, { 2, 3, 1 }, { 3, 4, 1 }, { 1, 5, 0 }, { 2, 6, 0 }, { 3, 7, 0 }, { 4, 8, 1 },
				{ 5, 6, 0 }, { 6, 7, 0 }, { 7, 8, 0 }, { 5, 9, 1 }, { 6, 10, 1 }, { 7, 11, 1 }, { 8, 12, 1 },
				{ 9, 10, 0 }, { 10, 11, 0 }, { 11, 12, 1 }, { 9, 13, 1 }, { 10, 14, 1 }, { 11, 15, 1 }, { 12, 16, 0 },
				{ 13, 14, 1 }, { 14, 15, 1 }, { 15, 16, 1 } };
		BackTracker tracker = new BackTracker(4, 4, doors);
		System.out.print(tracker.canReachEnd(0, 0));
		for (Integer s : tracker.visited) {
			System.out.println(s);
		}
	}
}