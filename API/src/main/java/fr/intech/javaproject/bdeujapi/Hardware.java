package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipment;
    @OneToOne
    private Equipment equipment;
    private String CPU;
    private String RAM;
    private String HDD;
    private String GPU;
    private String OS;


    public Hardware() {
    }
    public Hardware(int _idEquipment, Equipment _equipment, String _CPU, String _RAM, String _HDD, String _GPU, String _OS) {

        idEquipment = _idEquipment;
        equipment = _equipment;
        CPU = _CPU;
        RAM = _RAM;
        HDD = _HDD;
        GPU = _GPU;
        OS = _OS;
    }


    public int getIdEquipment() {
        return idEquipment;
    }
    public Equipment getEquipment() { return equipment; }
    public String getCPU() { return CPU; }
    public String getRAM() { return RAM; }
    public String getHDD() { return HDD; }
    public String getGPU() { return GPU; }
    public String getOS() { return OS; }


    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setCPU(String CPU) { this.CPU = CPU; }
    public void setRAM(String RAM) { this.RAM = RAM; }
    public void setHDD(String HDD) { this.HDD = HDD; }
    public void setGPU(String GPU) { this.GPU = GPU; }
    public void setOS(String OS) { this.OS = OS; }
}
