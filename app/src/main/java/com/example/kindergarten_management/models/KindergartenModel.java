package com.example.kindergarten_management.models;

import android.content.Context;

import com.example.kindergarten_management.enums.OrganizationalAffiliations;

import java.util.Objects;

/**
 * Model class representing a kindergarten.
 */
public class KindergartenModel extends BaseModel {
    private String name;
    private String address;
    private String cityName;
    private String phoneNumber;
    private String openingTime;
    private String closingTime;
    private String organizationalAffiliation;

    /**
     * Constructor that initializes the kindergarten model with a context.
     * @param context The context used for initialization.
     */
    public KindergartenModel(Context context) {
        super(context);
    }

    /**
     * Constructor that initializes the kindergarten model with given parameters.
     * @param id The ID of the kindergarten.
     * @param name The name of the kindergarten.
     * @param address The address of the kindergarten.
     * @param cityName The city name where the kindergarten is located.
     * @param phoneNumber The phone number of the kindergarten.
     * @param openingTime The opening time of the kindergarten.
     * @param closingTime The closing time of the kindergarten.
     * @param organizationalAffiliation The organizational affiliation of the kindergarten.
     */
    public KindergartenModel(int id, String name, String address, String cityName,
                             String phoneNumber, String openingTime, String closingTime,
                             String organizationalAffiliation) {
        super(id);
        this.name = name;
        this.address = address;
        this.cityName = cityName;
        this.phoneNumber = phoneNumber;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.organizationalAffiliation = organizationalAffiliation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getOrganizationalAffiliation() {
        return organizationalAffiliation;
    }

    public void setOrganizationalAffiliation(String organizationalAffiliation) {
        this.organizationalAffiliation = organizationalAffiliation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KindergartenModel that = (KindergartenModel) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(cityName, that.cityName))
            return false;
        if (!Objects.equals(phoneNumber, that.phoneNumber))
            return false;
        if (!Objects.equals(openingTime, that.openingTime))
            return false;
        if (!Objects.equals(closingTime, that.closingTime))
            return false;
        return Objects.equals(organizationalAffiliation, that.organizationalAffiliation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (openingTime != null ? openingTime.hashCode() : 0);
        result = 31 * result + (closingTime != null ? closingTime.hashCode() : 0);
        result = 31 * result + (organizationalAffiliation != null ? organizationalAffiliation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Kindergarten: " + name + ", City: " + cityName + ", (" + organizationalAffiliation + ")";
    }
}