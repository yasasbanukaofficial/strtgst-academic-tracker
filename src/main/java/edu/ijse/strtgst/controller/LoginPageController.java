package edu.ijse.strtgst.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class LoginPageController {
    public AnchorPane loginAnc;

    public void visitSignUpPage(ActionEvent actionEvent) {
        navigateTo("SignUpPage.fxml");
    }

    public void visitDashboard(ActionEvent actionEvent) {
        navigateTo("MainPage.fxml");
    }

    public void navigateTo(String path){
        try{
            loginAnc.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(loginAnc.widthProperty());
            load.prefHeightProperty().bind(loginAnc.heightProperty());
            loginAnc.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cant Identify the url path");
            e.printStackTrace();
        }
    }
}