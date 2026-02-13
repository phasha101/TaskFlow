package com.taskflow.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.taskflow.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class TaskManager {

//    private List<Task> tasks = new ArrayList<>();

    TaskRepository taskRepository = new TaskRepository();

    public void createTask(String taskTitle, Category category, long daysToComplete, Priority priority) {
        Task task = new Task(taskTitle, category, daysToComplete, priority);
        taskRepository.save(task);
        System.out.println("Task added! on: " + LocalDate.now());
    }

    public void createTask(String taskTitle, Category category, long daysToComplete) {
        createTask(taskTitle, category, daysToComplete, Priority.LOW);
    }

    public void updateTaskTitle(UUID id, String title) {
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setTitle(title);
            taskRepository.update(task);
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
            taskRepository.update(task);
            System.out.println("category updated successfully");
        }
        else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }

    public void updateTaskPriority(UUID id, Priority newPriority){
        Task task = taskRepository.findById(id);
        if (task!=null){
            task.setPriority(newPriority);
            taskRepository.update(task);
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
            taskRepository.update(task);
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
        Task task = taskRepository.findById(id);
        if (task != null) {
            taskRepository.delete(id);
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task of id: " + id + " not found.");
        }
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM Task").executeUpdate();  // ✅ bulk delete
            tx.commit();
        }
    }



}
