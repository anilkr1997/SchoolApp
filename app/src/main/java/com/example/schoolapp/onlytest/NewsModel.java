package com.example.schoolapp.onlytest;

public class NewsModel {
    String Title;
    String description;

    public String getImagepath() {
        return imagepath;
    }

    String imagepath;

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return description;
    }

    public NewsModel(String title, String description,String imagepath) {
        Title = title;
        this.description = description;
        this.imagepath = imagepath;
    }
}
