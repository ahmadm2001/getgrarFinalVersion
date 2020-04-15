package com.example.getgrarfinalversion;

public class Customer {
    private String name, Phone,email, TypeCar,numbercar, uid;
    Boolean inorder;
    public Customer(){}
    public Customer(String Name, String Phone,String email, String typeCar, String Numbercar, String uid ,Boolean inorder){
            this.name=Name;
            this.TypeCar=typeCar;
        this.numbercar=Numbercar;
        this.Phone=Phone;
        this.inorder=inorder;
        this.uid=uid;
        this.email=email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumbercar() {
        return numbercar;
    }

    public void setNumbercar(String numbercar) {
        this.numbercar = numbercar;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getTypeCar() {
        return TypeCar;
    }

    public void setTypeCar(String typeCar) {
        TypeCar = typeCar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getinorder(){
        return inorder;
    }

    public void setInorder(boolean inorder) {
        this.inorder = inorder;
    }
    /*public void copyCustomer(Customer customer){
        this.name=customer.getName();
        this.Phone=customer.getPhone();
        this.email=customer.getEmail();
        this.numbercar=customer.getNumbercar();
        this.TypeCar=customer.getTypeCar();
        this.uid=customer.getUid();
    }*/

}