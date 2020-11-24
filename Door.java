import java.util.HashMap;

/*This class will describe the functionality of a door object in the Maze
 * game.  */
public class Door {
	/* The door should house questions in the way of a map where the keys
	 * are the questions and the values are the possible answers and/or the
	 * allowed values. In addition, the Door object should make sure to have
	 * a boolean flag that will determine if the Door is locked. Finally the 
	 * Door object should have a method of viewing the question and the answer.
	 * This door object implementation is working under the assumption that the
	 * Door is always being created with the Door being unlocked. If the query
	 * is incorrectly answered, then the door will lock. If the query is 
	 * answered correctly, then the Door should remain unlocked. Need to also
	 * understand if the specific question should have its correct answer 
	 * indicated as well here in the class itself.
	 * 
	 *  */
	
	//The lock flag, true = locked, false = not locked.
	private boolean myLock;
	
	//Map containing the question and the answers
	private HashMap<String, String[]> myQandA;
	public Door(){
		setMyLock(false);
		myQandA = new HashMap<String, String[]>();
	}
	
	//Will return whether the door is locked
	public boolean isMyLock() {
		return myLock;
	}
	
	//Will set the lock on the door.
	public void setMyLock(boolean theLock) {
		this.myLock = theLock;
	}
	
	//Will set the question and the accepted answers 
	public void setQandA(String theQ, String[] theA) {
		myQandA.put(theQ, theA);
	}
	
	//Will return the Question as a String.
	public String getQ() {
		return (String) myQandA.keySet().toArray()[0];
	}
	
	//Will retrieve the array of answers.
	public String[] getA() {
		return myQandA.get(myQandA.keySet().toArray()[0]);
	}

	
}

