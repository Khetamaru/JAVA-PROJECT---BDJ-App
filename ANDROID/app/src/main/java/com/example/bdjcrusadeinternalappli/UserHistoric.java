package com.example.bdjcrusadeinternalappli;

import java.util.Date;

public class UserHistoric {

    protected int idUserHistoric;
    protected String surname;
    protected String login;
    protected String password;
    protected String mail;
    protected String level;
    protected Date date;


    public int getIdUserHistoric() {
        return idUserHistoric;
    }
    public String getSurname() {
        return surname;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getMail() {
        return mail;
    }
    public String getLevel() {
        return level;
    }
    public Date getDate() { return date; }


    public void setIdUserHistoric(int idUserHistoric) {
        this.idUserHistoric = idUserHistoric;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public void setDate(Date date) { this.date = date; }


    public String toString () {

        return  "{" +
                    "\"idUser\" : " + getIdUserHistoric() + "," +
                    "\"surname\" : \"" + getSurname() + "\"," +
                    "\"login\" : \"" + getLogin() + "\"," +
                    "\"password\" : " + getPassword() + "," +
                    "\"mail\" : \"" + getMail() + "\"," +
                    "\"level\" : \"" + getLevel() + "\"," +
                    "\"date\" : \"" + getDate() + "\"" +
                "}";
    }
}
