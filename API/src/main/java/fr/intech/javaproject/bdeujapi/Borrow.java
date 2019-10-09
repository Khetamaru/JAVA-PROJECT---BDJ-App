package fr.intech.javaproject.bdeujapi;

import java.util.Date;

public class Borrow {

    private User user;
    private Game game;
    private Date startDate;
    private Date endDate;


    public Borrow() {
    }
    public Borrow(User _user, Game _game, Date _startDate, Date _endDate) {

        user = _user;
        game = _game;
        startDate = _startDate;
        endDate = _endDate;
    }


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
