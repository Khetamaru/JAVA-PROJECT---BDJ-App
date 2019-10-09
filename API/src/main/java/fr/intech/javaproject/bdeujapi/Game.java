package fr.intech.javaproject.bdeujapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idGame;
    private String name;
    private String state;


    public Game() {
    }
    public Game(int _idGame, String _name, String _state) {

        idGame = _idGame;
        name = _name;
        state = _state;
    }


    public int getIdGame() {
        return idGame;
    }
    public String getName() {
        return name;
    }
    public String getState() {
        return state;
    }


    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }
}
