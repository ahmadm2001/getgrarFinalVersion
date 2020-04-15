package com.example.getgrarfinalversion;


import android.location.Location;

public class locationObject {
    private String myLocation,addrees,uid,name,phone,numbercar,typecar;
    private int status;
    Boolean inorder;

    public   locationObject(){}
    public locationObject (String myLocation,String addrees,String uid,String name,String numbercar,String typecar, String phone, int status){
        this.addrees=addrees;
        this.myLocation=myLocation;
        this.name = name;
        this.phone = phone;
        this.numbercar = numbercar;
        this.typecar = typecar;
        this.uid=uid;
        this.status=status;
    }

    public String getAddrees() {
        return addrees;
    }

    public void setAddrees(String addrees) {
        this.addrees = addrees;
    }




    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumbercar() {
        return numbercar;
    }

    public void setNumbercar(String numbercar) {
        this.numbercar = numbercar;
    }

    public String getTypecar() {
        return typecar;
    }

    public void setTypecar(String typecar) {
        this.typecar = typecar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
