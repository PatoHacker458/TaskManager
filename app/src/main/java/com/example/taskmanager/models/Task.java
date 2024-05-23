package com.example.taskmanager.models;
public class Task {
    private String id;
    private String name;
    private String description;
    private String deadline;
    private Boolean completed;
    private String uid;

    public Task() {
    }

    public Task(String id, String name, String description, String deadline, Boolean completed, String uid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.completed = completed;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return deadline;
    }

    public void setDueDate(String dueDate) {
        this.deadline = dueDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}