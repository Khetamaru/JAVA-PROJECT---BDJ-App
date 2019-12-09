package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class GameConsole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @OneToOne
    private Equipment equipment;
    private int nbMaxController;
    private String videoCable;
    private String powerCable;
    private Date realiseDate;
    private String editor;


    public GameConsole() {
    }
    public GameConsole(int _idEquipment, Equipment _equipment, int _nbMaxController, String _videoCable, String _powerCable, Date _realiseDate, String _editor) {

        idEquipment = _idEquipment;
        equipment = _equipment;
        nbMaxController = _nbMaxController;
        videoCable = _videoCable;
        powerCable = _powerCable;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public Equipment getEquipment() { return equipment; }
    public int getNbMaxController() { return nbMaxController; }
    public String getVideoCable() { return videoCable; }
    public String getPowerCable() { return powerCable; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setNbMaxController(int nbMaxController) { this.nbMaxController = nbMaxController; }
    public void setVideoCable(String videoCable) { this.videoCable = videoCable; }
    public void setPowerCable(String powerCable) { this.powerCable = powerCable; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }
}
