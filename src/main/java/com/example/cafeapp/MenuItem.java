package com.example.cafeapp;

    // MenuItem class represents items on the menu
 class MenuItem {
        private String name;
        private double price;
        // Constructor to create a menu item with a name and price
        public MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }
        // Getter method to get the name of the item in the menu
        public String getName() {
            return name;
        }
        // Getter method to get the price
        public double getPrice() {
            return price;
        }
    }
  
 