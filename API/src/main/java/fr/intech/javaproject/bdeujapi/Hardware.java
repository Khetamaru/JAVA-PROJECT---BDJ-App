package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class Hardware extends Equipment {

    private String CPU;
    private String RAM;
    private String HDD;
    private String GPU;
    private String OS;


    public Hardware() {

    }
    public Hardware(int _idGame, String _name, String _className, String _status, Date _dateRecup, String _state, String _origin, String _cfDoc, String _ableToBorrow,
        String _CPU, String _RAM, String _HDD, String _GPU, String _OS) {

        super(_idGame, _name, _className, _status, _dateRecup, _state, _origin, _cfDoc, _ableToBorrow);

        CPU = _CPU;
        RAM = _RAM;
        HDD = _HDD;
        GPU = _GPU;
        OS = _OS;
    }


    public String getCPU() { return CPU; }
    public String getRAM() { return RAM; }
    public String getHDD() { return HDD; }
    public String getGPU() { return GPU; }
    public String getOS() { return OS; }


    public void setCPU(String CPU) { this.CPU = CPU; }
    public void setRAM(String RAM) { this.RAM = RAM; }
    public void setHDD(String HDD) { this.HDD = HDD; }
    public void setGPU(String GPU) { this.GPU = GPU; }
    public void setOS(String OS) { this.OS = OS; }
}
