package com.example.schoolapp.Model;

public class FeeStructureModel {
    String ID;
    String ClassId;
    String FeeTypeID;
    String SessionId;
    String Anually;
    String Course_Monthly;

    public String getID() {
        return ID;
    }

    public String getClassId() {
        return ClassId;
    }

    public String getFeeTypeID() {
        return FeeTypeID;
    }

    public String getSessionId() {
        return SessionId;
    }

    public String getAnually() {
        return Anually;
    }

    public String getCourse_Monthly() {
        return Course_Monthly;
    }

    public String getQuaterly() {
        return Quaterly;
    }

    String Quaterly;

    public FeeStructureModel(String ID, String classId, String feeTypeID, String sessionId, String anually, String course_Monthly, String quaterly) {
        this.ID = ID;
        ClassId = classId;
        FeeTypeID = feeTypeID;
        SessionId = sessionId;
        Anually = anually;
        Course_Monthly = course_Monthly;
        Quaterly = quaterly;
    }
}
