package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.context.ControllerManager;
import edu.ijse.strtgst.dto.tm.AssignmentTM;
import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AssignmentPageController implements Initializable {
    public AnchorPane ancTaskContainer;
    public TableView<AssignmentTM> tblAssignment;
    public TableColumn<AssignmentTM, String> columnAssignmentName;
    public TableColumn<AssignmentTM, LocalDate> columnAssignmentDueDate;
    public TableColumn<AssignmentTM, String> columnAssignmentStatus;
    public TableColumn<AssignmentTM, String> columnAssignmentMarks;

    private final AssignmentModel assignmentModel = new AssignmentModel();
    private final ControllerManager controllerManager = new ControllerManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumn();
        controllerManager.setAssignmentPageController(this);
        Navigation.navigateTo(ancTaskContainer, View.DEFAULT_ASSIGNMENT);
    }

    public void addNewAssignment(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancTaskContainer, View.ADD_ASSIGNMENT);
    }

    public void loadTableData(){
        try {
            tblAssignment.setItems(FXCollections.observableArrayList(
                assignmentModel.getAllCustomer().stream().map(
                        assignmentDto -> new AssignmentTM(
                                assignmentDto.getAssignmentId(),
                                assignmentDto.getAssignmentName(),
                                assignmentDto.getAssignmentDescription(),
                                assignmentDto.getDueDate(),
                                assignmentDto.getAssignmentStatus(),
                                assignmentDto.getAssignmentMarks()
                        )
                ).toList()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTableColumn() {
        columnAssignmentName.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
        columnAssignmentDueDate.setCellValueFactory(new PropertyValueFactory<>("assignmentDueDate"));
        columnAssignmentStatus.setCellValueFactory(new PropertyValueFactory<>("assignmentStatus"));
        columnAssignmentMarks.setCellValueFactory(new PropertyValueFactory<>("assignmentMarks"));

        columnAssignmentStatus.setCellFactory(c -> new TableCell<>(){
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if(empty || status == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(status);
                    label.setStyle(getStatusStyle(status));
                    setGraphic(label);
                    setText(null);
                }
            }
        });
        loadTableData();
    }

    private String getStatusStyle(String status) {
        return switch (status.toLowerCase()) {
            case "completed" -> "-fx-background-color: #11C759; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            case "pending" -> "-fx-background-color: #f1c40f; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            case "overdue" -> "-fx-background-color: #d90429; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 10;";
            default -> "-fx-background-color: #bdc3c7; -fx-text-fill: black; -fx-padding: 4 8; -fx-background-radius: 10;";
        };
    }

    public void onClickTable(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancTaskContainer, View.ADD_ASSIGNMENT);
        AssignmentTM selectedAssignment = tblAssignment.getSelectionModel().getSelectedItem();
        AddNewAssignmentController addNewAssignmentController = controllerManager.getAddNewAssignmentController();
        if (selectedAssignment != null){
            addNewAssignmentController.configureEditForm(selectedAssignment);
        }
    }
}
