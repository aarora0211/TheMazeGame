package org.mazegame;


import static org.junit.Assert.*;

import org.junit.*;


public class BackTrackerTest {	
	@Test
	public void testCanReachEnd() {
		int doors[][] = { { 1, 2, 1 }, { 2, 3, 1 }, { 3, 4, 1 }, { 1, 5, 0 }, { 2, 6, 0 }, { 3, 7, 0 }, { 4, 8, 1 },
				{ 5, 6, 0 }, { 6, 7, 0 }, { 7, 8, 0 }, { 5, 9, 1 }, { 6, 10, 1 }, { 7, 11, 1 }, { 8, 12, 1 },
				{ 9, 10, 0 }, { 10, 11, 0 }, { 11, 12, 1 }, { 9, 13, 1 }, { 10, 14, 1 }, { 11, 15, 1 }, { 12, 16, 0 },
				{ 13, 14, 1 }, { 14, 15, 1 }, { 15, 16, 1 } };
		
		BackTracker tracker = new BackTracker(4, 4, doors);
		
		Boolean result = tracker.canReachEnd(0, 0);

		assertTrue(result);
	}
	
	@Test
	public void testCaNotReachEnd() {
		int doors[][] = { { 1, 2, 0 }, { 2, 3, 1 }, { 3, 4, 1 }, { 1, 5, 0 }, { 2, 6, 0 }, { 3, 7, 0 }, { 4, 8, 1 },
				{ 5, 6, 0 }, { 6, 7, 0 }, { 7, 8, 0 }, { 5, 9, 1 }, { 6, 10, 1 }, { 7, 11, 1 }, { 8, 12, 1 },
				{ 9, 10, 0 }, { 10, 11, 0 }, { 11, 12, 1 }, { 9, 13, 1 }, { 10, 14, 1 }, { 11, 15, 1 }, { 12, 16, 0 },
				{ 13, 14, 1 }, { 14, 15, 1 }, { 15, 16, 1 } };
		
		BackTracker tracker = new BackTracker(4, 4, doors);
		
		Boolean result = tracker.canReachEnd(0, 0);
		
		assertFalse(result);
	}
}
