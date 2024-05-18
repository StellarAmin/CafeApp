package com.example.cafeapp;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.cafeapp.controllers.LoginScreenController;
import com.example.cafeapp.dao.CustomerDao;
import com.example.cafeapp.dao.MenuItemDao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;
    static String appPath = new File("").getAbsolutePath();

    @Override
    public void start(Stage newPrimaryStage) {
        try {
            Database.getDBConnection();
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
        }
        primaryStage = newPrimaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cafeapp/view/LoginScreen.fxml"));
            BorderPane pane = loader.load();
            BorderPane.setAlignment(pane, Pos.CENTER);
            Scene scene = new Scene(pane, 400, 300);
            primaryStage.setScene(scene);
            LoginScreenController controller = ((LoginScreenController) loader.getController());
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userExists() {
        try {
            return CustomerDao.signInCustomer();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return false;
        }
    }

    private void showRegisterScreen() {
        Label titleLabel = new Label("Register");
        TextField usernameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");
        VBox registerLayout = new VBox(10);
        registerLayout.getChildren().addAll(titleLabel, new Label("Username:"), usernameField, new Label("Email:"),
                emailField, new Label("Password:"), passwordField, registerButton, backButton);
        registerLayout.setAlignment(Pos.CENTER);
        Scene registerScene = new Scene(registerLayout, 300, 200);
        primaryStage.setScene(registerScene);
        primaryStage.setTitle("Coffee & Restaurant App - Register");
        primaryStage.show();

        registerButton.setOnAction(b -> {
            // Simulate registration by storing username and password
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Customer.setName(username);
            Customer.setEmail(email);
            Customer.setPassword(password);

            try {
                CustomerDao.signUpCustomer(); // Save customer to database (for
                                              // demonstration)
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
            }

            // Print registration details (for demonstration)
            System.out.println("Registered username: " + username);
            System.out.println("Registered email: " + email);
            System.out.println("Registered password: " + password);

            // Automatically login after registration
            showMainMenuScreen(username); // Pass username to main menu
        });

        // backButton.setOnAction(b -> showLoginScreen());
    }

    private void showMainMenuScreen(String username) {
        Label titleLabel = new Label("Main Menu");
        Button viewMenuButton = new Button("View Menu");
        Button placeOrderButton = new Button("Place Order");
        Button logoutButton = new Button("Logout");
        Button aboutButton = new Button("About");
        Button contactButton = new Button("Contact");
        VBox mainMenuLayout = new VBox(10);
        mainMenuLayout.getChildren().addAll(titleLabel, viewMenuButton, placeOrderButton, aboutButton, contactButton,
                logoutButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        Scene mainMenuScene = new Scene(mainMenuLayout, 300, 200);
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Coffee & Restaurant App - Main Menu");
        primaryStage.show();

        viewMenuButton.setOnAction(e -> showMenuScreen(username));
        placeOrderButton.setOnAction(e -> {
            // Navigate to the coffee restaurant app
            CafeRestaurantApp cafeApp = new CafeRestaurantApp();
            cafeApp.start(new Stage());
        });
        aboutButton.setOnAction(e -> showAboutScreen());
        contactButton.setOnAction(e -> showContactScreen());
        logoutButton.setOnAction(e -> {
            showAlert("Logout", "Have a nice day! Come back again.");
            Platform.exit();
        });
    }

    private void showMenuScreen(String username) {
        // Display the menu items without launching the CafeRestaurantApp
        Stage menuStage = new Stage();
        VBox menuLayout = new VBox(10);
        Label titleLabel = new Label("Menu");
        menuLayout.getChildren().add(titleLabel);
        List<MenuItem> menu = new ArrayList<>();

        try {
            menu = MenuItemDao.getMenu();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }

        List<MenuItem> drinkMenu = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getType().equals("drink")) {
                drinkMenu.add(menu.get(i));
            }
        }
        Label drinkLabel = new Label("Drinks:");
        menuLayout.getChildren().add(drinkLabel);
        for (MenuItem item : drinkMenu) {
            Label itemLabel = new Label(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            menuLayout.getChildren().add(itemLabel);
        }

        List<MenuItem> foodMenu = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getType().equals("food")) {
                foodMenu.add(menu.get(i));
            }
        }
        Label foodLabel = new Label("Food:");
        menuLayout.getChildren().add(foodLabel);
        for (MenuItem item : foodMenu) {
            Label itemLabel = new Label(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            menuLayout.getChildren().add(itemLabel);
        }

        Button backButton = new Button("Back to Main Menu");
        menuLayout.getChildren().add(backButton);
        backButton.setOnAction(e -> {
            menuStage.close();
            showMainMenuScreen(username);
        });

        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 300, 400);
        menuStage.setScene(menuScene);
        menuStage.setTitle("Coffee & Restaurant App - Menu");
        menuStage.show();
    }

    private void showAboutScreen() {
        showAlert("About", "This is the Coffee & Restaurant App.\nDeveloped by Omar Alaa & Amin Mossadag.");
    }

    private void showContactScreen() {
        showAlert("Contact",
                "For any inquiries, please contact:\nEmail: omarlaaaeldeen620@gmail.com\nPhone: +201151555823");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
