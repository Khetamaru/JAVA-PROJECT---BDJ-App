package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class Other {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @OneToOne
    private Equipment equipment;
    private String type;


    public Other() {
    }
    public Other(int _idEquipment, Equipment _equipment, String _type) {

        idEquipment = _idEquipment;
        equipment = _equipment;
        type = _type;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public Equipment getEquipment() { return equipment; }
    public String getType() { return type; }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setType(String type) { this.type = type; }
}
