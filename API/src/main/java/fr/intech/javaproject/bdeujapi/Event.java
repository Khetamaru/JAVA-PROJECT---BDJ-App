package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvent;
    private int[] loans;
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;
    protected Time startHour;
    protected Time endHour;
    @OneToOne
    private Team team;

    public Event() {

    }
    public Event(int _idEvent, int[] _loans, String _name, String _place, Date _startDate, Date _endDate, Time _startHour, Time _endHour, Team _team) {

        idEvent = _idEvent;
        loans = _loans;
        name = _name;
        place = _place;
        startDate = _startDate;
        endDate = _endDate;
        startHour = _startHour;
        endHour = _endHour;
        team = _team;
    }


    public int getIdEvent() { return idEvent; }
    public int[] getLoans() { return loans; }
    public String getName() { return name; }
    public String getPlace() { return place; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public Time getStartHour() { return startHour; }
    public Time getEndHour() { return endHour; }
    public Team getTeam() { return team; }


    public void setIdEvent(int idEvent) { this.idEvent = idEvent; }
    public void setLoans(int[] loans) { this.loans = loans; }
    public void setName(String name) { this.name = name; }
    public void setPlace(String place) { this.place = place; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setStartHour(Time startHour) { this.startHour = startHour; }
    public void setEndHour(Time endHour) { this.endHour = endHour; }
    public void setTeam(Team team) { this.team = team; }
}
