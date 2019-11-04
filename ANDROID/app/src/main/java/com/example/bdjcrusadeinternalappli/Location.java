package com.example.bdjcrusadeinternalappli;

import java.sql.Time;
import java.util.Date;

public class Location {

    protected int idLocation;
    protected User user;
    protected String place;
    protected Date date;
    protected Time startHour;
    protected Time endHour;


    public int getIdLocation() {
        return idLocation;
    }
    public User getUser() {
        return user;
    }
    public String getPlace() {
        return place;
    }
    public Date getDate() { return date; }
    public Time getStartHour() { return startHour; }
    public Time getEndHour() { return endHour; }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
    public void setIdUser(User user) {
        this.user = user;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setDate(Date date) { this.date = date; }
    public void setStartHour(Time startHour) { this.startHour = startHour; }
    public void setEndHour(Time endHour) { this.endHour = endHour; }


    public String toString () {

        return  "{" +
                    "\"idLocation\" : " + getIdLocation() + "," +
                    "\"user\" : \"" + getUser().toString() + "\"," +
                    "\"place\" : \"" + getPlace() + "\"" +
                    "\"date\" : " + getDate() + "," +
                    "\"startHour\" : \"" + getStartHour() + "\"," +
                    "\"endHour\" : \"" + getEndHour() + "\"" +
                "}";
    }
}