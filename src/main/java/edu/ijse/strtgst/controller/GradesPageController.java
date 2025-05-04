package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GradesPageController implements Initializable {
    public AnchorPane ancAddGrade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigation.navigateTo(ancAddGrade, View.DEFAULT_GRADE);
    }

    public void addNewGrade(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancAddGrade, View.ADD_GRADE);
    }
}
