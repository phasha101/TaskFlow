package com.taskflow;

import java.sql.*;

public class TaskFlowInsertQuery {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/tasks";
        String user = "postgres";
        String password = "0000";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {

            System.out.println("connected to database");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
