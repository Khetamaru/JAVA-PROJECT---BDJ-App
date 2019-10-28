package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Location {

    protected int idMember;
    protected User user;
    protected String place;
    protected Date startDate;
    protected Date endDate;


    public int getIdMember() {
        return idMember;
    }
    public User getUser() {
        return user;
    }
    public String getPlace() {
        return place;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }


    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }
    public void setIdUser(User user) {
        this.user = user;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}