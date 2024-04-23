package com.example.pushuptrecker.models;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "trainings")
public class Training {
    @Id
    @Column(name="id_training")
    private int idTraining;
    @Column(name="name_training", nullable = false)
    private String name;

    public Training(){}
    public Training(String name){
        this.name = name;
    }
    public void changName(String name){
        this.name = name;
    }
    public int getId(){
        return this.idTraining;
    }
    public String getName(){
        return this.name;
    }
}
