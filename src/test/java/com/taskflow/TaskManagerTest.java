package com.taskflow;

import com.taskflow.Task;
import com.taskflow.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.*;

class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    @Test
    void testCreateTask() {
        manager.createTask("Do dishes", src.main.java.com.taskflow.Task.Category.CHORE, 2);
        assertFalse(manager.getTasks().isEmpty(), "Task list should not be empty");
        Task task = manager.getTasks().get(0);
        assertEquals("Do dishes", task.getTitle());
        assertEquals(Task.Category.CHORE, task.getCategory());
        assertEquals(Task.Status.PENDING, task.getStatus());
    }

    @Test
    void testUpdateTaskTitle() {
        manager.createTask("Old Title", Task.Category.WORK, 3);
        UUID id = manager.getTasks().get(0).getID();

        manager.updateTaskTitle(id, "New Title");
        assertEquals("New Title", manager.getTasks().get(0).getTitle());
    }

    @Test
    void testUpdateTaskCategory() {
        manager.createTask("Study Java", Task.Category.STUDY, 5);
        UUID id = manager.getTasks().get(0).getID();

        manager.updateTaskCategory(id, Task.Category.EXERCISE);
        assertEquals(Task.Category.EXERCISE, manager.getTasks().get(0).getCategory());
    }

    @Test
    void testUpdateTaskDeadline() {
        manager.createTask("Sweep floor", Task.Category.CHORE, 1);
        UUID id = manager.getTasks().get(0).getID();

        LocalDate newDeadline = LocalDate.now().plusDays(10);
        manager.updateTaskDeadline(id, newDeadline);
        assertEquals(newDeadline, manager.getTasks().get(0).getDeadline());
    }

    @Test
    void testDeleteTask() {
        manager.createTask("Cook dinner", Task.Category.COOK, 2);
        UUID id = manager.getTasks().get(0).getID();

        manager.deleteTask(id);
        assertTrue(manager.getTasks().isEmpty(), "Task should be deleted");
    }

    @Test
    void testDeleteNonExistentTask() {
        UUID fakeId = UUID.randomUUID();
        manager.deleteTask(fakeId);
        assertEquals(0, manager.getTasks().size(), "No tasks should be deleted");
    }
}
