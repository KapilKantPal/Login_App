package com.programmingmanav.loginapp;

public class DataBaseHolder {
    String UserName, Email, Password ;

    public DataBaseHolder(String userName, String email, String password) {
        UserName = userName;
        Email = email;
        Password = password;
    }

    public DataBaseHolder(String username , String email){
        UserName = username;
        Email = email;

    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}