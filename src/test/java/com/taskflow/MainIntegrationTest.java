package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
import com.taskflow.config.AppConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class MainIntegrationTest {

    @Autowired
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager.deleteAll();
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
