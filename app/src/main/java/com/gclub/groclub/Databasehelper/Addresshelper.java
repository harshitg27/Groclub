package com.gclub.groclub.Databasehelper;

public class Addresshelper {
    String area , city , houseDetail , landmark , name , pincode ;

    public Addresshelper() {
    }

    public Addresshelper(String area, String city, String houseDetail, String landmark, String name, String pincode) {
        this.area = area;
        this.city = city;
        this.houseDetail = houseDetail;
        this.landmark = landmark;
        this.name = name;
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseDetail() {
        return houseDetail;
    }

    public void setHouseDetail(String houseDetail) {
        this.houseDetail = houseDetail;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
