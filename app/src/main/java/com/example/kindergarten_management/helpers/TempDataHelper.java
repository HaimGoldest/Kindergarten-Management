package com.example.kindergarten_management.helpers;

import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.ArrayList;
import java.util.List;

public class TempDataHelper {

    public static ArrayList<StaffMemberModel> getStaffList(){
        ArrayList<StaffMemberModel> staff = new ArrayList<>();
        staff.add(new StaffMemberModel(0, "Name 1", "Teacher", "1.1.2024", 0, 0));
        staff.add(new StaffMemberModel(1, "Name 2", "Teacher", "1.1.2024", 1, 1));
        staff.add(new StaffMemberModel(2, "Name 3", "Assistant", "1.1.2024", 0, 0));
        staff.add(new StaffMemberModel(3, "Name 4", "Assistant", "1.1.2024", 1, 1));
        staff.add(new StaffMemberModel(4, "update user", "Assistant", "1.11.2023", 2, 5));

        return staff;
    }

    public static ArrayList<ClassModel> getClassesList(){
        int kindergartenId = 0;
        ArrayList<ClassModel> list = new ArrayList<>();
        list.add(new ClassModel(0, "Sport", 20, 11, 8, kindergartenId));
        list.add(new ClassModel(1, "Sport", 30, 12, 8, kindergartenId));
        list.add(new ClassModel(2, "Music", 40, 13, 8, kindergartenId));
        list.add(new ClassModel(3, "Music", 50, 14, 8, kindergartenId));
        list.add(new ClassModel(4, "Sport", 20, 11, 8, kindergartenId));
        list.add(new ClassModel(5, "Music", 50, 14, 8, kindergartenId));
        list.add(new ClassModel(6, "Sport", 20, 11, 8, kindergartenId));

        return list;
    }

    public static ArrayList<KindergartenModel> getKindergartenList(){
        ArrayList<KindergartenModel> list = new ArrayList<>();
        list.add(new KindergartenModel(0,"Gan_1", "address_1", "city_1", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(1,"Gan_2", "address_2", "city_2", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(2,"Gan_3", "address_3", "city_3", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(3,"Gan_4", "address_4", "city_4", "050-1234", "08:00", "16:00", "Normal"));

        return list;
    }

    public static List<ClassModel> getClassesByKindergarten(KindergartenModel kindergarten) {
        int kindergartenId = kindergarten.getId();
        ArrayList<ClassModel> list = new ArrayList<>();

        switch (kindergarten.getId()) {
            case 0:
                list.add(new ClassModel(0, "Sport", 20, 11, 8, kindergartenId));
                list.add(new ClassModel(1, "Sport", 30, 12, 8, kindergartenId));
                list.add(new ClassModel(2, "Music", 40, 13, 8, kindergartenId));
                list.add(new ClassModel(3, "Music", 50, 14, 8, kindergartenId));
                break;
            case 1:
                list.add(new ClassModel(4, "Sport", 20, 11, 8, kindergartenId));
                list.add(new ClassModel(5, "Music", 50, 14, 8, kindergartenId));
                break;
            case 2:
                list.add(new ClassModel(6, "Sport", 20, 11, 8, kindergartenId));
                break;
            default:
                break;
        }

        return list;
    }

    public static KindergartenModel getKindergarten(int kindergartenId) {
        for (KindergartenModel k: getKindergartenList()) {
            if(k.getId() == kindergartenId)
                return k;
        }

        return null;
    }

    public static ClassModel getClassModel(int classId) {
        for (ClassModel c: getClassesList()) {
            if(c.getId() == classId)
                return c;
        }

        return null;
    }

    public static StaffMemberModel getStaffMember(int staffMemberId) {
        for (StaffMemberModel item : getStaffList()) {
            if(item.getId() == staffMemberId)
                return item;
        }

        return null;
    }

    public static KindergartenModel getKindergartenByName(String kindergartenName) {
        for (KindergartenModel item : getKindergartenList()) {
            if(item.getName().equals(kindergartenName))
                return item;
        }

        return null;
    }
}