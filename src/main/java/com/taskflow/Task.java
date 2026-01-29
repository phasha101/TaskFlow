package src.main.java.com.taskflow;
import java.time.LocalDate;
import java.util.UUID;

public class Task{

    public enum Status{
        PENDING, COMPLETE;
    }

    public enum Category{
        WORK, STUDY, CHORE, COOK, EXERCISE;
    }

    private UUID ID;
    private String title;
    private LocalDate deadline;
    private Status status;
    private Category category;

    public Task(String title, Category category, long deadlineInDays){

        this.title = title;
        this.category = category;
        this.deadline = LocalDate.now().plusDays(deadlineInDays);
        this.ID = UUID.randomUUID();
        this.status = Status.PENDING;
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

    @Override
    public String toString() {
        return "Task [ID=" + ID + ", title=" + title + ", deadline=" + deadline + ", status=" + status + ", category="
                + category + "]";
    }
}
    
    
