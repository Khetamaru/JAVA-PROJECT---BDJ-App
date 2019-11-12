package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Equipment {

    protected int idEquipment;
    private String name;
    private String status;
    private Date dateRecup;
    private String state;
    private String origin;
    private String cfDoc;
    private String ableToBorrow;


    public int getIdEquipment() {
        return idEquipment;
    }
    public String getName() {
        return name;
    }
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
}
