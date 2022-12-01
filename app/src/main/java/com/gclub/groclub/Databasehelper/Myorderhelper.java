package com.gclub.groclub.Databasehelper;

public class Myorderhelper {
    String address , amount , date ,deliverydate , mobileno ,name , order_status , orderid ,payment_method ,time;

    public Myorderhelper() {
    }

    public Myorderhelper(String address, String amount, String date, String deliverydate, String mobileno, String name, String order_status, String orderid, String payment_method, String time) {
        this.address = address;
        this.amount = amount;
        this.date = date;
        this.deliverydate = deliverydate;
        this.mobileno = mobileno;
        this.name = name;
        this.order_status = order_status;
        this.orderid = orderid;
        this.payment_method = payment_method;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
