package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DefaultSubjectDisplayController {
    public AnchorPane ancDefaultTask;

    public void addNewSubject(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDefaultTask, View.ADD_SUBJECT);
    }
}
