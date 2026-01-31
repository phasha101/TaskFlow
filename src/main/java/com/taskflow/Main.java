package com.taskflow;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        boolean running = true;
        while (running) {
            System.out.println("\n--- TaskFlow Menu ---");
            System.out.println("1. Create Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Update Task Title");
            System.out.println("4. Delete Task");
            System.out.println("5. Save Tasks (CSV)");
            System.out.println("6. Load Tasks (CSV)");
            System.out.println("7. Exit");
            System.out.print("Choose an option(by their numbers please): ");
            int choice = scanner.nextInt(); scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter category (WORK/STUDY/CHORE/COOK/EXERCISE): ");
                    Task.Category category = Task.Category.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Days to complete: ");
                    long days = scanner.nextLong();
                    manager.createTask(title, category, days);
                }
                case 2 -> manager.listTasks();
                case 3 -> {
                    System.out.print("Enter task ID to update: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();
                    manager.updateTaskTitle(id, newTitle);
                } case 4 -> {
                    System.out.print("Enter task ID to delete: ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    manager.deleteTask(id);
                } case 5 -> manager.saveTasks("tasks.csv");
                case 6 -> manager.loadTasks("tasks.csv");
                case 7 -> {
                    running = false;
                    System.out.println("Exiting TaskFlow...");
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        scanner.close();
    }
}
