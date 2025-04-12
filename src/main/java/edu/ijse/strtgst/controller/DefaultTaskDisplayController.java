package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DefaultTaskDisplayController {
    public AnchorPane ancDefaultTask;

    public void addTask(MouseEvent mouseEvent) {
        try {
            ancDefaultTask.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AddNewTask.fxml"));
            load.prefWidthProperty().bind(ancDefaultTask.widthProperty());
            load.prefHeightProperty().bind(ancDefaultTask.heightProperty());
            ancDefaultTask.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error defining the path");
            e.printStackTrace();
        }
    }
}
