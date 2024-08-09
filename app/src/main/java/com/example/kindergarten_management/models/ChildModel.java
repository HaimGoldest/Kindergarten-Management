package com.example.kindergarten_management.models;

import com.example.kindergarten_management.controllers.DatabaseController;

import java.util.List;

/**
 * Model class representing a Child class.
 */
public class ChildModel extends BaseModel {
    private String fullName;
    private int age;
    private String city;
    private int kindergartenId;
    private List<ClassModel> favoriteClasses;


    public ChildModel(int id) {
        super(id);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public KindergartenModel getKindergarten() {

        return DatabaseController.getInstance(getContext()).getKindergarten(kindergartenId);
    }

    public void setKindergarten(int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    public List<ClassModel> getFavoriteClasses() {
        return favoriteClasses;
    }

    public void setFavoriteClasses(List<ClassModel> favoriteClasses) {
        this.favoriteClasses = favoriteClasses;
    }
}
