package com.example.schoolapp.Model;

public class GetAllclassFeeModel {
    String ID;
    String ClassId;
    String SessionId;
    String Anually;
    String Course_Monthly;
    String Quaterly;



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getAnually() {
        return Anually;
    }

    public void setAnually(String anually) {
        Anually = anually;
    }

    public String getCourse_Monthly() {
        return Course_Monthly;
    }

    public void setCourse_Monthly(String course_Monthly) {
        Course_Monthly = course_Monthly;
    }

    public String getQuaterly() {
        return Quaterly;
    }

    public void setQuaterly(String quaterly) {
        Quaterly = quaterly;
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

    String IsActive;

    public GetAllclassFeeModel(String ID, String classId, String sessionId, String anually, String course_Monthly, String quaterly, String isActive, String isDeleted) {
        this.ID = ID;
        ClassId = classId;
        SessionId = sessionId;
        Anually = anually;
        Course_Monthly = course_Monthly;
        Quaterly = quaterly;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }

    String IsDeleted;
}

