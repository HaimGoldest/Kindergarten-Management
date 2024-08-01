package com.example.kindergarten_management.models;

import android.content.Context;

import java.util.Objects;

/**
 * Model class representing a staff member.
 */
public class StaffMemberModel extends BaseModel {
    private String name;
    private String rule;
    private String startWorkingDate;
    private KindergartenModel assignedKindergarten;
    private ClassModel assignedClass;

    /**
     * Constructor that initializes the staff member model with a context.
     * @param context The context used for initialization.
     */
    public StaffMemberModel(Context context) {
        super(context);
    }

    /**
     * Constructor that initializes the staff member model with given parameters.
     *
     * @param id                   The ID of the staff member.
     * @param name                 The name of the staff member.
     * @param rule                 The role of the staff member.
     * @param startWorkingDate     The start working date of the staff member.
     * @param assignedKindergarten
     * @param assignedClass
     */
    public StaffMemberModel(int id, String name, String rule, String startWorkingDate, KindergartenModel assignedKindergarten, ClassModel assignedClass) {
        super(id);
        this.name = name;
        this.rule = rule;
        this.startWorkingDate = startWorkingDate;
        this.assignedKindergarten = assignedKindergarten;
        this.assignedClass = assignedClass;
    }

    /**
     * Gets the name of the staff member.
     * @return The name of the staff member.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the staff member.
     * @param name The new name of the staff member.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the role of the staff member.
     * @return The role of the staff member.
     */
    public String getRule() {
        return rule;
    }

    /**
     * Sets the role of the staff member.
     * @param rule The new role of the staff member.
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * Gets the start working date of the staff member.
     * @return The start working date of the staff member.
     */
    public String getStartWorkingDate() {
        return startWorkingDate;
    }

    /**
     * Sets the start working date of the staff member.
     * @param startWorkingDate The new start working date of the staff member.
     */
    public void setStartWorkingDate(String startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public KindergartenModel getAssignedKindergarten() {
        return assignedKindergarten;
    }

    public void setAssignedKindergarten(KindergartenModel assignedKindergarten) {
        this.assignedKindergarten = assignedKindergarten;
    }

    public ClassModel getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(ClassModel assignedClass) {
        this.assignedClass = assignedClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StaffMemberModel that = (StaffMemberModel) o;

        if (!Objects.equals(name, that.name)) return false;
        if (rule != that.rule) return false;
        return Objects.equals(startWorkingDate, that.startWorkingDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rule != null ? rule.hashCode() : 0);
        result = 31 * result + (startWorkingDate != null ? startWorkingDate.hashCode() : 0);
        return result;
    }
}