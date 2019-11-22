package com.trainingproject.model;

import com.trainingproject.helper.DbConnect;

import javax.servlet.ServletException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Token {
    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 2048 bit keys should be secure until 2030
    public static final int SECURE_TOKEN_LENGTH = 256;

    private static final SecureRandom random = new SecureRandom();

    private static final char[] symbols = CHARACTERS.toCharArray();

    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    /**
     * Generate the next secure random token in the series.
     */

    public static String nextToken() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public Token(String username, String token)
    {
        this.token = token;
        this.username = username;
    }

    public static void deleteToken(String token)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM tokens WHERE token_value =?");
            preparedStatement.setString(1,token);
            preparedStatement.executeUpdate();
        }catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }
    }

    public void addTokenToDataBase()
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;

        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO tokens(username, token_value) VALUES (?,?)");

            preparedStatement.setString(1,this.username);
            preparedStatement.setString(2,this.token);

            preparedStatement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println("Check failed (SQL INSERT STATEMENT FAILED)");
        }

    }

    public static boolean tokenExists(String token)
    {

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        int token_count=0;
        try {
            preparedStatement = connection.prepareStatement("SELECT count(token_value) as token_count FROM tokens WHERE  token_value =?");
            preparedStatement.setString(1, token);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                token_count =Integer.parseInt( resultSet.getString("token_count"));
            }
        }catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }

        if (token_count ==0)
            return false;
        else
            return true;
    }


    public static boolean userExists(String username)
    {

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        int username_count=0;
        try {
            preparedStatement = connection.prepareStatement("SELECT count(username) as username_count FROM tokens WHERE  username =?");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                username_count =Integer.parseInt( resultSet.getString("username_count"));
            }
        }catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }

        if (username_count ==0)
            return false;
        else
            return true;
    }


    public static String getUsernameOfToken(String token)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        String username = "";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT username FROM tokens WHERE  token_value =?");
            preparedStatement.setString(1, token);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                username = resultSet.getString("username");
            }

            return username;
        }
        catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }
        return username;
    }

}