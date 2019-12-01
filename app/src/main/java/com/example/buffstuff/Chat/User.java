package com.example.buffstuff.Chat;

//A class for each item; used to display in buy page
public class User {
    private String name;
    private String id;

    public User() {
        //empty constructor needed
    }

    public User(String name, String id) {

        this.name = name;
        this.id = id;
    }
    //Get all things in item
    public String getName() {
        return name;
    }
    public String getId() {return id;}


    //Set all things in item
    public void setName(String Name) {
        name = Name;
    }
    public void setId(String _id) { id = _id; }

}