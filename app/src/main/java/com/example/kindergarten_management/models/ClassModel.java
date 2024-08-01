package com.example.kindergarten_management.models;

import android.content.Context;

import java.util.Objects;

/**
 * Model class representing a kindergarten class.
 */
public class ClassModel extends BaseModel {
    private String type;
    private int maxChildren;
    private final int minChildren = 15;
    private int maxAge;
    private int minAge;
    private KindergartenModel kindergarten;

    /**
     * Constructor that initializes the class model with a context.
     * @param context The context used for initialization.
     */
    public ClassModel(Context context) {
        super(context);
    }

    public ClassModel(int id, String type, int maxChildren, int maxAge, int minAge, KindergartenModel kindergarten) {
        super(id);
        this.type = type;
        this.maxChildren = maxChildren;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.kindergarten = kindergarten;
    }

    /**
     * Gets the type of the class.
     * @return The type of the class.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the class.
     * @param type The new type of the class.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the maximum number of children allowed in the class.
     * @return The maximum number of children.
     */
    public int getMaxChildren() {
        return maxChildren;
    }

    /**
     * Sets the maximum number of children allowed in the class.
     * @param maxChildren The new maximum number of children.
     */
    public void setMaxChildren(int maxChildren) {
        this.maxChildren = maxChildren;
    }

    /**
     * Gets the minimum number of children required in the class.
     * @return The minimum number of children.
     */
    public int getMinChildren() {
        return minChildren;
    }

    /**
     * Gets the maximum age allowed in the class.
     * @return The maximum age.
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the maximum age allowed in the class.
     * @param maxAge The new maximum age.
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Gets the minimum age required in the class.
     * @return The minimum age.
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Sets the minimum age required in the class.
     * @param minAge The new minimum age.
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClassModel that = (ClassModel) o;

        if (maxChildren != that.maxChildren) return false;
        if (minChildren != that.minChildren) return false;
        if (maxAge != that.maxAge) return false;
        if (minAge != that.minAge) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + maxChildren;
        result = 31 * result + minChildren;
        result = 31 * result + maxAge;
        result = 31 * result + minAge;
        return result;
    }
}