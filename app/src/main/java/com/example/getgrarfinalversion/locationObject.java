package com.example.getgrarfinalversion;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class locationObject {
    private String myLocation,addrees,uid,name,phone,numbercar,typecar,Duid;
    private int status;
    Boolean Act;
    private Double firstlat,Firstlatlong1,lastlat,lastlong;

    public   locationObject(){}
    public locationObject (String myLocation,String addrees,String uid,String name,String numbercar,String typecar,
                           String phone, int status,Double Firstlatlong1,Double firstlat,
                           Double lastlat,Double lastlong,String Duid,Boolean Act){

        this.addrees=addrees;
        this.myLocation=myLocation;
        this.name = name;
        this.phone = phone;
        this.numbercar = numbercar;
        this.typecar = typecar;
        this.uid=uid;
        this.status=status;
        this.firstlat=firstlat;
        this.Firstlatlong1=Firstlatlong1;
        this.lastlat=lastlat;
        this.lastlong=lastlong;
        this.Duid=Duid;
        this.Act=Act;
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

    public Double getFirstlat() {
        return firstlat;
    }

    public void setFirstlat(Double firstlat) {
        this.firstlat = firstlat;
    }

    public Double getFirstlatlong1() {
        return Firstlatlong1;
    }

    public void setFirstlatlong1(Double firstlatlong1) {
        Firstlatlong1 = firstlatlong1;
    }

    public Double getLastlat() {
        return lastlat;
    }

    public void setLastlat(Double lastlat) {
        this.lastlat = lastlat;
    }

    public Double getLastlong() {
        return lastlong;
    }

    public void setLastlong(Double lastlong) {
        this.lastlong = lastlong;
    }

    public String getDuid() {
        return Duid;
    }

    public void setDuid(String duid) {
        Duid = duid;
    }

    public Boolean isAct() {
        return Act;
    }

    public void setAct(Boolean act) {
        Act = act;
    }
}
