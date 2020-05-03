package com.example.getgrarfinalversion;

import android.widget.ImageView;

public class Manager {
    private String name, Phone,numbercar ,Towinglicense ,uid,email;
   // private ImageView pic;
    public Manager (){}
    public Manager(String Fullname, String phone, String email, String Towinglicense,String numbercar ,String uid) {
        this.name = Fullname;
        this.Phone = phone;
        this.email=email;
        this.Towinglicense = Towinglicense;
        this.numbercar = numbercar;
        this.uid=uid;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getNumbercar() {
        return numbercar;
    }

    public void setNumbercar(String email) {
        this.numbercar = numbercar;
    }

    public String getTowinglicense() {
        return Towinglicense;
    }

    public void setTowinglicense(String Towinglicense) {
        this.Towinglicense = Towinglicense;
    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



