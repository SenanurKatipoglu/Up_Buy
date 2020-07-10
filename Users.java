package com.example.up_buy;

public class Users {

    String fullname;
    String username;
    String password;
    String PhoneNumber;
    String admin;






    public Users(String fullname, String username, String password,String PhoneNumber, String admin) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.PhoneNumber=PhoneNumber;
    }


    public Users(){

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }


}
