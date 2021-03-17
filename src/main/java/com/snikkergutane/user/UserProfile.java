package com.snikkergutane.user;

import java.time.LocalDate;

public class UserProfile {
    private String UserName;
    private String UserEmail;
    private String UserphoneNumber;
    private String Password;
    private UserType usertype;
    private LocalDate enrolledDate;


    public UserProfile(String name, String Email, String phoneNumber, LocalDate enrolledDate){
        this.UserName = name;
        this.UserEmail = Email;
        this.UserphoneNumber = phoneNumber;
        this.enrolledDate = enrolledDate;
        this.Password = "";
    }

    public String getUserName(){
        return this.UserName;

    }

    public String getUserEmail(){
        return this.UserEmail;
    }

    public String getUserphoneNumber(){
        return this.UserphoneNumber;
    }


    public String getPassword(){
        return this.Password;
    }


    public void setPassword(String Password){
        this.Password = Password;
    }


    public boolean checkPassword(String password){
        boolean checkPassword = false;

        if(getPassword().equals(password)){
            checkPassword = true;
        }
        return checkPassword;
    }
}