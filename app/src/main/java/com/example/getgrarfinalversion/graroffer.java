package com.example.getgrarfinalversion;

public class graroffer {

    private String name,phone, price, customeruid, ArrivalTime,driveruid;
    private Double lat,long1;
     long count;
    private boolean Act;
public graroffer(){}
    public graroffer(String name,String phone ,String price, String customeruid, String ArrivalTime, Double lat
            , Double long1,String driveruid,Boolean act, long count) {
        this.name=name;
        this.phone=phone;
        this.price = price;
        this.customeruid = customeruid;
        this.ArrivalTime = ArrivalTime;
        this.lat=lat;
        this.long1=long1;
        this.driveruid=driveruid;
         this.Act=act;
         this.count = count;

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


    public String getCustomeruid() {
        return customeruid;
    }

    public void setCustomeruid(String uid) {
        this.customeruid = uid;
    }

    public String getDriveruid() {
        return driveruid;
    }

    public void setDriveruid(String driveruid) {
        this.driveruid = driveruid;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public Double getLong1() {
        return long1;
    }

    public void setLong1(Double long1) {
        this.long1 = long1;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public boolean isAct() {
        return Act;
    }

    public void setAct(boolean act) {
        Act = act;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}