package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class UserHistoric {

    protected int idHistoric;
    protected User user;
    protected String origin;
    protected String action;
    protected Date date;


    public int getIdHistoric() {
        return idHistoric;
    }
    public User getUser() {
        return user;
    }
    public String getOrigin() {
        return origin;
    }
    public String getAction() {
        return action;
    }
    public Date getDate() {
        return date;
    }


    public void setIdHistoric(int idHistoric) {
        this.idHistoric = idHistoric;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
