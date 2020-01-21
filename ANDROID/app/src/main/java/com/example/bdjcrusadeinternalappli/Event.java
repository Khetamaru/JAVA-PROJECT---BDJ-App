package com.example.bdjcrusadeinternalappli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.Date;

public class Event {

    private int idEvent;
    private int[] loans;
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;
    protected Time startHour;
    protected Time endHour;
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


    public String toString () {

        return  "{" +
                    "\"idEvent\" : " + getIdEvent() + "," +
                    "\"loans\" : [ " + loansToString() + " ], " +
                    "\"name\" : \"" + getName() + "\"," +
                    "\"place\" : \"" + getPlace() + "\"," +
                    "\"startDate\" : " + getStartDate().getTime() + "," +
                    "\"endDate\" : " + getEndDate().getTime() + "," +
                    "\"startHour\" : \"" + getStartHour() + "\"," +
                    "\"endHour\" : \"" + getEndHour() + "\"," +
                    "\"team\" : " + getTeam() +
                "}";
    }

    public String toStringWithoutId () {

        return  "{" +
                    "\"loans\" : [ " + loansToString() + " ], " +
                    "\"name\" : \"" + getName() + "\"," +
                    "\"place\" : \"" + getPlace() + "\"," +
                    "\"startDate\" : " + getStartDate().getTime() + "," +
                    "\"endDate\" : " + getEndDate().getTime() + "," +
                    "\"startHour\" : \"" + getStartHour() + "\"," +
                    "\"endHour\" : \"" + getEndHour() + "\"," +
                    "\"team\" : " + getTeam() +
                "}";
    }

    public String loansToString() {

        String loansString = "";

        for (int i = 0; i < loans.length; i++) {

            loansString = loansString  + loans[i];

            if (i != loans.length - 1) {

                loansString = loansString + ", ";
            }
        }

        return loansString;
    }

    public String allStatesCheck() {

        if (nameCheck()) {

            return "Name is empty or too long (Max 25)";
        }
        if(loansCheck()) {

            return "You have to pick a Loan";
        }
        if(placeCheck()) {

            return "You have to pick a Place";
        }
        if(startDateCheck()) {

            return "You have to pick a Start Date";
        }
        if(endDateCheck()) {

            return "You have to pick a End Date";
        }
        if(startHourCheck()) {

            return "You have to pick a Start Hour";
        }
        if(endHourCheck()) {

            return "You have to pick a End Hour";
        }
        if(teamCheck()) {

            return "You have to pick a Team";
        }

        return "";
    }

    public boolean nameCheck() {

        if(name != null && name.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean loansCheck() {

        if(loans != null) {

            return false;
        }

        return true;
    }

    public boolean startDateCheck() {

        if(startDate != null) {

            return false;
        }

        return true;
    }

    public boolean endDateCheck() {

        if(endDate != null) {

            return false;
        }

        return true;
    }

    public boolean startHourCheck() {

        if(startHour != null) {

            return false;
        }

        return true;
    }

    public boolean endHourCheck() {

        if(endHour != null) {

            return false;
        }

        return true;
    }

    public boolean placeCheck() {

        if(place != null) {

            return false;
        }

        return true;
    }

    public boolean teamCheck() {

        if(team != null) {

            return false;
        }

        return true;
    }
}
