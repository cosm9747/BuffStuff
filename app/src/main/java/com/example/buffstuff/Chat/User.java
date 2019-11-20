package com.example.buffstuff.Chat;

//A class for each item; used to display in buy page
public class User {
    private String Name;


    public User() {
        //empty constructor needed
    }

    public User(String Name) {
        this.Name = Name;
    }
    //Get all things in item
    public String getName() {
        return Name;
    }



    //Set all things in item
    public void setName(String Name) {
        this.Name = Name;
    }

}