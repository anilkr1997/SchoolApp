package com.example.schoolapp.Model;

public class HouseModel {
    String ID;
    String HouseName;
    boolean IsActive;

    public String getID() {
        return ID;
    }

    public String getHouseName() {
        return HouseName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    boolean IsDeleted;

    public HouseModel(String ID, String houseName, boolean isActive, boolean isDeleted) {
        this.ID = ID;
        HouseName = houseName;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }
}
