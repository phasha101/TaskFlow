package com.taskflow.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.taskflow.model.*;


public class TaskManager {

    private List<Task> tasks = new ArrayList<>();
    private Priority priority = Priority.LOW;

    TaskRepository taskRepository = new TaskRepository();

    public void createTask(String taskTitle, Category category, long daysToComplete, Priority priority) {
        Task task = new Task(taskTitle, category, daysToComplete, priority);
        taskRepository.save(task);
        System.out.println("Task added! on: " + LocalDate.now());
    }

    public void createTask(String taskTitle, Category category, long daysToComplete) {

        Task task = new Task(taskTitle, category, daysToComplete, this.priority);
        taskRepository.save(task);
        System.out.println("Task added! on: " + LocalDate.now());
    }

    public void updateTaskTitle(UUID id, String title) {
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setTitle(title);
            taskRepository.save(task);
            System.out.println("Title updated successfully");
        }
        else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }

    public void updateTaskCategory(UUID id, Category category) {
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setCategory(category);
            taskRepository.save(task);
            System.out.println("category updated successfully");
        }
        else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }

    public void updateTaskPriority(UUID id, Priority newPriority){
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setPriority(priority);
            taskRepository.save(task);
            System.out.println("priority updated successfully");
        }
        else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }


    public void updateTaskDeadline(UUID id, LocalDate date) {
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setDeadline(date);
            taskRepository.save(task);
            System.out.println("deadline updated successfully");
        }
        else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }

    public void listTasks() {
        List<Task> tasks = taskRepository.findAll();
        for(Task task:tasks){
            System.out.println(task);
        }
    }

    public void deleteTask(UUID id) {
        try {
            taskRepository.delete(id);
        }catch (Exception e){
            System.out.println("could not delete task of id: " + id + "\n" + e);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

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
