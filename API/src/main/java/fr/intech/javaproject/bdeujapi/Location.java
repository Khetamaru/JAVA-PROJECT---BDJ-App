package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLocation;
    @OneToOne
    private User user;
    private String place;
    private Date date;
    private Time startHour;
    private Time endHour;


    public Location() {
    }
    public Location(int _idMember, User _user, String _place, Date _date, Time _startHour, Time _endHour) {

        idLocation = _idMember;
        user = _user;
        place = _place;
        date = _date;
        startHour = _startHour;
        endHour = _endHour;
    }


    public int getIdLocation() {
        return idLocation;
    }
    public User getUser() {
        return user;
    }
    public String getPlace() {
        return place;
    }
    public Date getDate() { return date; }
    public Time getStartHour() { return startHour; }
    public Time getEndHour() { return endHour; }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
    public void setUser(User user) { this.user = user; }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setDate(Date date) { this.date = date; }
    public void setStartHour(Time startHour) { this.startHour = startHour; }
    public void setEndHour(Time endHour) { this.endHour = endHour; }
}