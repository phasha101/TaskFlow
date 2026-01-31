package com.taskflow;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerPersistenceTest {

    private TaskManager manager;
    private final String filename = "tasks_test.csv";

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    @AfterEach
    void tearDown() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete(); // clean up after each test
        }
    }

    @Test
    void testSaveAndLoadTasksCSV() throws IOException {
        manager.createTask("Do dishes", Task.Category.CHORE, 2);
        manager.createTask("Study Java", Task.Category.STUDY, 5);

        // Save tasks to CSV
        manager.saveTasks(filename);

        // Clear in-memory list
        manager.getTasks().clear();
        assertTrue(manager.getTasks().isEmpty(), "Tasks should be cleared before loading");

        // Load tasks back
        manager.loadTasks(filename);

        assertEquals(2, manager.getTasks().size(), "Two tasks should be loaded");
        assertEquals("Do dishes", manager.getTasks().get(0).getTitle());
        assertEquals(Task.Category.CHORE, manager.getTasks().get(0).getCategory());
    }

    @Test
    void testLoadFromEmptyCSVFile() throws IOException {
        File file = new File(filename);
        assertFalse(file.exists(), "File should not exist initially");

        manager.loadTasks(filename);
        assertTrue(manager.getTasks().isEmpty(), "No tasks should be loaded from non-existent file");
    }

    @Test
    void testSaveTaskPreservesIdDeadlineAndStatusCSV() throws IOException {
        manager.createTask("Exercise", Task.Category.EXERCISE, 3);
        UUID id = manager.getTasks().get(0).getID();
        LocalDate deadline = manager.getTasks().get(0).getDeadline();

        manager.saveTasks(filename);
        manager.getTasks().clear();
        manager.loadTasks(filename);

        Task loaded = manager.getTasks().get(0);
        assertEquals(id, loaded.getID(), "UUID should be preserved");
        assertEquals(deadline, loaded.getDeadline(), "Deadline should be preserved");
        assertEquals(Task.Status.PENDING, loaded.getStatus(), "Status should be preserved");
    }
}
