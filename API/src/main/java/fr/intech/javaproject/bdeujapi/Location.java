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
    public Location(int _idMember, User _user, String _place, Date _startDate, Date _endDate) {

        idLocation = _idMember;
        user = _user;
        place = _place;
        startDate = _startDate;
        endDate = _endDate;
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
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }


    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
    public void setIdUser(User user) {
        this.user = user;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}