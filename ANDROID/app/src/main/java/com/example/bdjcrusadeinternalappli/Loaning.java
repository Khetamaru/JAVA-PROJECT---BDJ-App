package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Loaning {

    protected int idBorrow;
    protected User user;
    protected Equipment equipment;
    protected Date startDate;
    protected Date endDate;

    public Loaning() {

    }

    public Loaning(int _idBorrow, User _user, Equipment _equipment, Date _startDate, Date _endDate) {

        idBorrow = _idBorrow;
        user = _user;
        equipment = _equipment;
        startDate = _startDate;
        endDate = _endDate;
    }

    public int getIdBorrow() { return idBorrow; }
    public User getUser() {
        return user;
    }
    public Equipment getEquipment() {
        return equipment;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }


    public void setIdBorrow(int idBorrow) { this.idBorrow = idBorrow; }
    public void setUser(User user) {
        this.user = user;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
