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
        manager.createTask("Do dishes", com.taskflow.Task.Category.CHORE, 2);
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

    @Test
    void testCreateTaskWithZeroDaysDeadline() {
        manager.createTask("Immediate task", Task.Category.WORK, 0);
        Task task = manager.getTasks().get(0);
        assertEquals(LocalDate.now(), task.getDeadline(), "Deadline should be today when daysToComplete = 0");
    }

    @Test
    void testCreateTaskWithNegativeDaysDeadline() {
        manager.createTask("Past task", Task.Category.STUDY, -5);
        Task task = manager.getTasks().get(0);
        assertTrue(task.getDeadline().isBefore(LocalDate.now()), "Deadline should be in the past when daysToComplete < 0");
    }

    @Test
    void testUpdateNonExistentTaskTitle() { UUID fakeId = UUID.randomUUID();
        manager.updateTaskTitle(fakeId, "Ghost Task");
        assertTrue(manager.getTasks().isEmpty(), "No task should be updated when ID does not exist");
    }

    @Test
    void testDeleteFromEmptyList() {
        UUID fakeId = UUID.randomUUID();
        manager.deleteTask(fakeId);
        assertTrue(manager.getTasks().isEmpty(), "Deleting from empty list should not throw errors");
    }

    @Test
    void testMultipleTasksCreationAndDeletion() {
        manager.createTask("Task 1", Task.Category.CHORE, 1);
        manager.createTask("Task 2", Task.Category.COOK, 2);
        manager.createTask("Task 3", Task.Category.EXERCISE, 3);
        assertEquals(3, manager.getTasks().size(), "Three tasks should be created");
        UUID idToDelete = manager.getTasks().get(1).getID();
        manager.deleteTask(idToDelete);
        assertEquals(2, manager.getTasks().size(), "One task should be deleted");
        assertFalse(manager.getTasks().stream().anyMatch(t -> t.getID().equals(idToDelete)), "Deleted task should not remain in the list");
    }

}
