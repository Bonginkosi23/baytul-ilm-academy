package com.mabuzagroup.baytulilmacademy.models;

public class Module {

    private String id;
    private String courseId;
    private String title;
    private String description;
    private boolean active;
    private long createdAt;

    public Module() {
        // Required for Firestore
    }

    public Module(String id,
                  String courseId,
                  String title,
                  String description,
                  boolean active,
                  long createdAt) {

        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title;
    }
}