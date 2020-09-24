package com.example.schoolapp.Transport.modelclass;

public class RoutModerlclass {
    private int id;
    private String BusNumber;
    private String Drivername;
    private String DriverNumber;
    private String to;
    private String from;
    public RoutModerlclass(int id, String busNumber, String drivername, String driverNumber, String to, String from) {
        this.id = id;
        BusNumber = busNumber;
        Drivername = drivername;
        DriverNumber = driverNumber;
        this.to = to;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public String getDrivername() {
        return Drivername;
    }

    public void setDrivername(String drivername) {
        Drivername = drivername;
    }

    public String getDriverNumber() {
        return DriverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        DriverNumber = driverNumber;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
