package connectSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:./java-sqlite.db";

        // SQL statement for creating a new table
        String createRoomsSql = "CREATE TABLE IF NOT EXISTS rooms (" 
    			+ " id integer PRIMARY KEY,"
    			+ " name text NOT NULL," 
    			+ " capacity real)";

        String createDoorsSql = "CREATE TABLE IF NOT EXISTS doors (" 
        			+ " id integer PRIMARY KEY,"
        			+ " name text NOT NULL," 
        			+ " foreign key(room_id) references rooms(id))";
        
        String createQuestionsSql = "CREATE TABLE IF NOT EXISTS questions (" 
    			+ " id integer PRIMARY KEY,"
    			+ " name text NOT NULL," 
    			+ " foreign key(door_id) references doors(id))";

        
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(createRoomsSql);
            stmt.execute(createDoorsSql);
            stmt.execute(createQuestionsSql);
            System.out.println("Create table finished.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        createNewTable();
    }
}



