package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskPageController implements Initializable {
    public AnchorPane ancTaskContainer;

    public void addNewTask(MouseEvent mouseEvent) {
        navigateTo("AddNewTask.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("DefaultTaskDisplay.fxml");
    }

    public void navigateTo(String path){
        try {
            ancTaskContainer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(ancTaskContainer.widthProperty());
            load.prefHeightProperty().bind(ancTaskContainer.heightProperty());
            ancTaskContainer.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error defining the path");
            e.printStackTrace();
        }
    }
}
