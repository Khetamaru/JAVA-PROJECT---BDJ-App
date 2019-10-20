package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Borrow {

    protected int idBorrow;
    protected User user;
    protected Game game;
    protected Date startDate;
    protected Date endDate;

    public Borrow() {

    }

    public Borrow(int _idBorrow, User _user, Game _game, Date _startDate, Date _endDate) {

        idBorrow = _idBorrow;
        user = _user;
        game = _game;
        startDate = _startDate;
        endDate = _endDate;
    }

    public int getIdBorrow() { return idBorrow; }
    public User getUser() {
        return user;
    }
    public Game getGame() {
        return game;
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
    public void setGame(Game game) {
        this.game = game;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
