package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import com.taskflow.model.Status;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class TaskManagerPersistenceTest {

    private TaskManager manager;
    private final String url = "jdbc:postgresql://localhost:5432/tasks"; // match your DB name
    private final String user = "postgres";
    private final String password = "0000";

    @BeforeEach
    void setUp() {
        clearTaskTable();   // clear DB before each test
        manager = new TaskManager();
    }

    @AfterEach
    void tearDown() {
        clearTaskTable();   // clear DB after each test
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
    void testSaveAndLoadTasksDB() {
        manager.createTask("Do dishes", Category.CHORE, 2);
        manager.createTask("Study Java", Category.STUDY, 5);

        manager.saveTasksToDB(manager.getTasks());
        manager.getTasks().clear();

        manager.loadTasksFromDB(manager.getTasks());

        assertEquals(2, manager.getTasks().size(), "Two tasks should be loaded");
        assertEquals("Do dishes", manager.getTasks().get(0).getTitle());
        assertEquals(Category.CHORE, manager.getTasks().get(0).getCategory());
    }

    @Test
    void testLoadFromEmptyDB() {
        manager.loadTasksFromDB(manager.getTasks());
        assertTrue(manager.getTasks().isEmpty(), "No tasks should be loaded from empty DB");
    }

    @Test
    void testSaveTaskPreservesIdDeadlineAndStatusDB() {
        manager.createTask("Exercise", Category.EXERCISE, 3);
        UUID id = manager.getTasks().get(0).getID();
        LocalDate deadline = manager.getTasks().get(0).getDeadline();

        manager.saveTasksToDB(manager.getTasks());
        manager.getTasks().clear();
        manager.loadTasksFromDB(manager.getTasks());

        Task loaded = manager.getTasks().get(0);
        assertEquals(id, loaded.getID(), "UUID should be preserved");
        assertEquals(deadline, loaded.getDeadline(), "Deadline should be preserved");
        assertEquals(Status.PENDING, loaded.getStatus(), "Status should be preserved");
    }

    @Test
    void testPriorityRoundTripDB() {
        manager.createTask("Do dishes", Category.CHORE, 2, Priority.HIGH);
        manager.saveTasksToDB(manager.getTasks());

        manager.getTasks().clear();
        manager.loadTasksFromDB(manager.getTasks());

        assertEquals(1, manager.getTasks().size(), "Should load exactly one task");
        Task task = manager.getTasks().get(0);
        assertEquals("Do dishes", task.getTitle());
        assertEquals(Category.CHORE, task.getCategory());
        assertEquals(Priority.HIGH, task.getPriority(), "Priority should persist across save/load");
    }
}
