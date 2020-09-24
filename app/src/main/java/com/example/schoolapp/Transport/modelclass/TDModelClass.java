package com.example.schoolapp.Transport.modelclass;

public class TDModelClass {
    private int Image;
    private String DasboardName;

    public TDModelClass(int image, String dasboardName) {
        Image = image;
        DasboardName = dasboardName;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getDasboardName() {
        return DasboardName;
    }

    public void setDasboardName(String dasboardName) {
        DasboardName = dasboardName;
    }
}
