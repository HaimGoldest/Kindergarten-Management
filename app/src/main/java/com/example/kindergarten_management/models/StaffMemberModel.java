package com.example.kindergarten_management.models;

import android.content.Context;

import com.example.kindergarten_management.controllers.DatabaseController;

import java.util.Objects;

/**
 * Model class representing a staff member.
 */
public class StaffMemberModel extends BaseModel {
    private String name;
    private String rule;
    private String startWorkingDate;
    private int assignedKindergartenId;
    private int assignedClassId;

    /**
     * Constructor that initializes the staff member model with a context.
     * @param context The context used for initialization.
     */
    public StaffMemberModel(Context context) {
        super(context);
    }

    /**
     * Constructor that initializes the staff member model with given parameters.
     */
    public StaffMemberModel(int id, String name, String rule, String startWorkingDate, int assignedKindergartenId, int assignedClassId) {
        super(id);
        this.name = name;
        this.rule = rule;
        this.startWorkingDate = startWorkingDate;
        this.assignedKindergartenId = assignedKindergartenId;
        this.assignedClassId = assignedClassId;
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

        return DatabaseController.getInstance(getContext()).getKindergarten(assignedKindergartenId);
    }

    public void setAssignedKindergarten(int assignedKindergartenId) {
        this.assignedKindergartenId = assignedKindergartenId;
    }

    public ClassModel getAssignedClass() {
        return DatabaseController.getInstance(getContext()).getClassModel(assignedClassId);
    }

    public void setAssignedClass(int assignedClassId) {
        this.assignedClassId = assignedClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StaffMemberModel that = (StaffMemberModel) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!rule.equals(that.rule)) return false;
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