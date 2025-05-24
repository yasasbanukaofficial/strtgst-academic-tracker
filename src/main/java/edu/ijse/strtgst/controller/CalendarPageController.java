package edu.ijse.strtgst.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.*;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;
import com.calendarfx.view.page.YearPage;
import edu.ijse.strtgst.model.CalendarModel;
import edu.ijse.strtgst.util.AlertUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CalendarPageController implements Initializable {
    public VBox ancTimeline;
    private Calendar examCalendar = new Calendar("Exam");
    private Calendar lectureCalendar = new Calendar("Lecture");
    private Calendar eventsCalendar = new Calendar("Event");
    private CalendarSource calendarSource = new CalendarSource("My Calendar");
    private final DetailedWeekView weekView = new DetailedWeekView();
    private final DetailedDayView dayView = new DetailedDayView();
    private final MonthView monthView = new MonthView();
    private final YearView yearView = new YearView();

    private final CalendarModel calendarModel = new CalendarModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showWeekView(new ActionEvent());
        calendarSource.getCalendars().addAll(examCalendar, lectureCalendar, eventsCalendar);
        weekView.getCalendarSources().add(calendarSource);
        dayView.getCalendarSources().add(calendarSource);
        monthView.getCalendarSources().add(calendarSource);
        yearView.getCalendarSources().add(calendarSource);

        eventsInitializer();
    }

    public void navigateTo(Node node){
        ancTimeline.getChildren().clear();
        ancTimeline.getChildren().add(node);
    }

    public void setupView(DateControl view){
        ancTimeline.getStylesheets().add(getClass().getResource("/view/styles/popOver.css").toExternalForm());
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        view.setPrefSize(width, height);
        view.refreshData();
        view.requestLayout();
        UpdateThread.startThread(view);
        navigateTo(view);
    }

    public void showWeekView(ActionEvent actionEvent) {
        setupView(weekView);
    }

    public void showDayView(ActionEvent actionEvent) {
        setupView(dayView);
    }

    public void showMonthView(ActionEvent actionEvent) {
        setupView(monthView);
    }

    public void showYearView(ActionEvent actionEvent) {
        setupView(yearView);
    }

    private void eventsInitializer(){
        EventHandler<CalendarEvent> event = e -> handleEvent(e);
        examCalendar.addEventHandler(event);
        lectureCalendar.addEventHandler(event);
        eventsCalendar.addEventHandler(event);
    }

    private void handleEvent (CalendarEvent e) {
        Entry<?> entry = e.getEntry();
        try {
            if (!calendarModel.syncEntryWithDatabase(entry)) {
                AlertUtil.setErrorAlert("Error when adding an event to the calendar");
            }
        } catch (SQLException ex) {
            AlertUtil.setErrorAlert("Error when adding an event to the calendar in db");
            ex.printStackTrace();
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