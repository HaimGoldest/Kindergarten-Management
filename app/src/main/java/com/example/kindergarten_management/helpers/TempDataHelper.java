package com.example.kindergarten_management.helpers;

import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.ArrayList;
import java.util.List;

public class TempDataHelper {

    public static ArrayList<StaffMemberModel> getStaffList(){
        KindergartenModel kindergarten = getKindergartenList().get(0);
        ClassModel classModel = getClassesList().get(0);

        ArrayList<StaffMemberModel> staff = new ArrayList<>();
        staff.add(new StaffMemberModel(1, "Name 1", "Teacher", "1.1.2024", kindergarten, classModel));
        staff.add(new StaffMemberModel(2, "Name 2", "Teacher", "1.1.2024", kindergarten, classModel));
        staff.add(new StaffMemberModel(3, "Name 3", "Assistant", "1.1.2024", kindergarten, classModel));
        staff.add(new StaffMemberModel(4, "Name 4", "Assistant", "1.1.2024", kindergarten, classModel));

        return staff;
    }

    public static ArrayList<ClassModel> getClassesList(){
        KindergartenModel kindergarten = getKindergartenList().get(0);

        ArrayList<ClassModel> list = new ArrayList<>();
        list.add(new ClassModel(1, "Sport", 20, 11, 8, kindergarten));
        list.add(new ClassModel(2, "Sport", 30, 12, 8, kindergarten));
        list.add(new ClassModel(3, "Music", 40, 13, 8, kindergarten));
        list.add(new ClassModel(4, "Music", 50, 14, 8, kindergarten));

        return list;
    }

    public static ArrayList<KindergartenModel> getKindergartenList(){
        ArrayList<KindergartenModel> list = new ArrayList<>();
        list.add(new KindergartenModel(1,"Gan_1", "address_1", "city_1", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(2,"Gan_2", "address_2", "city_2", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(3,"Gan_3", "address_3", "city_3", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(4,"Gan_4", "address_4", "city_4", "050-1234", "08:00", "16:00", "Normal"));
        list.add(new KindergartenModel(5,"Gan_5", "address_5", "city_5", "050-1234", "08:00", "16:00", "Normal"));

        return list;
    }

    public static List<ClassModel> getClassesByKindergarten(KindergartenModel kindergarten) {
        ArrayList<ClassModel> list = new ArrayList<>();

        switch (kindergarten.getId()) {
            case 1:
                list.add(new ClassModel(1, "Sport", 20, 11, 8, kindergarten));
                list.add(new ClassModel(2, "Sport", 30, 12, 8, kindergarten));
                list.add(new ClassModel(3, "Music", 40, 13, 8, kindergarten));
                list.add(new ClassModel(4, "Music", 50, 14, 8, kindergarten));
                break;
            case 2:
                list.add(new ClassModel(5, "Sport", 20, 11, 8, kindergarten));
                list.add(new ClassModel(6, "Music", 50, 14, 8, kindergarten));
                break;
            case 3:
                list.add(new ClassModel(7, "Sport", 20, 11, 8, kindergarten));
                break;
            default:
                break;
        }

        return list;
    }
}
