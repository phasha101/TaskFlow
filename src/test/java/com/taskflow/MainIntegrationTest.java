package com.taskflow;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainIntegrationTest {

    private final String filename = "tasks_test.csv";

    @BeforeEach
    void cleanFileBefore() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void cleanFileAfter() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testPersistenceRoundTrip() throws IOException {
        TaskManager manager = new TaskManager();
        manager.createTask("Do dishes", Task.Category.CHORE, 2);
        manager.saveTasks(filename);

        TaskManager loaded = new TaskManager();
        loaded.loadTasks(filename);

        // ✅ Assertions
        assertEquals(1, loaded.getTasks().size(), "Should load exactly one task");
        assertEquals("Do dishes", loaded.getTasks().get(0).getTitle(), "Task title should match");
        assertEquals(Task.Category.CHORE, loaded.getTasks().get(0).getCategory(), "Category should match");
    }
}
