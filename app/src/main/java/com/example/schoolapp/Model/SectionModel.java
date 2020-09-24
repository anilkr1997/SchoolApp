package com.example.schoolapp.Model;

public class SectionModel {
    String ID;

    public SectionModel(String ID, String sectionName, boolean isActive, boolean isDeleted) {
        this.ID = ID;
        SectionName = sectionName;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }

    String SectionName;

    public String getID() {
        return ID;
    }

    public String getSectionName() {
        return SectionName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    boolean IsActive;
    boolean IsDeleted;
}
