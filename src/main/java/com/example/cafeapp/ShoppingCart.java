package com.example.cafeapp;

import java.util.ArrayList;
import java.util.List;

// ShoppingCart class manages items added to the cart and calculates the total
public class ShoppingCart {
    private static List<MenuItem> items = new ArrayList<>();
    private static double total = 0;

    // Method to add an item to the shopping cart
    public static void addItem(MenuItem item) {
        items.add(item);
        total += item.getPrice();
    }

    // Method to retrieve the items in the shopping cart
    public static List<MenuItem> getItems() {
        return items;
    }

    public static double getTotal() {
        return total;
    }

    public static void setTotal(double newTotal) {
        total = newTotal;
    }

    public static void clearItems() {
        items.clear();
        total = 0;
    }
}
