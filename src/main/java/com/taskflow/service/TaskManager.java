package com.taskflow.service;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import com.taskflow.model.Status;
import com.taskflow.model.Task;

import java.io.File;
import java.io.IOException;

public class TaskManager {

    private List<Task> tasks = new ArrayList<>();
    private Priority priority = Priority.LOW;
    String url = "jdbc:postgresql://localhost:5432/tasks";
    String user = "postgres";
    String password = "0000";

    public void createTask(String taskTitle, Category category, long daysToComplete, Priority priority) {
        Task task = new Task(taskTitle, category, daysToComplete, priority);
        tasks.add(task);
        System.out.println("Task added! on: " + LocalDate.now());
    }

    public void createTask(String taskTitle, Category category, long daysToComplete) {

        Task task = new Task(taskTitle, category, daysToComplete, this.priority);
        tasks.add(task);
        System.out.println("Task added! on: " + LocalDate.now());
    }

    public void updateTaskTitle(UUID id, String title) {
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setTitle(title);
                System.out.println("Title change successful");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void updateTaskCategory(UUID id, Category category) {
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setCategory(category);
                System.out.println("Category change successful");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void updateTaskPriority(UUID id, Priority newPriority){
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setPriority(newPriority);
                System.out.println("Priority change successful");
                return;
            }
        }
        System.out.println("Task not found.");
    }


    public void updateTaskDeadline(UUID id, LocalDate date) {
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setDeadline(date);
                System.out.println("Deadline change successful");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void deleteTask(UUID id) {
        boolean removed = tasks.removeIf(x -> x.getID().equals(id));
        System.out.println(removed ? "Task of id: " + id + " removed" 
                                   : "Failed to remove task of id: " + id);
    }

    public List<Task> getTasks() { return tasks; }

    public void saveTasksToDB(List<Task> tasks) {
        String sql = "INSERT INTO task (id, title, deadline, status, category, priority) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Task t : tasks) {
                pstmt.setObject(1, t.getID()); // UUID
                pstmt.setString(2, t.getTitle());
                pstmt.setDate(3, java.sql.Date.valueOf(t.getDeadline())); // LocalDate → SQL Date
                pstmt.setString(4, t.getStatus().toString());
                pstmt.setString(5, t.getCategory().toString());
                pstmt.setString(6, t.getPriority().toString());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Tasks saved to database");

        } catch (SQLException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }



    public void loadTasksFromDB(List<Task> tasks) {
        String sql = "SELECT id, title, deadline, status, category, priority FROM task;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            tasks.clear(); // reset current list

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String title = rs.getString("title");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                Category category = Category.valueOf(rs.getString("category"));
                Priority priority = Priority.valueOf(rs.getString("priority"));

                Task task = new Task();
                task.setId(id);
                task.setTitle(title);
                task.setDeadline(deadline);
                task.setStatus(status);
                task.setCategory(category);
                task.setPriority(priority);

                tasks.add(task);
            }

            System.out.println("Tasks loaded from database");

        } catch (SQLException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }


}
