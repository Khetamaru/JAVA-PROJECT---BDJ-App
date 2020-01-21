package com.example.bdjcrusadeinternalappli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    private int idTeam;
    private int[] users;
    private String name;

    public Team() {

    }
    public Team(int _idTeam, int[] _users, String _name) {

        idTeam = _idTeam;
        users = _users;
        name = _name;
    }


    public int getIdTeam() { return idTeam; }
    public int[] getUsers() { return users; }
    public String getName() { return name; }


    public void setIdTeam(int idTeam) { this.idTeam = idTeam; }
    public void setUsers(int[] users) { this.users = users; }
    public void setName(String name) { this.name = name; }


    public String toString () {

        return  "{" +
                    "\"idTeam\" : " + getIdTeam() + "," +
                    "\"users\" : [ " + usersToString() + " ], " +
                    "\"name\" : \"" + getName() + "\"" +
                "}";
    }

    public String toStringWithoutId () {

        return  "{" +
                    "\"users\" : [ " + usersToString() + " ], " +
                    "\"name\" : \"" + getName() + "\"" +
                "}";
    }

    public String usersToString() {

        String usersString = "";

        for (int i = 0; i < users.length; i++) {

            usersString = usersString  + users[i];

            if (i != users.length - 1) {

                usersString = usersString + ", ";
            }
        }

        return usersString;
    }

    public String allStatesCheck() {

        if (nameCheck()) {

            return "Name is empty or too long (Max 25)";
        }
        if(usersCheck()) {

            return "Users List can't be empty";
        }

        return "";
    }

    public boolean nameCheck() {

        if(name != null && name.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean usersCheck() {

        if(users != null && users.length > 0) {

            return false;
        }

        return true;
    }
}
