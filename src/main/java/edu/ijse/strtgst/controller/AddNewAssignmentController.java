package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.AssignmentDto;
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
import java.util.ResourceBundle;

public class AddNewAssignmentController implements Initializable {
    public AnchorPane ancAddNewTask;
    public TextField txtAssignmentName;
    public TextArea txtAssignmentDescription;
    public ComboBox<String> cmbSubject;
    public ComboBox<String> cmbMarks;
    public DatePicker dpDueDate;
    public ComboBox<String> cmbStatus;

    private AssignmentDto assignmentDto;
    private final AssignmentModel assignmentModel = new AssignmentModel();
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    private ObservableList<String> statusOptions = FXCollections.observableArrayList("Pending", "Completed", "Overdue");
    private ObservableList<String> marksOptions = FXCollections.observableArrayList();
    private ObservableList<String> subjectOptions = FXCollections.observableArrayList("Maths", "Science");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbSubject.setItems(subjectOptions);
        cmbSubject.setValue(subjectOptions.get(0));
        for (int i = 0; i <= 100 ; i++) {
            marksOptions.add(Integer.toString(i));
        }
        cmbMarks.setItems(marksOptions);
        cmbMarks.setValue(marksOptions.get(0));
        cmbStatus.setItems(statusOptions);
        cmbStatus.setValue(statusOptions.get(0));
    }

    public void cancelTask(ActionEvent actionEvent) {
        Navigation.navigateTo(ancAddNewTask, View.DEFAULT_ASSIGNMENT);
    }

    public void addAssignment(ActionEvent event) {
        String assignment_id = loadNextID();
        String assignmentName = txtAssignmentName.getText();
        String assignmentDescription = txtAssignmentDescription.getText();
        String assignmentMarks = cmbMarks.getValue();
        String subject = cmbSubject.getValue();
        LocalDate date = dpDueDate.getValue();
        String status = cmbStatus.getValue();
        String sub_id = fetchSubId(subject);

        if (sub_id == null){
            alert.setContentText("The selected subject does not exist. Please choose a valid subject.");
            alert.show();
            return;
        }
        if (!isFieldsFilled(assignmentName, status, date)){
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
                sub_id,
                assignmentName,
                assignmentDescription,
                assignmentMarks,
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

    public boolean isFieldsFilled(Object... inputs) {
        for(Object input : inputs){
            if (input == null || input.equals("")) {
                return false;
            }
        }
        return true;
    }

    public String fetchSubId(String subjectName) {
        try {
            return assignmentModel.fetchExistingID(subjectName);
        } catch (SQLException e) {
            alert.setContentText("Error when fetching existing sub id");
            alert.show();
            e.printStackTrace();
        }
        return null;
    }
}