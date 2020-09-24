package com.example.schoolapp.Model;

public class CreateFeeTypeModel {


    public String getID() {
        return ID;
    }

    public String getFeeType1() {
        return FeeType1;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }



    public CreateFeeTypeModel(String ID, String feeType1, boolean isActive, boolean isDeleted) {
        this.ID = ID;
        FeeType1 = feeType1;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }
    String ID;
    String FeeType1;
    boolean IsActive;
    boolean IsDeleted;
}
