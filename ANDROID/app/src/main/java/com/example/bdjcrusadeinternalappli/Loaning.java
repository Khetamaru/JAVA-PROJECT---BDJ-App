package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Loaning {

    protected int idLoaning;
    protected User user;
    protected Equipment equipment;
    protected Date startDate;
    protected Date endDate;
    protected String validation;

    public Loaning() {

    }

    public Loaning(int _idBorrow, User _user, Equipment _equipment, Date _startDate, Date _endDate, String _validation) {

        idLoaning = _idBorrow;
        user = _user;
        equipment = _equipment;
        startDate = _startDate;
        endDate = _endDate;
        validation = _validation;
    }

    public Loaning(User _user, Equipment _equipment, Date _startDate, Date _endDate, String _validation) {

        user = _user;
        equipment = _equipment;
        startDate = _startDate;
        endDate = _endDate;
        validation = _validation;
    }

    public int getIdLoaning() { return idLoaning; }
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
    public String getValidation() { return validation; }


    public void setIdLoaning(int idLoaning) { this.idLoaning = idLoaning; }
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
    public void setValidation(String validation) { this.validation = validation; }


    public String toString () {

        return  "{" +
                    "\"idLoaning\" : " + getIdLoaning() + "," +
                    "\"user\" : " + getUser().toString() + "," +
                    "\"equipment\" : " + getEquipment().toString() + "," +
                    "\"startDate\" : " + getStartDate().getTime() + "," +
                    "\"endDate\" : " + getEndDate().getTime() + "," +
                    "\"validation\" : \"" + getValidation() + "\"" +
                "}";
    }

    public String toStringWithoutId () {

        return  "{" +
                "\"user\" : " + getUser().toString() + "," +
                "\"equipment\" : " + getEquipment().toString() + "," +
                "\"startDate\" : " + getStartDate().getTime() + "," +
                "\"endDate\" : " + getEndDate().getTime() + "," +
                "\"validation\" : \"" + getValidation() + "\"" +
                "}";
    }
}
