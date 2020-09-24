package com.example.schoolapp.Model;

public class SessionModel {
    String Id;
    String SessionName;

    public String getId() {
        return Id;
    }

    public String getSessionName() {
        return SessionName;
    }

    public String getIsActive() {
        return IsActive;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    String IsActive;

    public SessionModel(String id, String sessionName, String isActive, String isDeleted) {
        Id = id;
        SessionName = sessionName;
        IsActive = isActive;
        IsDeleted = isDeleted;
    }

    String IsDeleted;
}
