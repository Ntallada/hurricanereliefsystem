package gpp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SheltersController {

    @FXML private TextField searchField;
    @FXML private Label resultsCountLabel;
    @FXML private Label selectedMapPinLabel;
    @FXML private VBox shelterListContainer;

    @FXML
    public void initialize() {
        // Load shelter data from your backend model here
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase().trim();
        // Filter your list of Sheltersby city/ZIP 
        System.out.println("Searching shelters for: " + query);
    }

    @FXML
    private void handleFilterClick() {
        // Open filter popup 
    }

    @FXML
    private void handleViewShelter1() {
        selectedMapPinLabel.setText("1  Community Center");
    }

    @FXML
    private void handleViewShelter2() {
        selectedMapPinLabel.setText("2  Northside High School");
    }

    @FXML private void handleZoomIn() { /* Zoom map pane */ }
    @FXML private void handleZoomOut() { /* Zoom out map pane */ }
    @FXML private void handleMenuClick() { /* Open side drawer */ }
    @FXML private void handleNotificationsClick() { /* Open alerts */ }

   
    @FXML private void goToHome() { /* Load HomeView.fxml */ }
    @FXML private void goToRequests() { /* Load RequestsView.fxml */ }
    @FXML private void goToAccount() { /* Load AccountView.fxml */ }
}