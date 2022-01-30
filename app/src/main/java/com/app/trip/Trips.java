package com.app.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "triptable")
public class Trips {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userID;
    private String startPoint;
    private String endPoint;
    private double startpointLat;
    private double startpointLan;
    private double endpointLat;
    private  double endpointLan;
    private String tripName;
    private String date;
    private String time;
    private String from;
    private String to;
    private String type ;
    ///why !!!!!!!!!!!!!!!
    public String notes;
    private String tripStatus;
    private long calendar;


    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Trips() {
    }

    public Trips(String userID,String startPoint, String endPoint, double startpointLat, double startpointLan, double endpointLat, double endpointLan, String tripName, String date, String time, String from, String to, String type, String notes,String tripStatus,long calendar) {
        this.userID=userID;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startpointLat = startpointLat;
        this.startpointLan = startpointLan;
        this.endpointLat = endpointLat;
        this.endpointLan = endpointLan;
        this.tripName = tripName;
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
        this.type = type;
        this.notes =notes;
        this.tripStatus=tripStatus;
        this.calendar=calendar;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getCalendar() {
        return calendar;
    }

    public void setCalendar(long calendar) {
        this.calendar = calendar;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public double getStartpointLat() {
        return startpointLat;
    }

    public void setStartpointLat(double startpointLat) {
        this.startpointLat = startpointLat;
    }

    public double getStartpointLan() {
        return startpointLan;
    }

    public void setStartpointLan(double startpointLan) {
        this.startpointLan = startpointLan;
    }

    public double getEndpointLat() {
        return endpointLat;
    }

    public void setEndpointLat(double endpointLat) {
        this.endpointLat = endpointLat;
    }

    public double getEndpointLan() {
        return endpointLan;
    }

    public void setEndpointLan(double endpointLan) {
        this.endpointLan = endpointLan;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes =notes;
    }
}
