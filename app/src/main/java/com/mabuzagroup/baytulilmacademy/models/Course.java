package com.mabuzagroup.baytulilmacademy.models;

public class Course {

    private String id;
    private String categoryId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private boolean active;
    private long createdAt;

    public Course() {
        // Required for Firestore
    }

    public Course(String id,
                  String categoryId,
                  String title,
                  String description,
                  String thumbnailUrl,
                  boolean active,
                  long createdAt) {

        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.active = active;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
}