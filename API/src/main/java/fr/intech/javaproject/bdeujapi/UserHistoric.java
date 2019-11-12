package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;
@Entity
public class UserHistoric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUserHistoric;
    private String level;
    private String surname;
    private String login;
    private String password;
    private String mail;
    private Date date;


    public UserHistoric() {
    }
    public UserHistoric(int _idHistoric, String _level, String _surname, String _login, String _password, String _mail, Date _date) {

        idUserHistoric = _idHistoric;
        level = _level;
        surname = _surname;
        login = _login;
        password = _password;
        mail = _mail;
        date = _date;
    }


    public int getIdUserHistoric() {
        return idUserHistoric;
    }
    public String getLevel() { return level; }
    public String getSurname() { return surname; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getMail() { return mail; }
    public Date getDate() {
        return date;
    }


    public void setIdUserHistoric(int idUserHistoric) {
        this.idUserHistoric = idUserHistoric;
    }
    public void setLevel(String level) { this.level = level; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setMail(String mail) { this.mail = mail; }
    public void setDate(Date date) {
        this.date = date;
    }
}
