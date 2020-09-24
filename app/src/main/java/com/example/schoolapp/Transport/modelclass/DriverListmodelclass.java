package com.example.schoolapp.Transport.modelclass;

public class DriverListmodelclass {

    private String ID;
    private String DriverName;
    private String Email;
    private String Phone;
    private String Address;
    private String ImagePath;
    private String UpdatedBy;
    private String CreatedDate;
    private String CreatedBy;
    private String UpadatedDate;
    private String LicenceNo;
    private String LicenceValideDate;
    private String LicenceType;


    public DriverListmodelclass(String id, String driverName, String email, String phone, String address, String imagePath, String updatedBy, String createdDate, String createdBy, String upadatedDate, String licenceNo, String licenceValideDate, String licenceType) {
        ID = id;
        DriverName = driverName;
        Email = email;
        Phone = phone;
        Address = address;
        ImagePath = imagePath;
        UpdatedBy = updatedBy;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        UpadatedDate = upadatedDate;
        LicenceNo = licenceNo;
        LicenceValideDate = licenceValideDate;
        LicenceType = licenceType;
    }

    public String getID() {
        return ID;
    }

    public String getDriverName() {
        return DriverName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public String getUpadatedDate() {
        return UpadatedDate;
    }

    public String getLicenceNo() {
        return LicenceNo;
    }

    public String getLicenceValideDate() {
        return LicenceValideDate;
    }

    public String getLicenceType() {
        return LicenceType;
    }
}
