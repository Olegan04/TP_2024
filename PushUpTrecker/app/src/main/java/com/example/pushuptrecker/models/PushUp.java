package com.example.pushuptrecker.models;
import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.pushuptrecker.models.*;
@Entity
@Table (name = "push_ups")
public class PushUp{
    @EmbeddedId
    private UserTrainingID id;
    private Double colibration;
    @Column(name = "quantity_per_day")
    private int days;
    @Column(name = "quantity_per_month")
    private int mounths;
    @Column(name = "quantity_per_year")
    private int years;
    @Column(name = "quantity_per_all_time")
    private int all;
    @OneToMany(mappedBy = "idTraining", cascade = CascadeType.ALL)
    private List<Training> trainings;
    public PushUp(){}
    public PushUp(int idUser, int idTraining){
        this.id = new UserTrainingID(idUser, idTraining);
        this.days = 0;
        this.mounths = 0;
        this.years = 0;
        this.all = 0;
    }
}
