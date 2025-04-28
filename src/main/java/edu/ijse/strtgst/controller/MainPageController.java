package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    public AnchorPane ancTabDisplay;

    public void navigateTo(String path){
        try {
            ancTabDisplay.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(ancTabDisplay.widthProperty());
            load.prefHeightProperty().bind(ancTabDisplay.heightProperty());
            ancTabDisplay.getChildren().add(load);
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Error finding the path");
            e.printStackTrace();
        }
    }

    public void visitAssignmentPage(MouseEvent mouseEvent) {
        navigateTo("AssignmentPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("DashboardPage.fxml");
    }

    public void visitDashboardPage(MouseEvent mouseEvent) {
        navigateTo("DashboardPage.fxml");
    }

    public void visitTaskPage(MouseEvent mouseEvent) {
        navigateTo("TaskPage.fxml");
    }

    public void visitSettingsPage(MouseEvent mouseEvent) {
        navigateTo("SettingsPage.fxml");
    }
}
