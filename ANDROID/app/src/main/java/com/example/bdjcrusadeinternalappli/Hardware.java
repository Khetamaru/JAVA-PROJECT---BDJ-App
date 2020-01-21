package com.example.bdjcrusadeinternalappli;

import java.util.Date;

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


    public String toString () {

        return  "{" +
                "\"idEquipment\" : " + getIdEquipment() + "," +
                "\"name\" : \"" + getName() + "\"," +
                "\"state\" : \"" + getState() + "\"," +
                "\"origin\" : \"" + getOrigin() + "\"," +
                "\"ableToBorrow\" : \"" + getAbleToBorrow() + "\"," +
                "\"cfDoc\" : \"" + getCfDoc() + "\"," +
                "\"dateRecup\" : " + getDateRecup().getTime() + "," +
                "\"status\" : \"" + getStatus() + "\"," +
                "\"cpu\" : \"" + getCPU() + "\"," +
                "\"ram\" : \"" + getRAM() + "\"," +
                "\"hdd\" : \"" + getHDD() + "\"," +
                "\"gpu\" : \"" + getGPU() + "\"," +
                "\"os\" : \"" + getOS() + "\"" +
                "}";
    }

    public String toStringWithoutId () {

        return  "{" +
                "\"name\" : \"" + getName() + "\"," +
                "\"state\" : \"" + getState() + "\"," +
                "\"origin\" : \"" + getOrigin() + "\"," +
                "\"ableToBorrow\" : \"" + getAbleToBorrow() + "\"," +
                "\"cfDoc\" : \"" + getCfDoc() + "\"," +
                "\"dateRecup\" : " + getDateRecup().getTime() + "," +
                "\"status\" : \"" + getStatus() + "\"," +
                "\"cpu\" : \"" + getCPU() + "\"," +
                "\"ram\" : \"" + getRAM() + "\"," +
                "\"hdd\" : \"" + getHDD() + "\"," +
                "\"gpu\" : \"" + getGPU() + "\"," +
                "\"os\" : \"" + getOS() + "\"" +
                "}";
    }

    public String allOtherStatesCheck() {

        if (cpuCheck()) {

            return "CPU is empty or too long (Max 25)";
        }
        if(ramCheck()) {

            return "RAM is empty or too long (Max 25)";
        }
        if(hddCheck()) {

            return "HDD is empty or too long (Max 25)";
        }
        if(gpuCheck()) {

            return "GPU is empty or too long (Max 25)";
        }
        if(osCheck()) {

            return "OS is empty or too long (Max 25)";
        }

        return "";
    }

    public boolean cpuCheck() {

        if(CPU != null && CPU.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean ramCheck() {

        if(RAM != null && RAM.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean hddCheck() {

        if(HDD != null && HDD.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean gpuCheck() {

        if(GPU != null && GPU.length() < 25) {

            return false;
        }

        return true;
    }

    public boolean osCheck() {

        if(OS != null && OS.length() < 25) {

            return false;
        }

        return true;
    }
}
