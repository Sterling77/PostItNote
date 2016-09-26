/**
 * Created by Letricia on 8/3/16.
 */
import com.sun.javafx.fxml.expression.Expression;
//import com.sun.javafx.fxml.expression.Expression.Parser;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import java.sql.*;
        import java.util.Scanner;
        import java.util.concurrent.SynchronousQueue;

//import static com.sun.org.apache.xerces.internal.impl.xpath.regex.Token.categories;

public class Categories {


    public static void main(String[] args) {

//        SELECT column_name(s)
//                FROM table1
//        JOIN table2
//        ON table1.column_name=table2.column_name;


        Categories categories1 = new Categories();
        categories1.checkTable();
        try {
            categories1.readAllRecords(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ask questions
        Scanner scanner = new Scanner(System.in);
        System.out.println("What category do you want to create? Name it:");
        String noteCode = scanner.nextLine();
        System.out.println("What is the color/flavor of your category?");
        String color = scanner.nextLine();
        System.out.println("Please describe your category:");
        String noteDesc = scanner.nextLine();
        //Date d = new Date(System.currentTimeMillis());
        categories1.insertRecord(noteCode,color,noteDesc);  //d.toString(), add this back if date is needed!!!!!
    }

    private void createTable() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:noteWriters","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE NOTE_TRACKER ( id INTEGER IDENTITY, notesTitle VARCHAR(256), notesContent VARCHAR(256),categoryDescription VARCHAR(256))");

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
        ResultSet rs = stmt.executeQuery("SELECT id,notesTitle,notesContent,categoryDescription FROM NOTE_TRACKER");
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
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO NOTE_TRACKER (notesTitle, notesContent, categoryDescription) VALUES (?,?,?);");
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
//    JoinRowSet jrs = new JoinRowSetImpl();
////        categories = new
////
////    CachedRowSetImpl() {
//
//////    }
//        Parser.Token.categories.setCommand("SELECT * FROM TRACKER");
//        categories.setUsername(settings.userName);
//        categories.setPassword(settings.password);
//        categories.setUrl(settings.urlString);
//        categories.execute();
//
//        postItNote = new CachedRowSetImpl();
//        postItNote.setCommand("SELECT * FROM TRACKERS");
//        postItNote.setUsername(settings.userName);
//        postItNote.setPassword(settings.password);
//        postItNote.setUrl(settings.urlString);
//        postItNote.execute();
//
//    jrs.addRowSet(categories, categoryDescription);
//    jrs.addRowSet(postItNote, categoryDescription);
//
//    System.out.println("All categories in" + supplierOfCategories + ":" );
//    while (jrs.getString("NOTE_TRACKERS").equals(supplierOfCategories) {
//        String categoriesName = jrs.getString(1);
//        System.out.println("  " + categoriesName);
    }

