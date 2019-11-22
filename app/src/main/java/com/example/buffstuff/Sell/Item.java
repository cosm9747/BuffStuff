package com.example.buffstuff.Sell;

//A class for each item; used to display in buy page
public class Item {
    private String name;
    private Double price;
    private String id;
    private String condition;
    private String category;

    public Item() {
        //empty constructor needed
    }

    public Item(String name, Double price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }
    //Get all things in item
    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getID(){return id;}

    public String getCondition(){return condition;}

    public String getCategory(){return category;}


    //Set all things in item
    public void setName(String Name) {
        name = Name;
    }

    public void setPrice(Double Price) {
        price = Price;
    }

    public void setId(String id){this.id = id;}

    public void setCondition(String condition){this.condition = condition;}

    public void setCategory(String category){this.category = category;}
}