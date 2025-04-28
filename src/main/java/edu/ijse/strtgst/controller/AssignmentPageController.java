package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentPageController implements Initializable {
    public AnchorPane ancTaskContainer;


    public void navigateTo(String path){
        try {
            ancTaskContainer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(ancTaskContainer.widthProperty());
            load.prefHeightProperty().bind(ancTaskContainer.heightProperty());
            ancTaskContainer.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error when defining url!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("DefaultAssignmentDisplay.fxml");
    }

    public void addNewAssignment(MouseEvent mouseEvent) {
        navigateTo("AddNewAssignment.fxml");
    }
}
