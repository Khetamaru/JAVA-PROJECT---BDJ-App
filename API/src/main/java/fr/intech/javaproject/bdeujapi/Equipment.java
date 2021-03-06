package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @Column(unique = true)
    private String name;
    private String className;
    private String status;
    private Date dateRecup;
    private String state;
    private String origin;
    private String cfDoc;
    private String ableToBorrow;


    public Equipment() {
    }
    public Equipment(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow) {

        idEquipment = _idGame;
        name = _name;
        className = _className;
        status = _status;
        dateRecup = _dateRecup;
        state = _state;
        origin = _origin;
        cfDoc = _cfDoc;
        ableToBorrow = _ableToBorrow;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public String getName() {
        return name;
    }
    public String getClassName() { return className; }
    public String getStatus() { return status; }
    public Date getDateRecup() { return dateRecup; }
    public String getState() {
        return state;
    }
    public String getOrigin() { return origin; }
    public String getCfDoc() { return cfDoc; }
    public String getAbleToBorrow() { return ableToBorrow; }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setClassName(String className) { this.className = className; }
    public void setStatus(String status) { this.status = status; }
    public void setDateRecup(Date dateRecup) { this.dateRecup = dateRecup; }
    public void setState(String state) {
        this.state = state;
    }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setCfDoc(String cfDoc) { this.cfDoc = cfDoc; }
    public void setAbleToBorrow(String ableToBorrow) { this.ableToBorrow = ableToBorrow; }
}
