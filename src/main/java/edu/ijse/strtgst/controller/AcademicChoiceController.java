package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AcademicChoiceController implements Initializable {
    public AnchorPane ancOption;
    public Button btnAddAssignment;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void visitLecturesPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancOption, View.LECTURES_FORM);
    }

    public void visitExamsPage(MouseEvent mouseEvent) {
    }

    public void visitSubjectsPage(MouseEvent mouseEvent) {
    }

    public void visitEventsPage(MouseEvent mouseEvent) {
    }

    public void closeEditor(MouseEvent mouseEvent) {}
}
