/*The maze class will contain the structure of the maze itself and all rooms
 * and doors will be enclosed within. */
public class Maze {
	
	/*This holds the maze. */
	private Room[][] myMaze; 
	        
	/*Constructor which initializes the maze and awards fixed dimensions */
	public Maze() {
		myMaze = new Room[5][6];
	}
	
	
	public void loadMaze() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j< 6; j++) {
				Room temp = new Room();
				temp.populate();
				myMaze[i][j] = temp;
			}
		}
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
