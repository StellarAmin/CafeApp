package com.example.cafeapp;

// Customer class represents a customer
class Customer {
    private static int id;
    private static String name;
    private static String email;
    private static String password;

    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(int id, String name, String email) {
        this.name = name;
        this.email = email;
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
