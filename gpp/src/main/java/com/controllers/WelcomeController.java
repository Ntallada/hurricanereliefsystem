package com.controllers;

import java.io.IOException;
import com.gpprelief.App;
import com.model.HurricaneReliefFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {

    @FXML private Label lbl_welcome;

    @FXML
    private void initialize() {
        String name = HurricaneReliefFacade.getInstance().getCurrentUser().getFirstName();
        lbl_welcome.setText("Welcome, " + name + "!");
    }

    @FXML
    private void logout() throws IOException {
        HurricaneReliefFacade.getInstance().logout();
        App.setRoot("login");
    }
}