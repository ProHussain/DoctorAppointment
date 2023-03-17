package com.example.doctorappointment.models;

public class AppointmentModel {
    private String userID, drID, drName, time, date,order;

    public AppointmentModel() {

    }

    public AppointmentModel(String userID, String drID, String drName, String time, String date, String order) {
        this.userID = userID;
        this.drID = drID;
        this.drName = drName;
        this.time = time;
        this.date = date;
        this.order = order;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDrID() {
        return drID;
    }

    public void setDrID(String drID) {
        this.drID = drID;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
