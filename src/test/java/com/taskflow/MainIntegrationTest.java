package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.service.TaskManager;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class MainIntegrationTest {

    private TaskManager manager;
    private final String url = "jdbc:postgresql://localhost:5432/tasks"; // match your DB name
    private final String user = "postgres";
    private final String password = "0000";

    @BeforeEach
    void clearDBBefore() {
        clearTaskTable();
        manager = new TaskManager();
    }

    @AfterEach
    void clearDBAfter() {
        clearTaskTable();
    }

    private void clearTaskTable() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE task;");
        } catch (SQLException e) {
            System.out.println("Error clearing DB: " + e.getMessage());
        }
    }

    @Test
    void testPersistenceRoundTripDB() {
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
