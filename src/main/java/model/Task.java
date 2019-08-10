package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private String taskDate;
    private String imagePath;

    // на спринге всегда нужен пустой конструктор
    public Task() {
    }

    public Task(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.taskDate = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return taskDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
}
