package com.example.schoolapp.Transport.modelclass;

public class Modeldriverassigne {
    private String Id;
    private String VehicleID;
    private String DriverID;
    private String Isactive;
    private String CreatedOn;
    private String Createdby;
    private String UpdatedOn;
    private String UpdatedBy;

    public Modeldriverassigne(String id, String vehicleID, String driverID, String isactive, String createdOn, String createdby, String updatedOn, String updatedBy) {
        Id = id;
        VehicleID = vehicleID;
        DriverID = driverID;
        Isactive = isactive;
        CreatedOn = createdOn;
        Createdby = createdby;
        UpdatedOn = updatedOn;
        UpdatedBy = updatedBy;
    }

    public String getId() {
        return Id;
    }

    public String getVehicleID() {
        return VehicleID;
    }

    public String getDriverID() {
        return DriverID;
    }

    public String getIsactive() {
        return Isactive;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public String getCreatedby() {
        return Createdby;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }
}
