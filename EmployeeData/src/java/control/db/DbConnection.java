/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AZZA
 */
// this class to connect to database which is mysql here 
public class DbConnection {

    // getConnection() method to get the database connection 
    public Connection getConnection() throws Exception {
        Connection databaseConnection;
        Class.forName("com.mysql.cj.jdbc.Driver");

        databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee?useSSL=false", "root", "root");

        if (databaseConnection != null) {
            System.err.println("connected");
            return databaseConnection;

        }
        return null;
    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // just for test the connection
    public static void main(String[] args) {
        DbConnection dbConn = new DbConnection();
        try {
            dbConn.getConnection();
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
