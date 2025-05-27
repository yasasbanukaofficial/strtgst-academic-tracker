package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.LectureDto;
import edu.ijse.strtgst.model.LectureModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.IdLoader;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class LectureFormController implements Initializable {

    public Label labelLectureHeading;
    public Label labelCancel;
    public TextField txtLectureName;
    public ComboBox <String> cmbSubject;
    public DatePicker dpDate;
    public ComboBox <String> cmbStartHour;
    public ComboBox <String> cmbStartMinute;
    public ComboBox <String> cmbEndHour;
    public ComboBox <String> cmbEndMinute;
    public Button btnCancel;
    public Button btnAddLecture;

    private final LectureModel lectureModel = new LectureModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void cancelAdding(ActionEvent event) {}

    public void addLecture(ActionEvent event) {
        String lecId = loadNextId();
        String lecName = txtLectureName.getText();
        String subId = getSubId(cmbSubject.getValue());
        LocalDate date = dpDate.getValue();
        LocalTime startTime = LocalTime.of(Integer.parseInt(cmbStartHour.getValue()), Integer.parseInt(cmbStartMinute.getValue()));
        LocalTime endTime = LocalTime.of(Integer.parseInt(cmbEndHour.getValue()), Integer.parseInt(cmbEndMinute.getValue()));
        String status = getStatus(dpDate.getValue());

        setButtonStates(false);
        if (validateLectureFields(lecName, date, startTime, endTime, status)){
            LectureDto lectureDto = new LectureDto(
                    lecId,
                    subId,
                    lecName,
                    date,
                    startTime,
                    endTime,
                    status
            );

            try {
                if (lectureModel.addLecture(lectureDto)){
                    AlertUtil.setInfoAlert("Successfully edited the lecture");
                } else AlertUtil.setInfoAlert("Failed to add the lecture");
            } catch (SQLException e){
                AlertUtil.setErrorAlert("Failed when adding a lecture");
                e.printStackTrace();
            }
        }
    }

    private String getSubId(String subName) {
        try {
            String subId = IdLoader.fetchIdByName("Subject", "sub_id", subName);
            if (!areRequiredFieldsFilled(subId)) {
                AlertUtil.setErrorAlert("Error when loading sub id");
            }
            return subId;
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when fetching sub id");
        }
        return null;
    }

    private void setButtonStates(boolean state) {
        btnAddLecture.setDisable(state);
        btnCancel.setDisable(state);
    }

    private String getStatus(LocalDate date) {
        if (LocalDate.now().isBefore(date)){
            return "upcoming";
        } else if (LocalDate.now().isAfter(date)){
            return "ended";
        } else {
            return "ongoing";
        }
    }

    private String loadNextId() {
        try {
            return IdLoader.getNextID("Lecture", "lec_id");
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading the next id");
            e.printStackTrace();
        }
        return "L001";
    }

    private boolean validateLectureFields(String lectureName, LocalDate date, LocalTime startTime, LocalTime endTime, String status) {
        if (!areRequiredFieldsFilled(lectureName, status, date)){
            AlertUtil.setErrorAlert("You must fill required fields (*)!");
            return false;
        }
        if (startTime.isAfter(endTime) || endTime.isBefore(startTime)){
            AlertUtil.setErrorAlert("Invalid time range: the start time must be earlier than the end time.");
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
