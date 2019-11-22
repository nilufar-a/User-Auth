package com.trainingproject.helper;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    public static Connection conn() throws ServletException
    {
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user="root";
            String password="root";
            String url="jdbc:mysql://127.0.0.1:3306/projectlab?user=root";

            connection=(Connection) DriverManager.getConnection(url,user,password);
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Connection failed (ClassNotFoundException)");
        }
        catch (SQLException ex) {
            System.out.println("Connection failed (SQLException)");
        }

        System.out.println(connection);

        return connection;
    }

    public static Connection getConnection() throws ServletException {
        String url = "jdbc:mysql://google/instance757?cloudSqlInstance=trainingprojectlab2019:europe-west1:project-lab&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=&useSSL=false";
        System.out.println(url);
        try {

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            throw new ServletException("SQL Error: " + e.getMessage(), e);
        }
    }

    public static Connection connection() throws ServletException
    {
        try {

            Class.forName("com.mysql.jdbc.GoogleDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }
        try {

            String DBUrl = String.format("jdbc:google:mysql://%s/%s",
                    "trainingprojectlab2019:europe-west1:project-lab", "userauth");
            Connection  DbConn = DriverManager.getConnection(DBUrl,"root","root");
            return DbConn;
        } catch(SQLException  ex) {
            throw new ServletException("SQL Error: " + ex.getMessage(), ex);
        }

    }
}