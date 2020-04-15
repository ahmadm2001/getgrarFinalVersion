package com.example.getgrarfinalversion;

public class graroffer {

    private String name,phone, price, uid, ArrivalTime;
public graroffer(){}
    public graroffer(String name,String phone ,String price, String uid, String ArrivalTime ) {
        this.name=name;
        this.phone=phone;
        this.price = price;
        this.uid = uid;
        this.ArrivalTime = ArrivalTime;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }
}