package com.taskflow;

import java.sql.*;

public class TaskFlowInsertQuery {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/tasks";
        String user = "postgres";
        String password = "0000";
        String sql = "insert INTO task(id, title, priority, status, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("connected to database");
            stmt.setInt(1, 4);
            stmt.setString(2, "dishes");
            stmt.setInt(3, 2);
            stmt.setString(4,"PENDING");
            stmt.setString(5, "CHORES");
            stmt.execute();
            System.out.println("data entered");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
