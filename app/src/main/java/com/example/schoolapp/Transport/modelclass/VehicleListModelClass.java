package com.example.schoolapp.Transport.modelclass;

public class VehicleListModelClass {
    private String ID,VehicleTypeId,VehicleNo,RcNo,WonerName,WonerAddress,PhoneNo;

    public VehicleListModelClass(String id, String vehicleTypeId, String vehicleNo, String rcNo, String wonerName, String wonerAddress, String phoneNo) {
        ID = id;
        VehicleTypeId = vehicleTypeId;
        VehicleNo = vehicleNo;
        RcNo = rcNo;
        WonerName = wonerName;
        WonerAddress = wonerAddress;
        PhoneNo = phoneNo;
    }

    public String getID() {
        return ID;
    }

    public String getVehicleTypeId() {
        return VehicleTypeId;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public String getRcNo() {
        return RcNo;
    }

    public String getWonerName() {
        return WonerName;
    }

    public String getWonerAddress() {
        return WonerAddress;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }
}
