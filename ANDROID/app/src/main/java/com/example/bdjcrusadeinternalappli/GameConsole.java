package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class GameConsole extends Equipment {

    private int nbMaxController;
    private String videoCable;
    private String powerCable;
    private Date realiseDate;
    private String editor;


    public GameConsole() {

    }
    public GameConsole(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
                       int _nbMaxController, String _videoCable, String _powerCable, Date _realiseDate, String _editor) {

        super(_idGame, _name, _className, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        nbMaxController = _nbMaxController;
        videoCable = _videoCable;
        powerCable = _powerCable;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public int getNbMaxController() { return nbMaxController; }
    public String getVideoCable() { return videoCable; }
    public String getPowerCable() { return powerCable; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setNbMaxController(int nbMaxController) { this.nbMaxController = nbMaxController; }
    public void setVideoCable(String videoCable) { this.videoCable = videoCable; }
    public void setPowerCable(String powerCable) { this.powerCable = powerCable; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }


    public String toString () {

        return  "{" +
                "\"idEquipment\" : " + getIdEquipment() + "," +
                "\"name\" : \"" + getName() + "\"," +
                "\"state\" : \"" + getState() + "\"," +
                "\"origin\" : \"" + getOrigin() + "\"," +
                "\"ableToBorrow\" : \"" + getAbleToBorrow() + "\"," +
                "\"cfDoc\" : \"" + getCfDoc() + "\"," +
                "\"dateRecup\" : " + getDateRecup().getTime() + "," +
                "\"status\" : \"" + getStatus() + "\"," +
                "\"nbMaxController\" : \"" + getNbMaxController() + "\"," +
                "\"videoCable\" : \"" + getVideoCable() + "\"," +
                "\"powerCable\" : \"" + getPowerCable() + "\"," +
                "\"realiseDate\" : \"" + getRealiseDate() + "\"," +
                "\"editor\" : \"" + getEditor() + "\"" +
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
                "\"status\" : \"" + getStatus() + "\"," +
                "\"nbMaxController\" : \"" + getNbMaxController() + "\"," +
                "\"videoCable\" : \"" + getVideoCable() + "\"," +
                "\"powerCable\" : \"" + getPowerCable() + "\"," +
                "\"realiseDate\" : \"" + getRealiseDate() + "\"," +
                "\"editor\" : \"" + getEditor() + "\"" +
                "}";
    }
}