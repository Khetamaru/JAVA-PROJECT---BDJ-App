package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class Other extends Equipment {

    private String type;


    public Other() {

    }
    public Other(int _idGame, String _name, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
                 String _type) {

        super(_idGame, _name, _type, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        type = _type;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }


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
                "\"type\" : \"" + getType() + "\"" +
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
                "\"type\" : \"" + getType() + "\"" +
                "}";
    }
}