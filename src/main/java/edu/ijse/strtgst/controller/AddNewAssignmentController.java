package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class AddNewAssignmentController {
    public AnchorPane ancAddNewTask;

    public void cancelTask(ActionEvent actionEvent) {
        Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
    }
}
