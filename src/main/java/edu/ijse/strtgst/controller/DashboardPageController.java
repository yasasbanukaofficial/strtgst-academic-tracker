package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.model.CalendarModel;
import edu.ijse.strtgst.model.ChatBotModel;
import edu.ijse.strtgst.model.TaskModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.PromptBuilder;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {
    public AnchorPane ancDashboard;
    public Label labelTotalTasks;
    public Label labelTotalAssignments;
    public Label labelEventsToday;
    public Label labelGpaCalculation;
    public Label labelLecturesToday;

    public VBox ancChatBot;
    public TextField txtEnterMsg;
    public TextFlow txtChatFlow;
    public StackPane btnSendMsg;

    private final TaskModel taskModel = new TaskModel();
    private final AssignmentModel assignmentModel = new AssignmentModel();
    private final CalendarModel calendarModel = new CalendarModel();
    private StringBuilder previousMsg = new StringBuilder();
    private final ChatBotModel chatBotModel = new ChatBotModel();
    private final PromptBuilder promptBuilder = new PromptBuilder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countTotal();
    }

    public void visitTasksPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.TASK);
    }

    public void visitEventsPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.CALENDAR);
    }

    public void visitAssignmentsPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.ASSIGNMENT);
    }

    public void visitLecturesPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.CALENDAR);
    }

    private void countTotal() {
        try {
            labelTotalTasks.setText(taskModel.getPendingOrOverdueTaskCount());
            labelTotalAssignments.setText(assignmentModel.getPendingOrOverdueAssignmentCount());
            labelEventsToday.setText(calendarModel.getAllFutureEntries("Event"));
            labelLecturesToday.setText(calendarModel.getAllFutureEntries("Lecture"));
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when fetching total");
            e.printStackTrace();
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
}
