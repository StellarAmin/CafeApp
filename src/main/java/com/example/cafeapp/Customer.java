package com.example.cafeapp;

// Customer class represents a customer
public class Customer {
    private static int id;
    private static String name;
    private static String email;
    private static String password;

    public Customer(int id, String name, String email, String password) {
        Customer.id = id;
        Customer.name = name;
        Customer.email = email;
        Customer.password = password;
    }

    public Customer(int id, String name, String email) {
        Customer.name = name;
        Customer.email = email;
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPasword() {
        return password;
    }

    public static void setId(int newId) {
        id = newId;
    }

    public static void setName(String newName) {
        name = newName;

    }

    public static void setEmail(String newEmail) {
        email = newEmail;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }
}
