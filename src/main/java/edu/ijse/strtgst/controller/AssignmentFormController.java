package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.context.ControllerManager;
import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.dto.tm.AssignmentTM;
import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.util.AlertUtil;
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
    private final ControllerManager controllerManager = new ControllerManager();
    private final AssignmentPageController assignmentPageController = ControllerManager.getAssignmentPageController();

    private ObservableList<String> statusOptions = FXCollections.observableArrayList("Pending", "Completed", "Overdue");
    private ObservableList<String> marksOptions = FXCollections.observableArrayList();
    private ObservableList<String> subjectOptions = FXCollections.observableArrayList("Maths", "Science");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllerManager.setAssignmentFormController(this);
        for (int i = 0; i <= 100 ; i++) {
            marksOptions.add(Integer.toString(i));
        }
        setupFormDefaults();
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
        if (validateAssignmentFields(assignmentName, status, date)) {
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
                if (assignmentModel.addAssignment(assignmentDto)) {
                    AlertUtil.setInfoAlert("Successfully added an assignment");
                    Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
                } else AlertUtil.setErrorAlert("Failed to save an Assignment");
            } catch (SQLException e) {
                AlertUtil.setErrorAlert("Failed when adding an assignment");
                e.printStackTrace();
            }
            assignmentPageController.updateOverdueStatus();
        }
    }

    private void deleteAssignment(AssignmentTM assignmentTM) {
        Optional<ButtonType> resp = AlertUtil.setConfirmationAlert("Are you sure you want to delete this assignment?",
                "Name: " + assignmentTM.getAssignmentName() + "\n" +
                "Due: " + assignmentTM.getAssignmentDueDate() + "\n" +
                "Status: " + assignmentTM.getAssignmentStatus() + "\n" +
                "Marks: " + assignmentTM.getAssignmentMarks());

        if (resp.isPresent() && resp.get() == ButtonType.YES) {
            String assignmentId = assignmentTM.getAssignmentId();
            try {
                if (assignmentModel.deleteAssignment(assignmentId)) {
                    assignmentPageController.setupTableColumn();
                    setupFormDefaults();
                    AlertUtil.setInfoAlert("Successfully deleted an assignment");
                } else { AlertUtil.setErrorAlert("Failed to deleted an assignment"); }
            } catch (SQLException e) {
                AlertUtil.setErrorAlert("Error when deleting an assignment");
                e.printStackTrace();
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

        setButtonStates(false);
        if (validateAssignmentFields(assignmentName, status, date)) {
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
                if (assignmentModel.editAssignment(assignmentDto)) {
                    AlertUtil.setInfoAlert("Successfully edited the assignment");
                    Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
                } else AlertUtil.setErrorAlert("Failed to edit the assignment");
            } catch (SQLException e) {
                AlertUtil.setErrorAlert("Failed when editing the assignment");
                e.printStackTrace();
            }
            assignmentPageController.updateOverdueStatus();
        }
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

    public String loadNextID(){
        try {
            return IdLoader.getNextID("Assignment", "assignment_id");
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading a Assignment ID");
            e.printStackTrace();
        }
        return "A001";
    }

    private void setupFormDefaults() {
        txtAssignmentName.setText("");
        txtAssignmentDescription.setText("");
        dpDueDate.setValue(null);
        cmbSubject.setItems(subjectOptions);
        cmbSubject.setValue(subjectOptions.get(0));
        cmbMarks.setItems(marksOptions);
        cmbMarks.setValue(marksOptions.get(0));
        cmbStatus.setItems(statusOptions);
        cmbStatus.setValue(statusOptions.get(0));
        setButtonStates(false);
    }

    private void setButtonStates(boolean state) {
        btnAddAssignment.setDisable(state);
        btnCancel.setDisable(state);
    }

    private boolean validateAssignmentFields(String assignmentName, String status, LocalDate date) {
        if (!areRequiredFieldsFilled(assignmentName, status, date)){
            AlertUtil.setErrorAlert("You must fill required fields (*)!");
            return false;
        }

        if (!status.equals("Overdue") && date.isBefore(LocalDate.now())){
            AlertUtil.setErrorAlert("Assignments due before today must be marked as overdue. ");
            return false;
        } else if (status.equals("Overdue") && date.isAfter(LocalDate.now())){
            AlertUtil.setErrorAlert("Cannot mark a future assignment as overdue.");
            return false;
        }
        return true;
    }

    private boolean areRequiredFieldsFilled(Object... inputs) {
        for(Object input : inputs){
            if (input == null || input.equals("")) {
                return false;
            }
        }
        return true;
    }
}