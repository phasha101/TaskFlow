package com.taskflow;

import com.taskflow.cli.Main;
import com.taskflow.model.Category;
import com.taskflow.model.Priority;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MainParserTest {

    @Test
    void testValidCategoryInput() {
        Scanner scanner = new Scanner("WORK\n");
        Category category = Main.parseCategory(scanner);
        assertEquals(Category.WORK, category);
    }

    @Test
    void testInvalidCategoryInput() {
        Scanner scanner = new Scanner("INVALID\n");
        Category category = Main.parseCategory(scanner);
        assertNull(category, "Invalid category should return null");
    }

    @Test
    void testValidPriorityInput() {
        Scanner scanner = new Scanner("HIGH\n");
        Priority priority = Main.parsePriority(scanner);
        assertEquals(Priority.HIGH, priority);
    }

    @Test
    void testInvalidPriorityInput() {
        Scanner scanner = new Scanner("URGENT\n");
        Priority priority = Main.parsePriority(scanner);
        assertNull(priority, "Invalid priority should return null");
    }

    @Test
    void testValidUUIDInput() {
        UUID expected = UUID.randomUUID();
        Scanner scanner = new Scanner(expected.toString() + "\n");
        UUID parsed = Main.parseUUID(scanner);
        assertEquals(expected, parsed);
    }

    @Test
    void testInvalidUUIDInput() {
        Scanner scanner = new Scanner("not-a-uuid\n");
        UUID parsed = Main.parseUUID(scanner);
        assertNull(parsed, "Invalid UUID should return null");
    }
}
