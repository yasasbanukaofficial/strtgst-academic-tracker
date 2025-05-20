package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.LectureDto;
import edu.ijse.strtgst.model.LectureModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.IdLoader;
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

    ObservableList <String> hours = FXCollections.observableArrayList();
    private final LectureModel lectureModel = new LectureModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i <= 24; i++) {
            String formattedHr = String.format("%02d", i);
            cmbStartHour.getItems().add(formattedHr);
            cmbEndHour.getItems().add(formattedHr);
        }
        for (int i = 0; i < 60; i++) {
            String formattedMin = String.format("%02d", i);
            cmbStartMinute.getItems().add(formattedMin);
            cmbEndMinute.getItems().add(formattedMin);
        }

        LocalTime currentTime = LocalTime.now();
        int currentHr = currentTime.getHour();
        int currentMin = currentTime.getMinute();

        cmbStartHour.setValue(String.valueOf(currentHr));
        cmbEndHour.setValue(String.valueOf(currentHr + 1));
        if (currentMin < 10){
            cmbEndMinute.setValue(0 + "" + String.valueOf(currentMin));
            cmbStartMinute.setValue(0 + "" + String.valueOf(currentMin));
        }
    }

    public void cancelAdding(ActionEvent event) {
    }

    public void addLecture(ActionEvent event) {
        String lecId = loadNextId();
        String subId = getSubId(cmbSubject.getValue());
        LocalTime startTime = LocalTime.of(Integer.parseInt(cmbStartHour.getValue()), Integer.parseInt(cmbStartMinute.getValue()));
        LocalTime endTime = LocalTime.of(Integer.parseInt(cmbEndHour.getValue()), Integer.parseInt(cmbEndMinute.getValue()));
        String status = getStatus(dpDate.getValue());

        LectureDto lectureDto = new LectureDto(
                lecId,
                subId,
                txtLectureName.getText(),
                dpDate.getValue(),
                startTime,
                endTime,
                status
        );
    }

    private String getSubId(String subName) {
        String subId = IdLoader.fetchIdByName()
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
}
