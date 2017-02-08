package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

/**
 * User class defines the attributes for a User
 */

@IgnoreExtraProperties
public class User extends Entity {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;
    private String username;
    private String authUserID;

    /**
     * Default constructor
     */
    public User() {
    }

    public User(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email, String username, String key){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.setKey(key);
        //this.authUserID = authUserID;
    }


//    public void setAuthUserID(String authuserID) {
//        this.authUserID = authUserID;
//    }

//    public String getAuthUserID() {
//
//        return authUserID;
//    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public void setKey(String key) {
        super.setKey(key);
    }
}
