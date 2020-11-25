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
        String sql = "CREATE TABLE IF NOT EXISTS doors (\n" + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n" + " capacity real\n" + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
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



