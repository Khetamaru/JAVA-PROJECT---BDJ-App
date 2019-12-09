package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @OneToOne
    private Equipment equipment;
    @OneToOne
    private GameConsole gameConsole;
    private int nbMaxPlayer;
    private String lan;
    private Date realiseDate;
    private String editor;


    public VideoGame() {
    }
    public VideoGame(int _idEquipment, Equipment _equipment, GameConsole _gameConsole, int _nbMaxPlayer, String _lan, Date _realiseDate, String _editor) {

        idEquipment = _idEquipment;
        equipment = _equipment;
        gameConsole = _gameConsole;
        nbMaxPlayer = _nbMaxPlayer;
        lan = _lan;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public int getIdEquipment() { return idEquipment; }
    public Equipment getEquipment() { return equipment; }
    public GameConsole getGameConsole() { return gameConsole; }
    public int getNbMaxPlayer() { return nbMaxPlayer; }
    public String getLan() { return lan; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setIdEquipment(int idEquipment) { this.idEquipment = idEquipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setGameConsole(GameConsole gameConsole) { this.gameConsole = gameConsole; }
    public void setNbMaxPlayer(int nbMaxPlayer) { this.nbMaxPlayer = nbMaxPlayer; }
    public void setLan(String lan) { this.lan = lan; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }
}
