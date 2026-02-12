package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class MainIntegrationTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager(); // fresh TaskManager each test
    }

    @Test
    void testPersistenceRoundTripDB() {
        // Create and persist a task
        manager.createTask("Do dishes", Category.CHORE, 2);

        // Load tasks directly from DB
        List<Task> tasks = manager.getTasks();

        // ✅ Assertions
        assertEquals(1, tasks.size(), "Should load exactly one task");
        assertEquals("Do dishes", tasks.get(0).getTitle(), "Task title should match");
        assertEquals(Category.CHORE, tasks.get(0).getCategory(), "Category should match");
    }
}
