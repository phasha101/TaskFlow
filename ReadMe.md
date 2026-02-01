# TaskFlow 📝

TaskFlow is a lightweight task management application built in Java.  
It supports creating, updating, deleting, saving, and loading tasks, with persistence via **CSV files** and an interactive **CLI interface**.

---

## 🚀 Features
- Create tasks with:
    - Title
    - Category (WORK, STUDY, CHORE, COOK, EXERCISE)
    - Deadline (days to complete)
- Update tasks:
    - Title
    - Category
    - Deadline
    - Status (mark complete)
- Delete tasks by ID
- List all tasks
- Save tasks to CSV
- Load tasks from CSV
- Interactive CLI menu

---

## 📂 Project Structure

src/
├── main/java/com/taskflow/
│    ├── Task.java           # Task model
│    ├── TaskManager.java    # Core logic (CRUD + persistence)
│    └── Main.java           # CLI interface
└── test/java/com/taskflow/
├── TaskManagerTest.java        # Unit tests for CRUD
├── TaskManagerPersistenceTest.java  # JSON persistence tests

---

## ⚙️ Requirements
- Java 17+ (tested with JDK Valhalla EA 23)
- Maven 3.8+
- [OpenCSV](http://opencsv.sourceforge.net/) for CSV handling
- JUnit 5 for testing

---

## 🛠️ Build & Run
Clone the repo and run:

```bash
mvn clean install

```

Run Tests
```bash
mvn test

```

Run CLI

```commandline
mvn compile exec:java -Dexec.mainClass="com.taskflow.cli.Main"

```

🖥️ CLI Menu

--- TaskFlow Menu ---
1. Create Task
2. List Tasks
3. Update Task Title
4. Update Task Category
5. Update Task Deadline
6. Mark Task Complete
7. Delete Task
8. Save Tasks (CSV)
9. Load Tasks (CSV)
10. Exit

