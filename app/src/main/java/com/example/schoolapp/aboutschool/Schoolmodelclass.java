package com.example.schoolapp.aboutschool;

public class Schoolmodelclass {
    private String ID,SchoolName,Address,OwnerName,PrincipalName,
            AboutSchool,ImagePath,PhoneNo,
            Email,BoardID,websiteUrl;

    public Schoolmodelclass(String id, String schoolName, String address, String ownerName, String principalName, String aboutSchool, String imagePath, String phoneNo, String email, String boardID, String websiteUrl) {
        ID = id;
        SchoolName = schoolName;
        Address = address;
        OwnerName = ownerName;
        PrincipalName = principalName;
        AboutSchool = aboutSchool;
        ImagePath = imagePath;
        PhoneNo = phoneNo;
        Email = email;
        BoardID = boardID;
        this.websiteUrl = websiteUrl;
    }

    public String getID() {
        return ID;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public String getAddress() {
        return Address;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public String getPrincipalName() {
        return PrincipalName;
    }

    public String getAboutSchool() {
        return AboutSchool;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public String getBoardID() {
        return BoardID;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
}
