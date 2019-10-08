package fr.intech.javaproject.bdeujapi;

public class Game {

    private int idGame;
    private String name;
    private String state;


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
