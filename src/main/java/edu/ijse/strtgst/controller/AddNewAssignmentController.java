package edu.ijse.strtgst.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class AddNewAssignmentController {
    public AnchorPane ancAddNewTask;

    public void cancelTask(ActionEvent actionEvent) {
        navigateTo("DefaultAssignmentDisplay.fxml");
    }

    public void navigateTo(String path){
        try {
            ancAddNewTask.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(ancAddNewTask.widthProperty());
            load.prefHeightProperty().bind(ancAddNewTask.heightProperty());
            ancAddNewTask.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error when defining url!");
            e.printStackTrace();
        }
    }
}
