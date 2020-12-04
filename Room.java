import java.util.ArrayList;

public class Room {
	
	/*This room class will house various doors ranging from 1 to 4 doors with
	 * a variable amount being included depending on where the door is located
	 * in a maze. */
	
	//Houses a my room list that will hold a collection of doors. (integer is
	//only a placeholder for now).
	private ArrayList<Door> myRoom;
	
	private String[] qsandas;
	
	//In the constructor we want to initialize my room and in addition we also
	//want to take in the number....
	public Room() {
		myRoom = new ArrayList<Door>();
		qsandas = new QuestionAnswer(4).getQuestionGrid();
	}
	
	//Populates myRoom with 4 doors with 4 different questions.
	public void populate() {
		for (int i = 0; i< qsandas.length; i++) {
			myRoom.add( new Door(qsandas[i]));
		}
	}
	
	
}
