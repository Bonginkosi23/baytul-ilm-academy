package com.mabuzagroup.baytulilmacademy.models;

public class User {

    private String uid;
    private String fullName;
    private String email;
    private String role;
    private String profileImage;
    private long joinedAt;
    private boolean active;
    private String accountType;

    public User() {
        // Required for Firestore
    }

    public User(String uid,
                String fullName,
                String email,
                String role,
                String profileImage,
                long joinedAt) {

        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
        this.joinedAt = joinedAt;
        this.active = true;
        this.accountType = "email";
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

    public long getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(long joinedAt) {
        this.joinedAt = joinedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}