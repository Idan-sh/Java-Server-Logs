package obj;

import java.util.Date;

public class Todo {
    private static int idGenerator = 0;
    private final int id = ++idGenerator;
    private String title;
    private String content;
    private long dueDate;
    private String status = "PENDING";


    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public long getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() { return content; }

    public static void decreaseIdGenerator() { idGenerator--; }


}
