# TaskFlow 📝

![Build Status](https://github.com/phasha101/TaskFlow/actions/workflows/maven.yml/badge.svg)


TaskFlow is a lightweight task management application built in Java.  
It supports creating, updating, deleting, saving, and loading tasks, with persistence via **CSV files** and an interactive **CLI interface**.

---

## 👩‍💻 Developer Setup 
1. Clone the repository:
2. ```bash git clone``` https://github.com/YOUR_USERNAME/TaskFlow.git 
   cd TaskFlow
3. Start PostgreSQL with Docker 
4. ```docker run --name taskflow-db \ -e POSTGRES_PASSWORD=0000 \ -e POSTGRES_DB=task \ -p 5432:5432 \ -d postgres:15```
5. Create the database schema

```bash
   docker exec -it taskflow-db psql -U postgres -d task
   ```

7. CREATE EXTENSION IF NOT EXISTS pgcrypto;
```bash 

    CREATE TABLE task (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    priority VARCHAR(20),
    status VARCHAR(20) DEFAULT 'pending',
    category VARCHAR(50),
    deadline DATE
);
  ```

8. - Configure JDBC:
     - TaskFlow uses JDBC to connect to PostgreSQL.
   Default connection settings:

     - URL: jdbc:postgresql://localhost:5432/task

     - User: postgres

     - Password: 0000

Update these in TaskManager.java if your environment differs.

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
- Save tasks via Hibernate ORM (PostgreSQL backend).
- Load tasks via Hibernate ORM (PostgreSQL backend).
- Interactive CLI menu

---

## 📂 Project Structure

src/
├── main/java/com/taskflow/
│   ├── model/
│   │   ├── Task.java        # Task entity
│   │   ├── Category.java    # Enum for categories
│   │   ├── Priority.java    # Enum for priorities
│   │   └── Status.java      # Enum for task status
│   │   └── TaskRepository.java # Persistence via Hibernate sessions
│   ├── service/
│   │   └── TaskManager.java # Core logic (CRUD + persistence)
│   │
│   └── cli/
│       └── Main.java        # CLI interface
│
└── test/java/com/taskflow/
├── TaskManagerTest.java         # Unit tests (CRUD, deadlines, priority)
├── TaskManagerPersistenceTest.java # Integration tests (CSV persistence round‑trip)
├── MainParserTest.java          # Unit tests for CLI input parsers (Category, Priority, UUID)
└── MainIntegrationTest.java     # Integration tests for CLI menu flow (end‑to‑end scenarios)

---

## ⚙️ Requirements
- Java 17+ (tested with JDK Valhalla EA 23)
- Maven 3.8+
- Hibernate ORM 6.x
- PostgreSQL 15
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

# 🖥️ CLI Menu

### --- TaskFlow Menu ---
1. Create Task
2. List Tasks
3. Update Task Title
4. Update Task Category
5. Update Task Deadline
6. Mark Task Complete
7. Change Priority
8. Delete Task
9. Save Tasks (CSV)
10. Load Tasks (CSV)
11. Exit

## 🧪 Example SQL for Mock Data

### Insert sample tasks directly into PostgreSQL:
```bash
    INSERT INTO task (id, title, priority, status, category, deadline)
VALUES
    (gen_random_uuid(), 'Do dishes', 'HIGH', 'pending', 'CHORE', '2026-02-10'),
    (gen_random_uuid(), 'Finish project report', 'MEDIUM', 'pending', 'WORK', '2026-02-15'),
    (gen_random_uuid(), 'Go for a run', 'LOW', 'pending', 'EXERCISE', '2026-02-08'),
    (gen_random_uuid(), 'Cook dinner', 'MEDIUM', 'pending', 'COOK', '2026-02-07');
```


## 🤝 Contributing
### We welcome contributions!

1. Fork the repository.

2. Create a feature branch:

```bash
git checkout -b feature/your-feature
```
3. Make your changes and add tests.

4. Run mvn test to ensure everything passes.

5. Submit a pull request 🚀.