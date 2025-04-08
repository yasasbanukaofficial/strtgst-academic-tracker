package edu.ijse.strtgst.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class SignUpPageController{
    public AnchorPane signUpAnc;

    public void visitLoginPage(ActionEvent actionEvent) {
        navigateTo("LoginPage.fxml");
    }

    public void navigateTo(String path){
        try{
            signUpAnc.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            signUpAnc.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cant Identify the url path");
        }
    }
}
