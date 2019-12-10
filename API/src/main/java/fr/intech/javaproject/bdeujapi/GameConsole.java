package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class GameConsole extends Equipment {

    private int nbMaxController;
    private String videoCable;
    private String powerCable;
    private Date realiseDate;
    private String editor;


    public GameConsole() {
    }
    public GameConsole(int _idGame, String _name, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
        int _idEquipment, Equipment _equipment, int _nbMaxController, String _videoCable, String _powerCable, Date _realiseDate, String _editor) {

        super(_idGame, _name, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        nbMaxController = _nbMaxController;
        videoCable = _videoCable;
        powerCable = _powerCable;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public int getNbMaxController() { return nbMaxController; }
    public String getVideoCable() { return videoCable; }
    public String getPowerCable() { return powerCable; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setNbMaxController(int nbMaxController) { this.nbMaxController = nbMaxController; }
    public void setVideoCable(String videoCable) { this.videoCable = videoCable; }
    public void setPowerCable(String powerCable) { this.powerCable = powerCable; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }
}
