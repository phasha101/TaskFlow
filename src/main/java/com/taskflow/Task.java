package com.taskflow;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class Task{

    public enum Status{
        PENDING, COMPLETE;
    }

    public enum Category{
        WORK, STUDY, CHORE, COOK, EXERCISE;
    }

    @JsonProperty
    ("id") private UUID ID;

    @JsonProperty("title")
    private String title;

    @JsonProperty("deadline")
    private LocalDate deadline;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("category")
    private Category category;

    public Task(String title, Category category, long deadlineInDays){

        this.title = title;
        this.category = category;
        this.deadline = LocalDate.now().plusDays(deadlineInDays);
        this.ID = UUID.randomUUID();
        this.status = Status.PENDING;
    }

    public Task() {
        // Required for Jackson
    }


    public UUID getID() {
        return ID;
    }
    
    public String getTitle() {
        return title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
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

    public void setDate(LocalDate date){
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
    
    
