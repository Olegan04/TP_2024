package com.example.pushuptrecker.models;

import androidx.annotation.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name="id_user")
    private int id;
    @Column(name = "name_user", nullable=false, length=254)
    private String name;
    @Column(name ="posvord", nullable=false,length=254)
    private String password;
    @Column(name ="gender", nullable=false,length=10)
    private String gender;
    @Column(name ="binding", length=254)
    private String binding;
    @Column(name ="country", nullable=false,length=254)
    private String country;
    @Column(name ="location", nullable=false,length=254)
    private String location;
    @Column(name ="date_of_birth", nullable=false)
    private Date date;
    @Column(name ="facebook")
    private String facebook;
    @Column(name ="instagram")
    private String instagram;
    @Column(name ="telegram")
    private String telegram;
    @Column(name ="tiktok")
    private String tiktok;
    @Column(name ="vk")
    private String vk;
    @Column(name ="youtube")
    private String youtube;
    @Column(name ="quantity_of_day")
    private int days;

    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL)
    private List<PushUp> pushUps;

    public User() {
    }

    public User(String binding, String password, String name, Date date, String gender, String country, String location) {
        this.binding = binding;
        this.password = password;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.country = country;
        this.location = location;
        this.days = 0;
        this.pushUps.add(new PushUp(this.id, 1));
    }
    public void changeBinding(String binding){this.binding = binding;}
    public void changePassword(String password){this.password = password;}
    public void changeName(String name){this.name = name;}
    public void changeCountry(String country){this.country = country;}
    public void changeLocation(String location){this.location = location;}
    public void changeFacebook(String link){
        this.facebook = link;
    }
    public void changeInstagram(String link){
        this.instagram = link;
    }
    public void changeTelegram(String link){
        this.telegram = link;
    }
    public void changeTikTok(String link){
        this.tiktok = link;
    }
    public void changeVk(String link){
        this.vk = link;
    }
    public void changeYoutube(String link){
        this.youtube = link;
    }
    public void changeDays(boolean day){
        if(day) this.days += 1;
        else this.days = 0;
    }

    public String getFacebook(){
        return this.facebook;
    }
    public String getInstagram(){
        return this.instagram;
    }
    public String getTelegram(){
        return this.telegram;
    }
    public String getTikTok(){
        return this.tiktok;
    }
    public String getVk(){
        return this.vk;
    }
    public String getYoutube(){
        return this.youtube;
    }
    public int getDays(){
        return this.days;
    }
    @NonNull
    @Override
    public String toString() {
        return "models.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
