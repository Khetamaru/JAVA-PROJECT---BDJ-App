package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class VideoGame extends Equipment {

    private GameConsole gameConsole;
    private int nbMaxPlayer;
    private String lan;
    private Date realiseDate;
    private String editor;


    public VideoGame() {

    }
    public VideoGame(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
                     GameConsole _gameConsole, int _nbMaxPlayer, String _lan, Date _realiseDate, String _editor) {

        super(_idGame, _name, _className, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        gameConsole = _gameConsole;
        nbMaxPlayer = _nbMaxPlayer;
        lan = _lan;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public GameConsole getGameConsole() { return gameConsole; }
    public int getNbMaxPlayer() { return nbMaxPlayer; }
    public String getLan() { return lan; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setGameConsole(GameConsole gameConsole) { this.gameConsole = gameConsole; }
    public void setNbMaxPlayer(int nbMaxPlayer) { this.nbMaxPlayer = nbMaxPlayer; }
    public void setLan(String lan) { this.lan = lan; }
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
                "\"gameConsole\" : " + getGameConsole().toString() + "," +
                "\"nbMaxPlayer\" : \"" + getNbMaxPlayer() + "\"," +
                "\"lan\" : \"" + getLan() + "\"," +
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
                "\"gameConsole\" : " + getGameConsole().toString() + "," +
                "\"nbMaxPlayer\" : \"" + getNbMaxPlayer() + "\"," +
                "\"lan\" : \"" + getLan() + "\"," +
                "\"realiseDate\" : \"" + getRealiseDate() + "\"," +
                "\"editor\" : \"" + getEditor() + "\"" +
                "}";
    }
}