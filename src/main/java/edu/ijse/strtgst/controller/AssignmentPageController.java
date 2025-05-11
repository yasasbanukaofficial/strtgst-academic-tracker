package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentPageController implements Initializable {
    public AnchorPane ancTaskContainer;
    public TableView tblAssignment;
    public TableColumn columnAssignmentName;
    public TableColumn columnAssignmentDueDate;
    public TableColumn columnAssignmentStatus;
    public TableColumn columnAssignmentMarks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Navigation.navigateTo(ancTaskContainer, View.DEFAULT_ASSIGNMENT);
    }

    public void addNewAssignment(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancTaskContainer, View.ADD_ASSIGNMENT);
    }
}
