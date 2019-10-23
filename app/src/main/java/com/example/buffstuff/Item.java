package com.example.buffstuff;

public class Item {
    private String name;
    private Double price;

    public Item() {
        //empty constructor needed
    }

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setPrice(Double Price) {
        price = Price;
    }
}