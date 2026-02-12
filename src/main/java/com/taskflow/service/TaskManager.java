package com.taskflow.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.taskflow.model.*;


public class TaskManager {

    private List<Task> tasks = new ArrayList<>();
    private Priority priority = Priority.LOW;
    String url = "jdbc:postgresql://localhost:5432/tasks";
    String user = "postgres";
    String password = "0000";

    TaskRepository taskRepository = new TaskRepository();

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
        for (Task task: tasks){
            taskRepository.save(task);
        }
    }



    public void loadTasksFromDB() {
        List<Task> tasksFromDb = taskRepository.findAll();
        try{
        this.tasks.clear();
        this.tasks.addAll(tasksFromDb);
        }catch (Exception e){
            System.out.println("failed to load from db, exception thrown: " + e);
        }
    }


}
