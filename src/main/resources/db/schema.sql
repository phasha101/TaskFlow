-- Create the database (run once per machine)
CREATE DATABASE tasks;

-- Switch to the database
\c tasks;

-- Create the task table
CREATE TABLE task (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    deadline DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL
);
