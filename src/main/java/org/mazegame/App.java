package org.mazegame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
//    	DBGenerator.loadAllDoors();
    	
//    	DBGenerator.deleteDB();
    	
    	DBGenerator.getRoomDetail(2);
    	
    	DBGenerator.getDoorDetail(4, DBGenerator.Direction.RIGHT);

//    	DBGenerator.answerQuestion(37, "1");

//    	DBGenerator.getDoorDetail(1, DBGenerator.Direction.BOTTOM);
    	
    };
}
