package com.example.buffstuff;

//A class for each item; used to display in buy page
public class Item {
    private String name;
    private Double price;
    private String id;

    public Item() {
        //empty constructor needed
    }

    public Item(String name, Double price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getID(){return id;}

    public void setName(String Name) {
        name = Name;
    }

    public void setPrice(Double Price) {
        price = Price;
    }

    public void setId(String id){this.id = id;}
}