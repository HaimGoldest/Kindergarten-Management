package com.example.kindergarten_management.helpers;

import com.example.kindergarten_management.enums.StaffRules;
import com.example.kindergarten_management.models.StaffMemberModel;

import java.util.ArrayList;

public class TempDataHelper {

    public static ArrayList<StaffMemberModel> getStaffList(){
        ArrayList<StaffMemberModel> staff = new ArrayList<>();
        staff.add(new StaffMemberModel(1, "Name 1", StaffRules.Teacher, "1.1.2024", null, null));
        staff.add(new StaffMemberModel(2, "Name 2", StaffRules.Teacher, "1.1.2024", null, null));
        staff.add(new StaffMemberModel(3, "Name 3", StaffRules.Assistant, "1.1.2024", null, null));
        staff.add(new StaffMemberModel(4, "Name 4", StaffRules.Assistant, "1.1.2024", null, null));

        return staff;
    }

}
