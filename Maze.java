
import java.util.ArrayList;

/**
 * 
 */

/*It might be necessary to have a class where the maze is loaded
 * into a ArrayList of Array list of rooms. Then, that class would be called
 * here in order to load the maze and fill it up.  */

public class Maze {
	
	/*This holds the maze. */
	private ArrayList<ArrayList<Integer> > myMaze;  
            
	
	public Maze() {
		myMaze = new ArrayList<ArrayList<Integer>>();
		
		/* Going implement try and catch here in order to read in a file as
		 * a parameter in the constructors declaration. Then there will be
		 * error checking in order to determine whether the file is valid.
		 *  If the file is valid, there should be no problem and there may
		 *  be a message in the console mentioning that the file was loaded
		 *  successfully and thus the maze and game may go on. If there is
		 *  a problem, the appropriate message will be sent and there will
		 *  be an exception. However, this should be perfect every time
		 *  as the maze will be consistent every time. */
	}
	
	
	public void loadMaze() {
		
	}
	
	public void moveUp() {
		
	}
	
	public void moveRight() {
		
	}
	
	public void moveDown() {
		
	}
	
	public void moveLeft() {
		
	}
}
