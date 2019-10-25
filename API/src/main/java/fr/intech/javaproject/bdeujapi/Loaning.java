package fr.intech.javaproject.bdeujapi;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Loaning {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBorrow;
    @OneToOne
    private User user;
    @OneToOne
    private Equipment equipment;
    private Date startDate;
    private Date endDate;
    String valid;


    public Loaning() {
    }
    public Loaning(User _user, Equipment _equipment, Date _startDate, Date _endDate, String _valid) {

        user = _user;
        equipment = _equipment;
        startDate = _startDate;
        endDate = _endDate;
        valid = _valid;
    }


    public int getIdBorrow() {
        return idBorrow;
    }
    public User getUser() {
        return user;
    }
    public Equipment getEquipment() {
        return equipment;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public String getValid() { return valid; }


    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setValid(String valid) {
        this.valid = valid;
    }


}
