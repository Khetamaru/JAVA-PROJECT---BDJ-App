package fr.intech.javaproject.bdeujapi;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTeam;
    private int[] users;
    private String name;

    public Team() {

    }
    public Team(int _idTeam, int[] _users, String _name) {

        idTeam = _idTeam;
        users = _users;
        name = _name;
    }


    public int getIdTeam() { return idTeam; }
    public int[] getUsers() { return users; }
    public String getName() { return name; }


    public void setIdTeam(int idTeam) { this.idTeam = idTeam; }
    public void setUsers(int[] users) { this.users = users; }
    public void setName(String name) { this.name = name; }
}
