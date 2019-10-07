package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class User_X_Game {

    protected int idUser;
    protected int idGame;
    protected Date startDate;
    protected Date endDate;


    public int getIdUser() {
        return idUser;
    }
    public int getIdGame() {
        return idGame;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }


    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
