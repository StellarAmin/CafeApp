package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.cafeapp.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class ContactScreenController implements Initializable {

    @FXML
    private void showMainMenuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/MainMenu.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
