package edu.ijse.strtgst.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class AddNewTaskController {
    public AnchorPane ancAddNewTask;

    public void cancelTask(ActionEvent actionEvent) {
        try{
            ancAddNewTask.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DefaultTaskDisplay.fxml"));
            load.prefHeightProperty().bind(ancAddNewTask.heightProperty());
            load.prefWidthProperty().bind(ancAddNewTask.widthProperty());
            ancAddNewTask.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error defining url path");
        }
    }
}
