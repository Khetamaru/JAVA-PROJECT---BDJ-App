package fr.intech.javaproject.bdeujapi;

import java.util.Date;

public class Historic {

    private int idHistoric;
    private User user;
    private String origin;
    private String action;
    private Date date;


    public Historic(int _idHistoric, User _user, String _origin, String _action, Date _date) {

        idHistoric = _idHistoric;
        user = _user;
        origin = _origin;
        action = _action;
        date = _date;
    }


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
