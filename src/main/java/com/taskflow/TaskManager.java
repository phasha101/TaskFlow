package com.taskflow;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    public void createTask(String taskTitle, Task.Category category, long daysToComplete) {
        Task task = new Task(taskTitle, category, daysToComplete);
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

    public void updateTaskCategory(UUID id, Task.Category category) {
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setCategory(category);
                System.out.println("Category change successful");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void updateTaskDeadline(UUID id, LocalDate date) {
        for (Task x : tasks) {
            if (x.getID().equals(id)) {
                x.setDate(date);
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

    public void saveTasks(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        try {
            objectMapper.writeValue(new File(filename), tasks);
            System.out.println("Tasks saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasks(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        try {
            Task[] loaded = objectMapper.readValue(new File(filename), Task[].class);
            tasks.clear();
            for (Task t : loaded) {
                tasks.add(t);
            }
            System.out.println("Tasks loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

}



// class test{
//
//     public static void main(String[] args) {
//         TaskManager manager = new TaskManager();
//         Task task = new Task("dishes", Task.Category.CHORE, 3);
//         manager.createTask("do the dishes", Task.Category.CHORE, 1);
//         manager.listTasks();
////         manager.saveTasks();
//     }
// }