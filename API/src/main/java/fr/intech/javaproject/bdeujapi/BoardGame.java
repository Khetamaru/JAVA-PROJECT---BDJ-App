package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class BoardGame extends Equipment {

    private String type;
    private int nbMaxPlayer;
    private Date realiseDate;
    private String editor;


    public BoardGame() {
    }
    public BoardGame(int _idGame, String _name, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
        int _idEquipment, Equipment _equipment, String _type, int _nbMaxPlayer, Date _realiseDate, String _editor) {

        super(_idGame, _name, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        type = _type;
        nbMaxPlayer = _nbMaxPlayer;
        realiseDate = _realiseDate;
        editor = _editor;
    }


    public String getType() { return type; }
    public int getNbMaxPlayer() { return nbMaxPlayer; }
    public Date getRealiseDate() { return realiseDate; }
    public String getEditor() { return editor; }


    public void setType(String type) { this.type = type; }
    public void setNbMaxPlayer(int nbMaxPlayer) { this.nbMaxPlayer = nbMaxPlayer; }
    public void setRealiseDate(Date realiseDate) { this.realiseDate = realiseDate; }
    public void setEditor(String editor) { this.editor = editor; }
}
