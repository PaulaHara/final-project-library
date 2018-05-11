package ca.ciccc.java.db;

import java.sql.*;
import org.postgresql.Driver;

/**
 * @author paula on 20/04/18.
 */
public class ConnectionFactory {
    // JDBC driver name and database URL
    static final String URL = "jdbc:postgresql://localhost/";
    static final String DB_URL = "jdbc:postgresql://localhost/library";

    //  Database credentials
    static final String USER = "admin";
    static final String PASS = "admin";

    static Connection conn = null;
    static Statement stmt = null;

    /**
     * Create database if it doesn't exist
     * @throws SQLException
     */
    private static void createDatabase() throws SQLException {
        //Open a connection
        conn = DriverManager.getConnection(URL, USER, PASS);
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("select datname from pg_database where datname like 'library';");

        // If there is no next, than the database doesn't exist and must be created
        if(!rs.next()) {
            stmt.executeUpdate("CREATE DATABASE library");
        }
    }

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    public static void createTables() {
        try{
            createDatabase();

            //Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            String sql;

            ResultSet tables = dbm.getTables(null, null, "author", null);
            if(!tables.next() || !tables.getString("TABLE_NAME").equalsIgnoreCase("author")) {
                // Creating Author table
                sql = new String("CREATE TABLE AUTHOR " +
                        "(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), " +
                        "date_of_birth DATE, pseudonym VARCHAR(255), specialty VARCHAR(255), PRIMARY KEY (id))");
                stmt.executeUpdate(sql);
            }

            tables = dbm.getTables(null, null, "book", null);
            if(!tables.next() || !tables.getString("TABLE_NAME").equalsIgnoreCase("book")) {
                // Creating Book table
                sql = new String("CREATE TABLE BOOK " +
                        "(id SERIAL, title VARCHAR(255), author_id INTEGER, year_published INTEGER, " +
                        "edition INTEGER, isbn VARCHAR(100), genre VARCHAR(100), number_copies INTEGER, " +
                        "copies_available INTEGER, PRIMARY KEY (id), FOREIGN KEY (author_id) REFERENCES AUTHOR(id))");
                stmt.executeUpdate(sql);
            }

            tables = dbm.getTables(null, null, "customer", null);
            if(!tables.next() || !tables.getString("TABLE_NAME").equalsIgnoreCase("customer")) {
                // Creating Customer table
                sql = new String("CREATE TABLE CUSTOMER " +
                        "(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), " +
                        "date_of_birth DATE, customer_id VARCHAR(100), is_active BOOLEAN, PRIMARY KEY (id))");
                stmt.executeUpdate(sql);
            }

            tables = dbm.getTables(null, null, "borrowing", null);
            if(!tables.next() || !tables.getString("TABLE_NAME").equalsIgnoreCase("borrowing")) {
                // Creating Borrowing table
                sql = new String("CREATE TABLE BORROWING " +
                        "(id SERIAL, customer_id INTEGER, is_finished BOOLEAN, borrowed_date DATE, " +
                        "return_date DATE, PRIMARY KEY (id), FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id))");
                stmt.executeUpdate(sql);
            }

            tables = dbm.getTables(null, null, "book_borrowing", null);
            if(!tables.next() || !tables.getString("TABLE_NAME").equalsIgnoreCase("book_borrowing")) {
                // Pivot table for BOOK and CUSTOMER tables
                sql = new String("CREATE TABLE BOOK_BORROWING " +
                        "(book_id INTEGER NOT NULL, borrowing_id INTEGER NOT NULL, " +
                        "PRIMARY KEY (book_id, borrowing_id), " +
                        "CONSTRAINT BOOK_BORROWING_BOOK_ID FOREIGN KEY (book_id) REFERENCES BOOK(id) ON DELETE CASCADE, " +
                        "CONSTRAINT BOOK_BORROWING_BORROWING_ID FOREIGN KEY (borrowing_id) REFERENCES BORROWING(id) ON DELETE CASCADE)");
                stmt.executeUpdate(sql);
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public static void disconnectFromDB() {
        try{
            if(stmt!=null)
                conn.close();
        }catch(SQLException se){
        }
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}