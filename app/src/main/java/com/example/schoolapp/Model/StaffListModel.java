package com.example.schoolapp.Model;

public class StaffListModel  {
    String Id,EmpName,FatherName,MotherName,EmpTypeId,Address,Phone,Email,DOJ,ImagePath,Imagebase4,CreatedDate,CreatedBy,UpdatedBy,UpdatedDate;

    public String getId() {
        return Id;
    }

    public String getEmpName() {
        return EmpName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public String getEmpTypeId() {
        return EmpTypeId;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getDOJ() {
        return DOJ;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getImagebase4() {
        return Imagebase4;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public StaffListModel(String id, String empName, String fatherName, String motherName, String empTypeId, String address, String phone, String email, String DOJ, String imagePath, String imagebase4, String createdDate, String createdBy, String updatedBy, String updatedDate) {
        Id = id;
        EmpName = empName;
        FatherName = fatherName;
        MotherName = motherName;
        EmpTypeId = empTypeId;
        Address = address;
        Phone = phone;
        Email = email;
        this.DOJ = DOJ;
        ImagePath = imagePath;
        Imagebase4 = imagebase4;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        UpdatedDate = updatedDate;
    }
}
