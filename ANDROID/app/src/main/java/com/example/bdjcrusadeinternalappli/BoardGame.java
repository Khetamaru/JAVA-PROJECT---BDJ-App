package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class BoardGame extends Equipment {

    private String type;
    private int nbMaxPlayer;
    private Date realiseDate;
    private String editor;

    public BoardGame() {

    }
    public BoardGame(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow, String _type,
                      int _nbMaxPlayer, Date _realiseDate, String _editor) {

        super(_idGame, _name, _className, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        type = _type;
        nbMaxPlayer = _nbMaxPlayer;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public String getType() { return type; }
    public int getNbMaxPlayer() { return nbMaxPlayer; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setType(String type) { this.type = type; }
    public void setNbMaxPlayer(int nbMaxPlayer) { this.nbMaxPlayer = nbMaxPlayer; }
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
                    "\"type\" : \"" + getType() + "\"," +
                    "\"nbMaxPlayer\" : \"" + getNbMaxPlayer() + "\"," +
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
                    "\"type\" : \"" + getType() + "\"," +
                    "\"nbMaxPlayer\" : \"" + getNbMaxPlayer() + "\"," +
                    "\"realiseDate\" : \"" + getRealiseDate().getTime() + "\"," +
                    "\"editor\" : \"" + getEditor() + "\"" +
                "}";
    }

    public String allOtherStatesCheck() {

        if (typeCheck()) {

            return "type is empty or too long (Max 25)";
        }
        if(nbMaxPlayerCheck()) {

            return "nbMaxPlayer can't be negative";
        }
        if(realiseDateCheck()) {

            return "You have to pick a Realise Date";
        }
        if(editorCheck()) {

            return "Editor is empty or too long (Max 25)";
        }

        return "";
    }

    public boolean typeCheck() {

        if(type != null && type.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean nbMaxPlayerCheck() {

        if(nbMaxPlayer > 0) {

            return false;
        }

        return true;
    }

    public boolean realiseDateCheck() {

        if(realiseDate != null) {

            return false;
        }

        return true;
    }

    public boolean editorCheck() {

        if(editor != null && editor.length() < 25) {

            return false;
        }

        return true;
    }
}