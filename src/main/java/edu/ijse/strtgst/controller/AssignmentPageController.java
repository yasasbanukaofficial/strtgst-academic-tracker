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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumn();
        updateOverdueStatus();
        controllerManager.setAssignmentPageController(this);
        Navigation.navigateTo(ancTaskContainer, View.DEFAULT_ASSIGNMENT);
    }

    public void addNewAssignment(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancTaskContainer, View.ADD_ASSIGNMENT);
    }

    private void loadTableData(){
        try {
            tblAssignment.setItems(FXCollections.observableArrayList(
                assignmentModel.getAllAssignments().stream().map(
                        assignmentDto -> new AssignmentTM(
                                assignmentDto.getAssignmentId(),
                                assignmentDto.getAssignmentName(),
                                assignmentDto.getAssignmentDescription(),
                                assignmentDto.getAssignmentMarks(),
                                assignmentDto.getSubName(),
                                assignmentDto.getDueDate(),
                                assignmentDto.getAssignmentStatus()
                        )
                ).toList()
            ));
        } catch (Exception e) {
            alert.setContentText("Error when loading table data");
            alert.show();
            e.printStackTrace();
        }
    }

    public void setupTableColumn() {
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
        AssignmentFormController addNewAssignmentController = controllerManager.getAssignmentFormController();
        if (selectedAssignment != null){
            addNewAssignmentController.populateFormForEdit(selectedAssignment);
        }
    }

    public void updateOverdueStatus() {
        try {
            LocalDate today = LocalDate.now();
            ArrayList<ArrayList> assignments = assignmentModel.getAllSubjectStatus();

            for (ArrayList row : assignments) {
                String status = row.get(0).toString();
                LocalDate dueDate = LocalDate.parse(row.get(1).toString());
                String assignmentId = row.get(2).toString();

                if (status.equalsIgnoreCase("Pending") && dueDate.isBefore(today)) {
                    assignmentModel.updateAssignmentStatus(assignmentId, "Overdue");
                    System.out.println("Updated to overdue: " + assignmentId);
                }
            }
            setupTableColumn();

        } catch (SQLException e) {
            alert.setContentText("Error when updating status");
            alert.show();
            e.printStackTrace();
        }
    }
}
