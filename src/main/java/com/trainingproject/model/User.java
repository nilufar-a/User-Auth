package com.trainingproject.model;


import com.trainingproject.helper.DbConnect;

import javax.servlet.ServletException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class User {
    private String username;
    private String email;
    char[] password;
    byte[] salt;

    private SecureRandom random ;

    public char[] getPassword()
    {
        return password;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public User(String username,String email, char[] password, byte[] salt)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        random = new SecureRandom();

    }

    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
    }

    public static void addNewUserToDataBase(User user) {
    	
        Connection connection = null;
        try {

            connection = DbConnect.conn();

        }
        catch(ServletException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        PreparedStatement preparedStatement=null;

        try {

            preparedStatement=connection.prepareStatement("INSERT INTO users(username, email, password, salt)VALUES(?,?,?,?)");

            preparedStatement.setString(1,user.username);
            preparedStatement.setString(2, user.email);
            String temPass = new String( Password.hash(user.password, user.salt));
            preparedStatement.setString(3,Base64.getEncoder().encodeToString(temPass.getBytes()));
            preparedStatement.setString(4, Base64.getEncoder().encodeToString(user.salt));

            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }


    }

    public static void deleteAccount(String username)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement=null;

        try {
        	
            preparedStatement = connection.prepareStatement("DELETE FROM tokens WHERE username = ?");
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }

    }
}
