package com.example.cafeapp;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
import com.example.cafeapp.controllers.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;
    static String appPath = new File("").getAbsolutePath();
    public static BorderPane pane;

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
            pane = loader.load();
            BorderPane.setAlignment(pane, Pos.CENTER);
            Scene scene = new Scene(pane, 600, 500);
            primaryStage.setScene(scene);
            LoginScreenController controller = ((LoginScreenController) loader.getController());
            controller.setStage(primaryStage);
            primaryStage.setTitle("Cafe & Restaurant App");
            primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/com/example/cafeapp/img/cafe.png")));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
