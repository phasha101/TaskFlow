import java.util.Date;
import java.util.UUID;

public class Task{

    enum statuses{
        PENDING, COMPLETE;
    }

    private UUID ID;
    private String title;
    private Date deadline;
    private statuses status;
    private String category;

    public Task(String title, String category, Date deadline){

        this.title = title;
        this.category = category;
        this.deadline = deadline;
        this.ID = UUID.randomUUID();
        this.status = statuses.PENDING;
    }

    public UUID getID() {
        return ID;
    }
    public void setID(int iD) {}
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public statuses getStatus() {
        return status;
    }
    public void setStatus(statuses status) {
        this.status = status;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

}
    
    
