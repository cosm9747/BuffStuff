package com.example.buffstuff;

public class Item {
    private String name;

    public Item() {
        //empty constructor needed
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }

}