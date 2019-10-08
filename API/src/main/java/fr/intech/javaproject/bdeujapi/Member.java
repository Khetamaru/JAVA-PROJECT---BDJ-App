package fr.intech.javaproject.bdeujapi;

import java.util.Date;

public class Member {

    private int idMember;
    private User user;
    private String place;
    private Date startDate;
    private Date endDate;


    public Member(int _idMember, User _user, String _place, Date _startDate, Date _endDate) {

        idMember = _idMember;
        user = _user;
        place = _place;
        startDate = _startDate;
        endDate = _endDate;
    }


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