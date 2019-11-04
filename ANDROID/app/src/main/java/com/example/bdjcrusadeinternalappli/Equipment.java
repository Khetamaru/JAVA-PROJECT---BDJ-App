package com.example.bdjcrusadeinternalappli;

public class Equipment {

    protected int idEquipment;
    protected String name;
    protected String state;


    public int getIdEquipment() {
        return idEquipment;
    }
    public String getName() {
        return name;
    }
    public String getState() {
        return state;
    }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String toString () {

        return  "{" +
                    "\"idEquipment\" : " + getIdEquipment() + "," +
                    "\"name\" : \"" + getName() + "\"," +
                    "\"state\" : \"" + getState() + "\"" +
                "}";
    }
}
