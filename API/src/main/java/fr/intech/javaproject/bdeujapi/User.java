package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUser;
    private String surname;
    @Column(unique = true)
    private String login;
    private String password;
    private String mail;
    private String level;


    public User() {
    }
    public User(int _idUser, String _pseudo, String _login, String _password, String _mail, String _level) {

        idUser = _idUser;
        surname = _pseudo;
        login = _login;
        password = _password;
        mail = _mail;
        level = _level;
    }


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
