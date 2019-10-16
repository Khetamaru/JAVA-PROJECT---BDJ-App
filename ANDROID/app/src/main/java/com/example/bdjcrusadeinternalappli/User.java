package com.example.bdjcrusadeinternalappli;

public class User {

    protected int idUser;
    protected String surname;
    protected String login;
    protected String password;
    protected String mail;
    protected String level;


    public int getIdUser() {
        return idUser;
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


    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
}
