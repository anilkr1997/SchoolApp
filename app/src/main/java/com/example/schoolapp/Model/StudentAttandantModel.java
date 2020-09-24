package com.example.schoolapp.Model;

public class StudentAttandantModel {
    String name;
    boolean present;

    public String getName() {
        return name;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isAbsent() {
        return absent;
    }

    boolean absent ;

    public StudentAttandantModel(String name, boolean present, boolean absent) {
        this.name = name;
        this.present = present;
        this.absent = absent;
    }
}
