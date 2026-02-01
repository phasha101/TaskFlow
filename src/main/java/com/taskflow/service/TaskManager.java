package com.taskflow.service;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.taskflow.model.Task;

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

    public void saveTasks(String filename) {
        File file = new File(filename);
        try (FileWriter fileWriter = new FileWriter(file, true);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            if (file.length() == 0) {
                String[] header = {"Title", "Id", "Deadline", "Status", "Category"};
                csvWriter.writeNext(header);
            }

            for (Task t : tasks) {
                String[] data = {
                        t.getTitle(),
                        t.getID().toString(),
                        t.getDeadline().toString(),
                        t.getStatus().toString(),
                        t.getCategory().toString()
                };
                csvWriter.writeNext(data);
            }

            System.out.println("Tasks saved to " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }


    public void loadTasks(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No CSV file found at: " + file.getAbsolutePath());
            return;
        } try (
                FileReader fileReader = new FileReader(file);
                CSVReader csvReader = new CSVReader(fileReader)) {
            String[] nextLine; tasks.clear();// reset current list
            // Skip header row
            csvReader.readNext();
            while ((nextLine = csvReader.readNext()) != null) {
                String title = nextLine[0];
                UUID id = UUID.fromString(nextLine[1]);
                LocalDate deadline = LocalDate.parse(nextLine[2]);
                Task.Status status = Task.Status.valueOf(nextLine[3]);
                Task.Category category = Task.Category.valueOf(nextLine[4]);
                // Rebuild Task object
                Task task = new Task();
                task.setTitle(title);
                task.setCategory(category);
                task.setDeadline(deadline);
                task.setStatus(status);
                task.setId(id);
                tasks.add(task); }
            System.out.println("Tasks loaded from " + filename); }
        catch (IOException e) {
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