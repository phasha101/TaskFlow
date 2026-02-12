package com.taskflow.cli;

import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        boolean running = true;

        while (running) {
            System.out.println("\n--- TaskFlow Menu ---");
            System.out.println("1. Create Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Update Task Title & Category");
            System.out.println("4. Delete Task");
            System.out.println("5. Change Priority");
            System.out.println("6. Update Deadline");
            System.out.println("7. Change Status");
            System.out.println("8. Exit");
            System.out.print("Choose an option (number): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    Category category = parseCategory(scanner);
                    if (category == null) break;

                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();

                    System.out.print("Days to complete: ");
                    long days = scanner.nextLong();
                    scanner.nextLine(); // consume newline

                    Priority priority = parsePriority(scanner);
                    if (priority == null) break;

                    manager.createTask(title, category, days, priority);
                }
                case 2 -> manager.listTasks();
                case 3 -> {
                    UUID id = parseUUID(scanner);
                    if (id == null) break;

                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();

                    Category newCategory = parseCategory(scanner);
                    if (newCategory == null) break;

                    manager.updateTaskTitle(id, newTitle);
                    manager.updateTaskCategory(id, newCategory);
                }
                case 4 -> {
                    UUID id = parseUUID(scanner);
                    if (id == null) break;
                    manager.deleteTask(id);
                }
                case 5 -> {
                    UUID id = parseUUID(scanner);
                    if (id == null) break;

                    Priority newPriority = parsePriority(scanner);
                    if (newPriority == null) break;

                    manager.updateTaskPriority(id, newPriority);
                }
                case 6 -> {
                    UUID id = parseUUID(scanner);
                    if (id == null) break;

                    System.out.print("Enter new deadline (YYYY-MM-DD): ");
                    try {
                        LocalDate newDeadline = LocalDate.parse(scanner.nextLine());
                        manager.updateTaskDeadline(id, newDeadline);
                    } catch (Exception e) {
                        System.out.println("❌ Invalid date format. Use YYYY-MM-DD.");
                    }
                }
                case 7 -> {
                    UUID id = parseUUID(scanner);
                    if (id == null) break;

                    Task task = manager.getTasks().stream()
                            .filter(t -> t.getId().equals(id))
                            .findFirst()
                            .orElse(null);

                    if (task != null) {
                        task.markComplete();
                        manager.updateTaskTitle(task.getId(), task.getTitle()); // persist change
                        System.out.println("Task marked complete.");
                    } else {
                        System.out.println("Task not found.");
                    }
                }
                case 8 -> {
                    running = false;
                    System.out.println("Exiting TaskFlow...");
                }
                default -> System.out.println("❌ Invalid choice, try again.");
            }
        }
        scanner.close();
    }

    public static Category parseCategory(Scanner scanner) {
        System.out.print("Enter category (WORK/STUDY/CHORE/COOK/EXERCISE): ");
        String input = scanner.nextLine().toUpperCase();
        try {
            return Category.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid category. Try again.");
            return null;
        }
    }

    public static Priority parsePriority(Scanner scanner) {
        System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
        String input = scanner.nextLine().toUpperCase();
        try {
            return Priority.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid priority. Try again.");
            return null;
        }
    }

    public static UUID parseUUID(Scanner scanner) {
        System.out.print("Enter task ID: ");
        String input = scanner.nextLine();
        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid ID format.");
            return null;
        }
    }
}
