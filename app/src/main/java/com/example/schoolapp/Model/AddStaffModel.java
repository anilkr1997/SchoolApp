package com.example.schoolapp.Model;

public class AddStaffModel {

        String ID;

    public String getID() {
        return ID;
    }

    public String getStaffProfile() {
        return StaffProfile;
    }

    public String getIsActive() {
        return IsActive;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    String StaffProfile;
    String IsActive;
    String IsDeleted;

    public AddStaffModel(String ID, String staffProfile, String isActive, String isDeleted) {
        this.ID = ID;
        StaffProfile = staffProfile;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }


}

