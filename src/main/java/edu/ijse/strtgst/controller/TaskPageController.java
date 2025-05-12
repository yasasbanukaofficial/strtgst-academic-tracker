package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskPageController implements Initializable {
    public AnchorPane ancTaskContainer;

    public void addNewTask(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancTaskContainer, View.ADD_TASK);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigation.navigateTo(ancTaskContainer, View.DEFAULT_TASK);
    }
}
