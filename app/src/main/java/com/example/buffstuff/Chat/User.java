package com.example.buffstuff.Chat;

//A class for each item; used to display in buy page
public class User {
    private String name;

    public User() {
        //empty constructor needed
    }

    public User(String name, Double price, String id) {
        this.name = name;
    }
    //Get all things in item
    public String getName() {
        return name;
    }


    //Set all things in item
    public void setName(String Name) {
        name = Name;
    }

}