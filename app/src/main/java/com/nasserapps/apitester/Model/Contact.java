package com.nasserapps.apitester.Model;

public class Contact {

    public int _id;
    public String name = "";
    public String mobileNo = "";

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
// TODO Auto-generated method stub
        this._id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    // constructor
    public Contact (int id, String name, String mobileNo){
        this._id = id;
        this.mobileNo = mobileNo;
        this.name = name;
    }

    public Contact (){

    }
}

