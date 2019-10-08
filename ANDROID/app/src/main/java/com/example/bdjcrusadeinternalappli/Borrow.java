package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Borrow {

    protected User user;
    protected Game game;
    protected Date startDate;
    protected Date endDate;


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
