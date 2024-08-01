package com.example.kindergarten_management.models;

import android.content.Context;

import com.example.kindergarten_management.enums.OrganizationalAffiliations;

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
}