package com.example.pushuptrecker.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserTrainingID{
    @Column(name="id_user")
    public int idUser;
    @Column(name="id_training")
    public int idTraining;

    public UserTrainingID() {}

    public UserTrainingID(int idUser, int idTraining) {
        this.idUser = idUser;
        this.idTraining = idTraining;
    }
    // equals, hashCode

}