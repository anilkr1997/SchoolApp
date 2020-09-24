package com.example.schoolapp.Model;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class StudentUpdatedetails implements Serializable {


    String ID;
    String AdmissionNo;
    String StudentName;
    String FatherName;
    String MotherName;
    String Address;
    String Phone;
    String DOB;
    String OcupetionOfFather;
    String SessionId;
    String RegisterDate;
    String CreatedDate;
    String CreatedBy;
    String UpdatedDate;
    String UpdatedBy;
    String ClassId;
    String BoardId;
    String IsActive;
    String IsDeleted;

    public String getFatherEducation() {
        return FatherEducation;
    }

    public String getMotherOccupation() {
        return MotherOccupation;
    }

    public String getMotherEducation() {
        return MotherEducation;
    }

    public String getPreviousSchoolName() {
        return PreviousSchoolName;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public String getEmail() {
        return Email;
    }

    public String getSibiling() {
        return Sibiling;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public String getAdharCardNo() {
        return AdharCardNo;
    }

    public String getSectionId() {
        return SectionId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    String FatherEducation;
    String MotherOccupation;
    String MotherEducation;
    String PreviousSchoolName;
    String BloodGroup;
    String Email;
    String Sibiling;
    String emergencyContactNo;
    String AdharCardNo;
    String SectionId;

    public StudentUpdatedetails(String ID, String admissionNo, String studentName, String fatherName, String motherName,
                                String address, String phone, String DOB, String ocupetionOfFather, String sessionId,
                                String registerDate, String createdDate, String createdBy, String updatedDate, String updatedBy,
                                String classId, String boardId, String isActive, String isDeleted, String fatherEducation,
                                String motherOccupation, String motherEducation, String previousSchoolName, String bloodGroup,
                                String email, String sibiling, String emergencyContactNo, String adharCardNo, String sectionId,
                                String imagePath) {
        this.ID = ID;
        AdmissionNo = admissionNo;
        StudentName = studentName;
        FatherName = fatherName;
        MotherName = motherName;
        Address = address;
        Phone = phone;
        this.DOB = DOB;
        OcupetionOfFather = ocupetionOfFather;
        SessionId = sessionId;
        RegisterDate = registerDate;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        UpdatedDate = updatedDate;
        UpdatedBy = updatedBy;
        ClassId = classId;
        BoardId = boardId;
        IsActive = isActive;
        IsDeleted = isDeleted;
        FatherEducation = fatherEducation;
        MotherOccupation = motherOccupation;
        MotherEducation = motherEducation;
        PreviousSchoolName = previousSchoolName;
        BloodGroup = bloodGroup;
        Email = email;
        Sibiling = sibiling;
        this.emergencyContactNo = emergencyContactNo;
        AdharCardNo = adharCardNo;
        SectionId = sectionId;
        ImagePath = imagePath;
    }

    String ImagePath;

    public String getID() {
        return ID;
    }



    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        AdmissionNo = admissionNo;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getOcupetionOfFather() {
        return OcupetionOfFather;
    }

    public void setOcupetionOfFather(String ocupetionOfFather) {
        OcupetionOfFather = ocupetionOfFather;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getBoardId() {
        return BoardId;
    }

    public void setBoardId(String boardId) {
        BoardId = boardId;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }
}

