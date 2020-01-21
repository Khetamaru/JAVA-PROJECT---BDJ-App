package com.example.bdjcrusadeinternalappli;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {

    protected int idEquipment;
    private String name;
    private String className;
    private String status;
    private Date dateRecup;
    private String state;
    private String origin;
    private String cfDoc;
    private String ableToBorrow;


    public Equipment() {
    }
    public Equipment(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow) {

        idEquipment = _idGame;
        name = _name;
        className = _className;
        status = _status;
        dateRecup = _dateRecup;
        state = _state;
        origin = _origin;
        cfDoc = _cfDoc;
        ableToBorrow = _ableToBorrow;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public String getName() {
        return name;
    }
    public String getClassName() { return className; }
    public String getState() {
        return state;
    }
    public String getOrigin() { return origin; }
    public Date getDateRecup() { return dateRecup; }
    public String getAbleToBorrow() { return ableToBorrow; }
    public String getCfDoc() { return cfDoc; }
    public String getStatus() { return status; }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setClassName(String className) { this.className = className; }
    public void setState(String state) {
        this.state = state;
    }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setAbleToBorrow(String ableToBorrow) { this.ableToBorrow = ableToBorrow; }
    public void setCfDoc(String cfDoc) { this.cfDoc = cfDoc; }
    public void setDateRecup(Date dateRecup) { this.dateRecup = dateRecup; }
    public void setStatus(String status) { this.status = status; }


    public String toString () {

        return  "{" +
                    "\"idEquipment\" : " + getIdEquipment() + "," +
                    "\"name\" : \"" + getName() + "\"," +
                    "\"state\" : \"" + getState() + "\"," +
                    "\"origin\" : \"" + getOrigin() + "\"," +
                    "\"ableToBorrow\" : \"" + getAbleToBorrow() + "\"," +
                    "\"cfDoc\" : \"" + getCfDoc() + "\"," +
                    "\"dateRecup\" : " + getDateRecup().getTime() + "," +
                    "\"status\" : \"" + getStatus() + "\"" +
                "}";
    }

    public String toStringWithoutId () {

        return  "{" +
                "\"name\" : \"" + getName() + "\"," +
                "\"state\" : \"" + getState() + "\"," +
                "\"origin\" : \"" + getOrigin() + "\"," +
                "\"ableToBorrow\" : \"" + getAbleToBorrow() + "\"," +
                "\"cfDoc\" : \"" + getCfDoc() + "\"," +
                "\"dateRecup\" : " + getDateRecup().getTime() + "," +
                "\"status\" : \"" + getStatus() + "\"" +
                "}";
    }

    public Equipment extarct() {

        return Equipment.this;
    }

    public String allStatesCheck() {

        if (nameCheck()) {

            return "Name is empty or too long (Max 25)";
        }
        if (statusCheck()) {

            return "Status is empty or too long (Max 25)";
        }
        if (recupDateCheck()) {

            return "You have to pick a Recuperation Date";
        }
        if (stateCheck()) {

            return "State is empty or too long (Max 100)";
        }
        if (originCheck()) {

            return "Origin is empty or too long (Max 25)";
        }
        if (cfDocCheck()) {

            return "cfDoc is too long (Max 50)";
        }

        return "";
    }

    public boolean nameCheck() {

        if (name != null && name.length() < 25) {

            return false;
        }
        return true;
    }

    public boolean statusCheck() {

        if (status != null && status.length() < 25) {

            return false;
        }
        return true;
    }

    public boolean recupDateCheck() {

        if (dateRecup != null) {

            return false;
        }
        return true;
    }

    public boolean stateCheck() {

        if (state != null && state.length() < 100) {

            return false;
        }
        return true;
    }

    public boolean originCheck() {

        if (origin != null && origin.length() < 25) {

            return false;
        }
        return true;
    }

    public boolean cfDocCheck() {

        if (name != null && name.length() < 50) {

            return false;
        }
        return true;
    }
}
