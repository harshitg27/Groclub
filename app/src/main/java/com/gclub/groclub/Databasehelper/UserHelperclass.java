package com.gclub.groclub.Databasehelper;

public class UserHelperclass {
    String fullname,phonenum,email,password,refral;

    private UserHelperclass(){

    }

    public UserHelperclass(String fullname, String phonenum, String email, String password, String refral) {
        this.fullname = fullname;
        this.phonenum = phonenum;
        this.email = email;
        this.password = password;
        this.refral = refral;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefral() {
        return refral;
    }

    public void setRefral(String refral) {
        this.refral = refral;
    }

}
