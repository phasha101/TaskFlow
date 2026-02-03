package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import com.taskflow.model.Status;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
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
        manager.createTask("Do dishes", Category.CHORE, 2);
        manager.createTask("Study Java", Category.STUDY, 5);

        // Save tasks to CSV
        manager.saveTasks(filename);

        // Clear in-memory list
        manager.getTasks().clear();
        assertTrue(manager.getTasks().isEmpty(), "Tasks should be cleared before loading");

        // Load tasks back
        manager.loadTasks(filename);

        assertEquals(2, manager.getTasks().size(), "Two tasks should be loaded");
        assertEquals("Do dishes", manager.getTasks().get(0).getTitle());
        assertEquals(Category.CHORE, manager.getTasks().get(0).getCategory());
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
        manager.createTask("Exercise", Category.EXERCISE, 3);
        UUID id = manager.getTasks().get(0).getID();
        LocalDate deadline = manager.getTasks().get(0).getDeadline();

        manager.saveTasks(filename);
        manager.getTasks().clear();
        manager.loadTasks(filename);

        Task loaded = manager.getTasks().get(0);
        assertEquals(id, loaded.getID(), "UUID should be preserved");
        assertEquals(deadline, loaded.getDeadline(), "Deadline should be preserved");
        assertEquals(Status.PENDING, loaded.getStatus(), "Status should be preserved");
    }

    @Test void testPriorityRoundTrip() throws IOException {
        TaskManager manager = new TaskManager();
        manager.createTask("Do dishes", Category.CHORE, 2, Priority.HIGH);
        manager.saveTasks(filename);
        TaskManager loaded = new TaskManager();
        loaded.loadTasks(filename);
        assertEquals(1, loaded.getTasks().size(), "Should load exactly one task");
        Task task = loaded.getTasks().get(0);
        assertEquals("Do dishes", task.getTitle());
        assertEquals(Category.CHORE, task.getCategory());
        assertEquals(Priority.HIGH, task.getPriority(), "Priority should persist across save/load"); }
}
