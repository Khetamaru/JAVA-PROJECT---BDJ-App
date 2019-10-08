package com.example.bdjcrusadeinternalappli;

public class User {

    protected int idUser;
    protected String pseudo;
    protected String login;
    protected String password;
    protected String mail;


    public int getIdUser() {
        return idUser;
    }
    public String getPseudo() {
        return pseudo;
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


    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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
}
