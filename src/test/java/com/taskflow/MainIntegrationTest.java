package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
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
    void testPersistenceRoundTripDB() {
        TaskManager manager = new TaskManager();
        manager.createTask("Do dishes", Category.CHORE, 2);

        // Save to DB
        manager.saveTasksToDB(manager.getTasks());

        // Load from DB
        TaskManager loaded = new TaskManager();
        loaded.loadTasksFromDB(loaded.getTasks());

        // ✅ Assertions
        assertEquals(1, loaded.getTasks().size(), "Should load exactly one task");
        assertEquals("Do dishes", loaded.getTasks().get(0).getTitle(), "Task title should match");
        assertEquals(Category.CHORE, loaded.getTasks().get(0).getCategory(), "Category should match");
    }

}
