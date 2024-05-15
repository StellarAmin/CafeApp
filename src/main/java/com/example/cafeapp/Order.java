package com.example.cafeapp;

   // Order class represents an order made by a customer
 class Order {
        private int orderId;
        private Customer customer;
        private ShoppingCart cart;

        public Order(int orderId, Customer customer, ShoppingCart cart) {
            this.orderId = orderId;
            this.customer = customer;
            this.cart = cart;
        }

        public int getOrderId() {
            return orderId;
        }

        public Customer getCustomer() {
            return customer;
        }

        public ShoppingCart getCart() {
            return cart;
        }
    }
