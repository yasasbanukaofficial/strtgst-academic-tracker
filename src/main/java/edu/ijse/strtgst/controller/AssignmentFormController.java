package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.context.ControllerManager;
import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.dto.tm.AssignmentTM;
import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.util.IdLoader;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AssignmentFormController implements Initializable {
    public AnchorPane ancAddNewTask;
    public TextField txtAssignmentName;
    public TextArea txtAssignmentDescription;
    public ComboBox<String> cmbSubject;
    public ComboBox<String> cmbMarks;
    public DatePicker dpDueDate;
    public ComboBox<String> cmbStatus;
    public Button btnAddAssignment;
    public Button btnCancel;
    public Label labelAssignmentHeader;
    public Label labelCancel;

    private AssignmentDto assignmentDto;
    private final AssignmentModel assignmentModel = new AssignmentModel();
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    private final ControllerManager controllerManager = new ControllerManager();
    private final AssignmentPageController assignmentPageController = ControllerManager.getAssignmentPageController();

    private ObservableList<String> statusOptions = FXCollections.observableArrayList("Pending", "Completed", "Overdue");
    private ObservableList<String> marksOptions = FXCollections.observableArrayList();
    private ObservableList<String> subjectOptions = FXCollections.observableArrayList("Maths", "Science");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllerManager.setAssignmentFormController(this);
        cmbSubject.setItems(subjectOptions);
        cmbSubject.setValue(subjectOptions.get(0));
        for (int i = 0; i <= 100 ; i++) {
            marksOptions.add(Integer.toString(i));
        }
        cmbMarks.setItems(marksOptions);
        cmbMarks.setValue(marksOptions.get(0));
        cmbStatus.setItems(statusOptions);
        cmbStatus.setValue(statusOptions.get(0));
        setButtonStates(false);
    }

    public void cancelTask(ActionEvent actionEvent) {
        Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
    }

    public void addAssignment(ActionEvent event) {
        String assignment_id = loadNextID();
        String assignmentName = txtAssignmentName.getText();
        String assignmentDescription = txtAssignmentDescription.getText();
        String assignmentMarks = cmbMarks.getValue();
        String subName = cmbSubject.getValue();
        LocalDate date = dpDueDate.getValue();
        String status = cmbStatus.getValue();
        setButtonStates(false);

        if (!areRequiredFieldsFilled(assignmentName, status, date)){
            alert.setContentText("You must fill required fields (*)!");
            alert.show();
            return;
        }
        if (!status.equals("Overdue") && date.isBefore(LocalDate.now())){
            alert.setContentText("Invalid Date: Please choose a date in the future. (Tip: This only works when you want to add overdue tasks.)");
            alert.show();
            return;
        }

        assignmentDto = new AssignmentDto(
                assignment_id,
                assignmentName,
                assignmentDescription,
                assignmentMarks,
                subName,
                date,
                status
        );

        try {
            if (assignmentModel.addAssignment(assignmentDto)){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully added an assignment");
                alert.show();
                Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
            } else new Alert(Alert.AlertType.ERROR, "Failed to save an Assignment").show();
        } catch (SQLException e) {
            alert.setContentText("Failed when adding an assignment");
            alert.show();
            e.printStackTrace();
        }
        assignmentPageController.updateOverdueStatus();
    }

    private void deleteAssignment(AssignmentTM assignmentTM) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Are you sure you want to delete this assignment?");
        alert.setContentText("Name: " + assignmentTM.getAssignmentName() + "\n" +
                "Due: " + assignmentTM.getAssignmentDueDate() + "\n" +
                "Status: " + assignmentTM.getAssignmentStatus() + "\n" +
                "Marks: " + assignmentTM.getAssignmentMarks());

        Optional<ButtonType> resp = alert.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            String assignmentId = assignmentTM.getAssignmentId();
            try {
                if (assignmentModel.deleteAssignment(assignmentId)) {
                    assignmentPageController.setupTableColumn();
                    resetForm();
                    new Alert(Alert.AlertType.INFORMATION, "Successfully deleted an assignment").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to deleted an assignment").show();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "Error when deleting an assignment").show();
            }
        }
    }

    private void editAssignment(AssignmentTM assignmentTM) {
        String assignment_id = assignmentTM.getAssignmentId();
        String assignmentName = txtAssignmentName.getText();
        String assignmentDescription = txtAssignmentDescription.getText();
        String assignmentMarks = cmbMarks.getValue();
        LocalDate date = dpDueDate.getValue();
        String status = cmbStatus.getValue();
        String subName = cmbSubject.getValue();

        if (!areRequiredFieldsFilled(assignmentName, status, date)){
            alert.setContentText("You must fill required fields (*)!");
            alert.show();
            return;
        }
        if (!status.equals("Overdue") && date.isBefore(LocalDate.now())){
            alert.setContentText("Invalid Date: Please choose a date in the future. (Tip: This only works when you want to add overdue tasks.)");
            alert.show();
        }

        assignmentDto = new AssignmentDto(
                assignment_id,
                assignmentName,
                assignmentDescription,
                assignmentMarks,
                subName,
                date,
                status
        );

        try {
            if (assignmentModel.editAssignment(assignmentDto)){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully edited the assignment");
                alert.show();
                Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
            } else new Alert(Alert.AlertType.ERROR, "Failed to edit the Assignment").show();
        } catch (SQLException e) {
            alert.setContentText("Failed when editing the assignment");
            alert.show();
            e.printStackTrace();
        }
        assignmentPageController.updateOverdueStatus();
    }

    public void populateFormForEdit(AssignmentTM assignmentTM){
        txtAssignmentName.setText(assignmentTM.getAssignmentName());
        txtAssignmentDescription.setText(assignmentTM.getAssignmentDescription());
        dpDueDate.setValue(assignmentTM.getAssignmentDueDate());
        cmbStatus.setValue(assignmentTM.getAssignmentStatus());
        cmbMarks.setValue(assignmentTM.getAssignmentMarks());
        cmbSubject.setValue(assignmentTM.getSubName());
        setButtonStates(false);

        labelAssignmentHeader.setText("Edit Assignment");
        labelCancel.setText("Cancel Editing");
        labelCancel.setOnMouseClicked(e -> {
            cancelTask(new ActionEvent());
        });

        btnAddAssignment.setText("Edit Assignment");
        btnAddAssignment.setOnAction(e -> {editAssignment(assignmentTM);});

        btnCancel.setText("Delete Assignment");
        btnCancel.getStyleClass().add("button-delete");
        btnCancel.setOnAction(e -> {deleteAssignment(assignmentTM);});
    }

    private void setButtonStates(boolean state) {
        btnAddAssignment.setDisable(state);
        btnCancel.setDisable(state);
    }

    private void resetForm() {
        txtAssignmentName.setText("");
        txtAssignmentDescription.setText("");
        dpDueDate.setValue(null);
        cmbSubject.setValue(null);
        cmbMarks.setValue(null);
        cmbStatus.setValue(null);
        setButtonStates(true);
    }

    public String loadNextID(){
        try {
            return IdLoader.getNextID("Assignment", "assignment_id");
        } catch (SQLException e) {
            alert.setContentText("Error when loading a Assignment ID");
            alert.show();
            e.printStackTrace();
        }
        return "A001";
    }

    public boolean areRequiredFieldsFilled(Object... inputs) {
        for(Object input : inputs){
            if (input == null || input.equals("")) {
                return false;
            }
        }
        return true;
    }
}