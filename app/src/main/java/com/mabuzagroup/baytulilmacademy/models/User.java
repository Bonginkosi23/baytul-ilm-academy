package com.mabuzagroup.baytulilmacademy.models;

public class User {

    private String uid;
    private String fullName;
    private String email;
    private String role;
    private String profileImage;
    private long joinedAt;

    public User() {
        // Required for Firestore
    }

    public User(String uid,
                String fullName,
                String email,
                String role,
                String profileImage,
                Long joinedAt) {

        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
        this.joinedAt = joinedAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Long getJoineAt(Long joinedAt) {
        return joinedAt;
    }

    public void setJoinedAt(Long joinedAt) {
        this.joinedAt = joinedAt;
    }
}