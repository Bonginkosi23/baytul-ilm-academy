package com.mabuzagroup.baytulilmacademy.models;

public class Lesson {

    private String id;
    private String moduleId;
    private String title;
    private String description;
    private String youtubeUrl;
    private boolean active;
    private long createdAt;

    public Lesson() {
        // Required for Firestore
    }

    public Lesson(String id,
                  String moduleId,
                  String title,
                  String description,
                  String youtubeUrl,
                  boolean active,
                  long createdAt) {

        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.youtubeUrl = youtubeUrl;
        this.active = active;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
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