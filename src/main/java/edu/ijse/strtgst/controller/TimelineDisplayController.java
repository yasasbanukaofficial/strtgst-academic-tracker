package edu.ijse.strtgst.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class TimelineDisplayController {

    public void showPopup(MouseEvent mouseEvent) {
        Pane clickedPane = (Pane) mouseEvent.getSource();
        clickedPane.setStyle("-fx-background-color: lightGreen");
    }
}
