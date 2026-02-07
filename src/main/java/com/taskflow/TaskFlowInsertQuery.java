package com.taskflow;

import java.sql.*;

public class TaskFlowInsertQuery {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/tasks";
        String user = "postgres";
        String password = "0000";

        Connection connection = DriverManager.getConnection(url, user, password);
        try  {
            listTasks(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listTasks(Connection con) throws SQLException {
        String sql = "select * from task";
        Statement statement = con.createStatement();
        ResultSet set = statement.executeQuery(sql);
        while(set.next()){
            System.out.print("ID: "+set.getInt(1)+" |");
            System.out.print(" Title: "+set.getString(2)+" |");
            System.out.print(" Priority Level: "+set.getInt(3)+" |");
            System.out.print(" Status: "+set.getString(4)+" |");
            System.out.println(" Category: "+set.getString(5)+" |");
        }
    }
}
