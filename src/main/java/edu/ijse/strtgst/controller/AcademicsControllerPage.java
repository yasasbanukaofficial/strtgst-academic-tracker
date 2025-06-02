package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.AcademicDto;
import edu.ijse.strtgst.model.AcademicModel;
import edu.ijse.strtgst.model.ChatBotModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.PromptBuilder;
import edu.ijse.strtgst.util.View;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    public Label labelTotalTasks;

    public VBox ancChatBot;
    public TextField txtEnterMsg;
    public TextFlow txtChatFlow;
    public StackPane btnSendMsg;

    public VBox ancQueryView;
    public Label lblDailyQuotes;
    public TextFlow txtRespondFlow;
    public TextField txtEnterQuery;
    public StackPane btnSendQuery;

    private final AcademicModel academicModel = new AcademicModel();
    private StringBuilder previousMsg = new StringBuilder();
    private final ChatBotModel chatBotModel = new ChatBotModel();
    private final PromptBuilder promptBuilder = new PromptBuilder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupData();
        randomReminderGenerator();
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

    public void sendMessage(MouseEvent mouseEvent) {
        txtChatFlow.getChildren().clear();
        String userInput = txtEnterMsg.getText();
        if (userInput.trim().equals("")) {
            AlertUtil.setErrorAlert("Please enter a valid entry message to send");
            return;
        }
        String response = chatBotModel.getResponse(promptBuilder.askAboutStudies(userInput, previousMsg));
        Text userTxt = new Text("User:      " + userInput + "\n");
        Text responseTxt = new Text("Chat:      " + response);
        txtChatFlow.getChildren().add(userTxt);
        txtChatFlow.getChildren().add(responseTxt);
        previousMsg.append(userInput).append("\n");
        txtEnterMsg.setText("");
    }

    private void randomReminderGenerator() {
        lblDailyQuotes.setText(chatBotModel.getResponse(promptBuilder.reminderGenerator()));
    }

    public void sendQuery(MouseEvent mouseEvent) {
        txtRespondFlow.getChildren().clear();
        try {
            String userInput = txtEnterQuery.getText();
            if (userInput.trim().equals("")) {
                AlertUtil.setErrorAlert("Please enter a valid query message to send");
                return;
            }
            String aiResponse = chatBotModel.getResponse(promptBuilder.buildSqlInsertAcademicsPrompt(userInput));
            boolean isValid = aiResponse != null && aiResponse.trim().toLowerCase().startsWith("insert into");

            String response;
            if (isValid) {
                boolean isSynced = academicModel.syncEntryByAi(aiResponse);
                response = isSynced ?
                        "Your subject/grade is successfully added. Add some more!" :
                        "Failed to add an event. Try with a stable internet connection.";
            } else {
                response = "Sorry, I couldn’t understand that. Please describe an entry like: “Add the subject named Maths which has total marks of 75.”";
            }
            Text userTxt = new Text("User:      " + userInput + "\n");
            Text responseTxt = new Text("Response:  " + response);
            txtRespondFlow.getChildren().add(userTxt);
            txtRespondFlow.getChildren().add(responseTxt);
            previousMsg.append(userInput).append("\n");
            txtEnterQuery.setText("");
        } catch (Exception e){
            AlertUtil.setErrorAlert("Error when sending the message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
