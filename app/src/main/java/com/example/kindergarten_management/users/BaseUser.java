package com.example.kindergarten_management.users;

public abstract class BaseUser {

    private String uid;
    private String email;
    private String name;
    private String rule;

    public BaseUser(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.rule = this.getClass().getSimpleName();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

}
