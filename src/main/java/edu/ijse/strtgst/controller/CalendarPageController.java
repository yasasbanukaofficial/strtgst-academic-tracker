package edu.ijse.strtgst.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.*;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import edu.ijse.strtgst.model.CalendarModel;
import edu.ijse.strtgst.util.AlertUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarPageController implements Initializable {
    public VBox ancTimeline;
    public VBox ancAgendaView;
    public VBox ancChatBot;
    public TextFlow txtChatFlow;
    public TextField txtEnterMsg;
    public StackPane btnSendMsg;
    private Calendar examCalendar = new Calendar("Exam");
    private Calendar lectureCalendar = new Calendar("Lecture");
    private Calendar eventsCalendar = new Calendar("Event");
    private CalendarSource calendarSource = new CalendarSource("My Calendar");
    private final WeekPage weekView = new WeekPage();
    private final DetailedDayView dayView = new DetailedDayView();
    private final MonthPage monthView = new MonthPage();
    private final AgendaView agendaView = new AgendaView();

    private final CalendarModel calendarModel = new CalendarModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCalendarViews();
        eventsInitializer();
        loadAllEntries();
    }

    public void navigateTo(VBox anchor, Node node){
        anchor.getChildren().clear();
        anchor.getChildren().add(node);
    }

    public void setupView(VBox anchor, DateControl view){
        anchor.getStylesheets().add(getClass().getResource("/view/styles/popOver.css").toExternalForm());
        double width = anchor.getPrefWidth() - 20.0;
        double height = anchor.getPrefHeight() - 20.0;
        view.setPrefSize(width, height);
        view.refreshData();
        view.requestLayout();
        UpdateThread.startThread(view);
        navigateTo(anchor, view);
    }

    public void showWeekView(ActionEvent actionEvent) {
        setupView(ancTimeline, weekView);
    }

    public void showDayView(ActionEvent actionEvent) {
        setupView(ancTimeline, dayView);
    }

    public void showMonthView(ActionEvent actionEvent) {
        setupView(ancTimeline, monthView);
    }

    public void showAgendaView(ActionEvent actionEvent) {setupView(ancAgendaView, agendaView);}

    private void eventsInitializer(){
        EventHandler<CalendarEvent> event = e -> handleEvent(e);
        examCalendar.addEventHandler(event);
        lectureCalendar.addEventHandler(event);
        eventsCalendar.addEventHandler(event);
    }

    private void handleEvent (CalendarEvent e) {
        Entry<?> entry = e.getEntry();
        try {
            if (e.getCalendar() == null){
                if (!calendarModel.deleteEntry((e.getOldCalendar().getName()), entry.getId())){
                    AlertUtil.setErrorAlert("Error when deleting entry from the database");
                }
            } else {
                if (!calendarModel.syncEntryWithDatabase(entry)) {
                    AlertUtil.setErrorAlert("Error when modifying an event to the calendar");
                }
            }
        } catch (Exception ex) {
            AlertUtil.setErrorAlert("Error when modifying an event to the calendar in db");
            ex.printStackTrace();
        }
    }

    private void loadAllEntries() {
        try {
            loadEntriesForCalendar(examCalendar, calendarModel.getAllExamEntries());
            loadEntriesForCalendar(lectureCalendar, calendarModel.getAllLectureEntries());
            loadEntriesForCalendar(eventsCalendar, calendarModel.getAllEventEntries());
            refreshViews();
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading all entries from the calendar");
            e.printStackTrace();
        }
    }

    private void loadEntriesForCalendar(Calendar calendar, ArrayList<Entry<?>> entries) {
        for (Entry<?> entry : entries) {
            calendar.addEntry(entry);
        }
    }

    private void refreshViews() {
        weekView.refreshData();
        dayView.refreshData();
        monthView.refreshData();
    }

    private void setupCalendarViews() {
        showDayView(new ActionEvent());
        showAgendaView(new ActionEvent());
        setupCalendarStyles();
        calendarSource.getCalendars().addAll(lectureCalendar, eventsCalendar, examCalendar);

        weekView.getCalendarSources().clear();
        weekView.getCalendarSources().add(calendarSource);

        dayView.getCalendarSources().clear();
        dayView.getCalendarSources().add(calendarSource);

        monthView.getCalendarSources().clear();
        monthView.getCalendarSources().add(calendarSource);

        agendaView.getCalendarSources().clear();
        agendaView.getCalendarSources().add(calendarSource);

        monthView.setShowDate(false);
        monthView.setShowNavigation(false);
        weekView.setShowDate(false);
    }

    private void setupCalendarStyles() {
        examCalendar.setStyle(Calendar.Style.STYLE5);
        lectureCalendar.setStyle(Calendar.Style.STYLE2);
        eventsCalendar.setStyle(Calendar.Style.STYLE4);
    }

    public void sendMessage(MouseEvent mouseEvent) {
        txtChatFlow.getChildren().clear();
        try {
            String userInput = txtEnterMsg.getText();
            if (userInput.trim().equals("")) {
                AlertUtil.setErrorAlert("Please enter a valid entry message to send");
                return;
            }
            boolean isSynced = calendarModel.syncEntryByAi(ChatBot.getResponse(userInput));
            String response = isSynced == true ? "Your Event is successfully added. Add some more!" : "Failed to add an Event. Try with an internet connection";
            Text userTxt = new Text("User:      " + userInput + "\n");
            Text responseTxt = new Text("Chat:      " + response);
            txtChatFlow.getChildren().add(userTxt);
            txtChatFlow.getChildren().add(responseTxt);
            txtEnterMsg.setText("");
            loadAllEntries();
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when sending the message " + e.getMessage());
        }

    }

    private void disableButtons() {
        btnSendMsg.setDisable(true);
    }
}

