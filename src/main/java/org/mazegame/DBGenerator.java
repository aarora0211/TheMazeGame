package org.mazegame;

import java.sql.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.apache.commons.lang3.StringUtils;

public class DBGenerator {
	public static void init() {
		// SQLite connection string
		String url = "jdbc:sqlite:./java-sqlite.db";

		// SQL statement for creating a new table
		String createRoomsSql = "CREATE TABLE IF NOT EXISTS rooms (" + " id integer PRIMARY KEY,"
				+ " name text NOT NULL," + " capacity real)";

		String createDoorsSql = "CREATE TABLE IF NOT EXISTS doors (" + " id integer PRIMARY KEY,"
				+ " name text NOT NULL," + " room_id integer," + " foreign key(room_id) references rooms(id)" + ")";

		String createQuestionsSql = "CREATE TABLE IF NOT EXISTS questions (" + " id integer PRIMARY KEY,"
				+ " question text NOT NULL," + " answers text NOT NULL," + " right_answer text NOT NULL,"
				+ " door_id integer," + " foreign key(door_id) references doors(id)" + ")";

		String insertQuestions = "";
		String insertDoor = "";
		String insertRoom = "";

		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.execute(createRoomsSql);
			stmt.execute(createDoorsSql);
			stmt.execute(createQuestionsSql);

			// load all questions to the database
			try {
				SAXReader reader = new SAXReader();
				Document read = reader.read("./questions.xml");
				Element root = read.getRootElement();
				Iterator dataraw = root.elementIterator();

				while (dataraw.hasNext()) {
					Element questions = (Element) dataraw.next();

					String questionText = "";
					String answersText = "";
					String rightAnswerText = "";

					// get answers of question
					Iterator answers = questions.elementIterator();
					while (answers.hasNext()) {
						Element answer = (Element) answers.next();

						if (answer.getName() == "question") {
							questionText = answer.getStringValue();
						}

						if (answer.getName() == "right_answer") {
							rightAnswerText = answer.getStringValue();
						}

						if (answer.getName() == "answer") {
							if (StringUtils.isBlank(answersText)) {
								answersText += answer.getStringValue();
							} else {
								answersText += "," + answer.getStringValue();
							}
						}
					}

					System.out.println(questionText);

					stmt.execute("INSERT INTO questions(question, answers, right_answer) VALUES('" + questionText
							+ "', '" + answersText + "', '" + rightAnswerText + "');");
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			// generate doors and assign question to door, in a 2*2 room placement we need
			// 2*2*4 = 16 doors
			int doorSize = 16;
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions ORDER BY RANDOM() limit " + doorSize + "");
			List list = new ArrayList();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();

			// loop through the result set
			while (rs.next()) {
				Map rowData = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}

			for (int i = 0; i < list.size(); i++) {
				stmt.execute("INSERT INTO doors(name) VALUES('test door');");
				ResultSet resultSet = stmt.getGeneratedKeys();
				if (resultSet.next()) {
					System.out.println(resultSet.getInt(1));
					stmt.executeUpdate("UPDATE questions SET door_id = " + resultSet.getInt(1) + " where id = "
							+ ((Map) list.get(i)).get("id") + "");
					stmt.executeUpdate("UPDATE doors SET room_id = " + resultSet.getInt(1) + " where id = "
							+ resultSet.getInt(1) + "");
				}
			}

			// generate rooms and assign door to room, in a 2*2 room placement we need 2*2 =
			// 4 rooms
			int roomSize = 4;
			for (int x = 1; x <= roomSize; x++) {
				stmt.execute("INSERT INTO rooms(name, capacity) VALUES('test room, 4');");
			}

			System.out.println("Data initialization completed");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static List getQuestionsByDoor(String door_id) {
		String url = "jdbc:sqlite:./java-sqlite.db";

		List list = new ArrayList();

		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	public static List getDoorsByRoom(String room_id) {
		String url = "jdbc:sqlite:./java-sqlite.db";

		List list = new ArrayList();

		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	}
}
