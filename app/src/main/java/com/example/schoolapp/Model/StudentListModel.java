package com.example.schoolapp.Model;

import androidx.annotation.NonNull;

public class StudentListModel {
    String ID;
    String name;
    String image;
    String admission;

    public StudentListModel(String ID, String name, String image, String admission, String aClass) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.admission = admission;
        Class = aClass;
    }

    String Class;

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getAdmission() {
        return admission;
    }


    public String getClasss() {
        return Class;
    }
}
