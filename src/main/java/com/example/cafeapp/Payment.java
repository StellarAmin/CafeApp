package com.example.cafeapp;

 // Payment class represents a payment made for an order
     class Payment {
        private int paymentId;
        private double amount;
        private Order order;

        public Payment(int paymentId, double amount, Order order) {
            this.paymentId = paymentId;
            this.amount = amount;
            this.order = order;
        }

        public int getPaymentId() {
            return paymentId;
        }

        public double getAmount() {
            return amount;
        }

        public Order getOrder() {
            return order;
        }
    }
