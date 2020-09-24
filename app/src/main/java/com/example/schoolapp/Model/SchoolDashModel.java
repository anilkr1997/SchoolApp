package com.example.schoolapp.Model;




public class SchoolDashModel {
    String schooltitle;
    int image;

    public SchoolDashModel(String schooltitle,
            int image ){
        this.schooltitle=schooltitle;
        this.image=image;
    }

    public String getSchooltitle() {
        return schooltitle;
    }

    public int getImage() {
        return image;
    }
}
