package com.app.trip;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Trip Model  values Data
//Bogo
public class Trip {

    int id;
    String tripName;
    String startPoint;
    String endPoint;
    String time;
    String date;
    String tripType;


    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
