package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @OneToOne
    private Equipment equipment;
    private String type;
    private int nbMaxPlayer;
    private Date realiseDate;
    private String editor;


    public BoardGame() {
    }
    public BoardGame(int _idEquipment, Equipment _equipment, String _type, int _nbMaxPlayer, Date _realiseDate, String _editor) {

        idEquipment = _idEquipment;
        equipment = _equipment;
        type = _type;
        nbMaxPlayer = _nbMaxPlayer;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public Equipment getEquipment() { return equipment; }
    public String getType() { return type; }
    public int getNbMaxPlayer() { return nbMaxPlayer; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setType(String type) { this.type = type; }
    public void setNbMaxPlayer(int nbMaxPlayer) { this.nbMaxPlayer = nbMaxPlayer; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }
}
