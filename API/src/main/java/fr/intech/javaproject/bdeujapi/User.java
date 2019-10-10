package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUser;

    @JsonProperty("surname")
    private String pseudo;
    private String login;
    private String password;
    private String mail;


    public User() {
    }
    public User(int _idUser, String _pseudo, String _login, String _password, String _mail) {

        idUser = _idUser;
        pseudo = _pseudo;
        login = _login;
        password = _password;
        mail = _mail;
    }


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
