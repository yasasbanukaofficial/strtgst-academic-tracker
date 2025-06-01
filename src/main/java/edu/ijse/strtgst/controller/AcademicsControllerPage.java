package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.AcademicDto;
import edu.ijse.strtgst.model.AcademicModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.View;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AcademicsControllerPage implements Initializable {
    public Label labelUpcomingEvent;
    public Label labelUpcomingEventDate;
    public Label labelUpcomingExam;
    public Label labelUpcomingExamDate;
    public Label labelUpcomingLecture;
    public Label labelUpcomingLectureDate;
    public Label labelEventLocation;
    public Label labelExamLocation;
    public Label labelLectureLocation;

    private final AcademicModel academicModel = new AcademicModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupData();
    }

    private void setupData() {
        try {
            setupSection(academicModel.getRecentDetails("Exam"),
                    labelUpcomingExam, labelUpcomingExamDate, labelExamLocation,
                    "Exam");

            setupSection(academicModel.getRecentDetails("Lecture"),
                    labelUpcomingLecture, labelUpcomingLectureDate, labelLectureLocation,
                    "Lecture");

            setupSection(academicModel.getRecentDetails("Event"),
                    labelUpcomingEvent, labelUpcomingEventDate, labelEventLocation,
                    "Event");

        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading data to display");
            e.printStackTrace();
        }
    }

    private void setupSection(AcademicDto dto, Label titleLabel, Label dateLabel, Label locationLabel, String tableName) {
        if (dto == null) {
            titleLabel.setText("No " + tableName);
            dateLabel.setText("");
            locationLabel.setText("");
        } else {
            titleLabel.setText(dto.getTitle());

            LocalDateTime date = dto.getFromDateTime().toLocalDateTime();
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                dateLabel.setText(date.format(formatter));
            }

            String location = dto.getLocation();
            if (location != null) {
                locationLabel.setText(location);
            }
        }
    }

    public void showOptionsPage(MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(View.ACADEMIC_CHOICE.getPath()));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
