package com.example.getgrarfinalversion;


import android.location.Location;

public class locationObject {
    private String myLocation,addrees,uid;

    private  locationObject(){}
    public locationObject (String myLocation,String addrees,String uid){
        this.addrees=addrees;
        this.myLocation=myLocation;

     //   this.uid=uid;
    }

    public String getAddrees() {
        return addrees;
    }

    public void setAddrees(String addrees) {
        this.addrees = addrees;
    }


    /*public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

  /*  public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }*/
}