class ChatBot {
    private static final String GOOGLE_API_KEY = "AIzaSyAboDpPm77ZEmlnGyyRK-Ta518yv6e9p9Q";

    public static String getResponse(String userInput) {
        System.setProperty("GOOGLE_API_KEY", GOOGLE_API_KEY);

        try {
            Client client = new Client.Builder().apiKey(GOOGLE_API_KEY).build();
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    """
                            You are an AI that only returns plain SQL INSERT statements — no code blocks, no labels, no explanations, no markdown formatting.
                            
                            Today's Date is """ + LocalDate.now() + """

                            There are 3 tables in the MySQL database:
                            
                            -- Table Lecture  
                            CREATE TABLE lecture (  
                                lec_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (lec_id)  
                            );
                            
                            -- Table Exam  
                            CREATE TABLE exam (  
                                exam_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (exam_id)  
                            );
                            
                            -- Table Event  
                            CREATE TABLE event (  
                                event_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (event_id)  
                            );
                            
                            Instructions:
                            - Remember the queries you add will be retrieved by the project's model to apply it in CalendarFx's views
                            - Only generate SQL INSERT INTO statements.
                            - Do not wrap the output in triple backticks (```), do not prefix with “sql” or any labels.
                            - Do not return any comments or explanations.
                            - Do not return anything if the user input is casual or unrelated (e.g., “Hello”, “How are you”).
                            - Convert expressions like “Monday at 9pm” to full `YYYY-MM-DD HH:MM:SS` datetime.
                            - Do NOT use SQL functions like CONCAT, just give the final datetime.
                            - Set `full_day = TRUE` if the user says "full day" or similar.
                            - Default location is `'SCHOOL'` if not mentioned.
                            - Always generate a random ID for each row (e.g., `'EXM123456'`, `'EVT987654'`, `'LEC456789'`).
                            - Do not generate any statement if any of the details are missing because the database going to add the default values. Don't even add null to it.
                            - Do not pass null. If nay required value is missing just dont add that single data to the db since the db will add their default values.
                            - If user said something it will repeat or occur this way add it to the repeat_type column this way only for 
                              daily -> RRULE:FREQ=DAILY
                              weekly -> RRULE:FREQ=WEEKLY
                              monthly -> RRULE:FREQ=MONTHLY
                              yearly -> RRULE:FREQ=YEARLY
                              if there's nothing user mentioned about it put null only nothing else. 
                            - If the user didnt mention about what the exam, lecture or the event is just put a random name + the table name as the title.
                            
                            Now generate a valid SQL INSERT statement based only on the following user input. Do not wrap it, label it, or explain it:
                            
                            """ + userInput,
                    null
            );
            return response.text();
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when generating the query through AI");
            return "Error:  " + e.getMessage();
        }
    }
}

class UpdateThread{
    private static Thread updateTimeThread;
    private static volatile boolean running = false;
    private static DateControl currentView;

    public static Thread startThread(DateControl view) {
        currentView = view;
        if (updateTimeThread == null){
            running = true;
            updateTimeThread = new Thread("Calendar: Update Time"){
                @Override
                public void run() {
                    while (running){
                        Platform.runLater(() -> {
                            if (currentView != null && currentView.getScene() != null){
                                currentView.setDate(LocalDate.now());
                                currentView.setTime(LocalTime.now());
                            }
                        });

                        try{
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            updateTimeThread.setPriority(Thread.MIN_PRIORITY);
            updateTimeThread.setDaemon(true);
            updateTimeThread.start();
        }
        return updateTimeThread;
    }

    public static void stopThread() {
        running = false;
        if (updateTimeThread != null){
            updateTimeThread.interrupt();
            updateTimeThread = null;
        }
    }
}