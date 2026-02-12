package com.taskflow.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="task")
public class Task{

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    @GeneratedValue
    @JsonProperty
    ("id") private UUID ID;

    @Column(name = "title", nullable = false)
    @JsonProperty("title")
    private String title;

    @Column(name = "deadline", nullable = false)
    @JsonProperty("deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    @JsonProperty("category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @JsonProperty("priority")
    private Priority priority;

    public Task(String title, Category category, long deadlineInDays, Priority priority){

        this.title = title;
        this.category = category;
        this.deadline = LocalDate.now().plusDays(deadlineInDays);
        this.ID = UUID.randomUUID();
        this.status = Status.PENDING;
        this.priority = priority;
    }

    public Task() {
        // Required for Jackson
    }

    public Priority getPriority() {
        return this.priority;
    }

    public UUID getId() {
        return this.ID;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public Status getStatus() {
        return this.status;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void markComplete() { this.status = Status.COMPLETE; }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public void setDeadline(LocalDate date){
        this.deadline = date;
    }

    public void setId(UUID id){
        this.ID = id;
    }

    @Override
    public String toString() {
        return "Task [ID=" + ID + ", title=" + title + ", deadline=" + deadline + ", status=" + status + ", category="
                + category + "]";
    }
}
    
    
