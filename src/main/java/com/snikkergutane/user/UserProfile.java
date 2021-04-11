package com.snikkergutane.user;

import java.time.LocalDate;

/**
 * The type User profile. Where user makes accounts for login screen.
 */
public class UserProfile {
    private String UserName;
    private String UserEmail;
    private String UserPhoneNumber;
    private String Password;
    private UserType usertype;
    private LocalDate enrolledDate;


    /**
     * Instantiates a new User profile.
     *
     * @param name         the name
     * @param Email        the email
     * @param phoneNumber  the phone number
     * @param enrolledDate the enrolled date
     */
    public UserProfile(String name, String Email, String phoneNumber, LocalDate enrolledDate) throws IllegalArgumentException{
        if (name == null){
            name = "";
        }

        if(Email == null){
            Email = "";
        }

        if(phoneNumber == null){
            phoneNumber = "";
        }

        if(name.isBlank() || Email.isBlank() || phoneNumber.isBlank()){
            throw new IllegalArgumentException("Name, Email or phonenumber has to have a value.");
        }


        this.UserName = name.trim();
        this.UserEmail = Email.trim();
        this.UserPhoneNumber = phoneNumber.trim();
        this.enrolledDate = enrolledDate;
        this.Password = "";
    }

    /**
     * Get user name string.
     *
     * @return the string
     */
    public String getUserName(){
        return this.UserName;

    }

    /**
     * Get user email string.
     *
     * @return the string
     */
    public String getUserEmail(){
        return this.UserEmail;
    }

    /**
     * Get userphone number string.
     *
     * @return the string
     */
    public String getUserphoneNumber(){
        return this.UserPhoneNumber;
    }


    /**
     * Get password string.
     *
     * @return the string
     */
    public String getPassword(){
        return this.Password;
    }


    /**
     * Set password.
     *
     * @param Password the password
     */
    public void setPassword(String Password){
        this.Password = Password;
    }


    /**
     * Check password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean checkPassword(String password){
        boolean checkPassword = false;

        if(getPassword().equals(password)){
            checkPassword = true;
        }
        return checkPassword;
    }
}