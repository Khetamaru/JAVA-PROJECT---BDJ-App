package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Historic {

    protected int idHistoric;
    protected int idUser;
    protected String origin;
    protected String action;
    protected Date date;


    public int getIdHistoric() {
        return idHistoric;
    }
    public int getIdUser() {
        return idUser;
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
    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
