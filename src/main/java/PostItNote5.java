import java.sql.*;
import java.util.Scanner;
import com.sun.rowset.JoinRowSetImpl;

/**
 * Created by Letricia on 8/3/16.
 */

public class PostItNote5 {

        public static void main(String[] args) {
            PostItNote5 postItNote5 = new PostItNote5();
            postItNote5.checkTable();
            try {
                postItNote5.readAllRecords(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ask questions
            Scanner scanner = new Scanner(System.in);
            System.out.println("What is the title of your Post-It-Note?");
            String noteCode = scanner.nextLine();
            System.out.println("What is the content of your Post-It-Note?");
            String color = scanner.nextLine();
            System.out.println("Please assign your Post-It-Note a description?");
            String noteDesc = scanner.nextLine();
            //Date d = new Date(System.currentTimeMillis());
            postItNote5.insertRecord(noteCode,color,noteDesc);  //d.toString(), add this back if date is needed!!!!!
        }

        private void createTable() {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                Connection conn = DriverManager.getConnection("jdbc:hsqldb:noteWriters","sa","");
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE TABLE NOTE_TRACKERS ( id INTEGER IDENTITY, notesTitle VARCHAR(256), notesContent VARCHAR(256),categoryDescription VARCHAR(256))");

                stmt.close();
                conn.close();
            } catch(Exception e) {
                System.out.println("Table Already Exists");
            }
        }

        private void checkTable() {
            try {
                readAllRecords(false);
            } catch (Exception e) {
                createTable();
            }
        }

        private void readAllRecords(boolean printMe) throws Exception {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:noteWriters","sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id,notesTitle,notesContent,categoryDescription FROM NOTE_TRACKERS");
            while(rs.next()) {
                String rowResults = rs.getInt("id")+" -- "+rs.getString("notesTitle")+" -- "+rs.getString("notesContent")
                        +" -- "+rs.getString("categoryDescription");
                if(printMe) {
                    System.out.println(rowResults);
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        }

        private void insertRecord(String notesTitle, String notesContent, String categoryDescription) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                Connection conn = DriverManager.getConnection("jdbc:hsqldb:noteWriters","sa","");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO NOTE_TRACKERS (notesTitle, notesContent, categoryDescription) VALUES (?,?,?);");
                stmt.setString(1,notesTitle);
                stmt.setString(2, notesContent);
                stmt.setString(3,categoryDescription);
                stmt.executeUpdate();

                stmt.close();
                conn.close();
                System.out.println("NOTE: Finished Update");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }


    }

