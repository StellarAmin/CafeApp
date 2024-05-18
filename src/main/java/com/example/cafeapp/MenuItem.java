package com.example.cafeapp;

// MenuItem class represents items on the menu
public class MenuItem {
    private int id;
    private String name, type;
    private double price;

    // Constructor to create a menu item with a name and price
    public MenuItem(int id, String name, double price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getter method to get the name of the item in the menu
    public int getId() {
        return id;
    }

    // Getter method to get the name of the item in the menu
    public String getName() {
        return name;
    }

    // Getter method to get the name of the item in the menu
    public String getType() {
        return type;
    }

    // Getter method to get the price
    public double getPrice() {
        return price;
    }
}
