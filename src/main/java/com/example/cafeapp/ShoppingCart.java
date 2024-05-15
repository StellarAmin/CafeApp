package com.example.cafeapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

  // ShoppingCart class manages items added to the cart and calculates the total
     class ShoppingCart {
        private ObservableList<String> items = FXCollections.observableArrayList();
        private ObservableList<Double> prices = FXCollections.observableArrayList();
        private double total = 0;
        // Method to add an item to the shopping cart
        public void addItem(String item, double price) {
            items.add(item);
            prices.add(price);
            total += price;
        }
        // Method to retrieve the items in the shopping cart
        public ObservableList<String> getItems() {
            return items;
        }

        public ObservableList<Double> getPrices() {
            return prices;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public void clearItems() {
            items.clear();
            prices.clear();
            total = 0;
        }
    }
