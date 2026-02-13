package com.taskflow;

import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import com.taskflow.model.Status;
import com.taskflow.model.Task;
import com.taskflow.service.TaskManager;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class TaskManagerPersistenceTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
        manager.deleteAll();
    }

    @Test
    void testCreateAndListTasks() {
        manager.createTask("Do dishes", Category.CHORE, 2, Priority.HIGH);
        manager.createTask("Study Java", Category.STUDY, 5);

        List<Task> tasks = manager.getTasks();
        assertEquals(2, tasks.size(), "Two tasks should be persisted");
    }

    @Test
    void testUpdateTaskTitle() {
        manager.createTask("Exercise", Category.EXERCISE, 3);
        Task task = manager.getTasks().get(0);

        manager.updateTaskTitle(task.getId(), "Morning Exercise");
        Task updated = manager.getTasks().get(0);

        assertEquals("Morning Exercise", updated.getTitle());
    }

    @Test
    void testDeleteTask() {
        manager.createTask("Clean room", Category.CHORE, 1);
        Task task = manager.getTasks().get(0);

        manager.deleteTask(task.getId());
        List<Task> tasks = manager.getTasks();

        assertTrue(tasks.isEmpty(), "Task should be deleted");
    }

    @Test
    void testPriorityRoundTrip() {
        manager.createTask("Do dishes", Category.CHORE, 2, Priority.HIGH);
        Task task = manager.getTasks().get(0);

        assertEquals(Priority.HIGH, task.getPriority(), "Priority should persist");
    }
}
