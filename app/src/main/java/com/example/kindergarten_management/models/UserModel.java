package com.example.kindergarten_management.models;

public class UserModel {

    private String uid;
    private String email;
    private String name;
    private String rule;

    public UserModel(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.name = "User";
        this.rule = "Rule";
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

    public void setRule(String rule) {
        this.rule = rule;
    }
}