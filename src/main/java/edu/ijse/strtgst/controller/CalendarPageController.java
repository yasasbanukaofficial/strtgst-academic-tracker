package edu.ijse.strtgst.controller;

import com.calendarfx.view.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CalendarPageController implements Initializable {
    public VBox ancTimeline;
    private DetailedWeekView detailedWeekView;
    private DetailedDayView detailedDayView;
    private MonthView monthView;
    private YearView yearView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showWeekView(new ActionEvent());
    }

    public void navigateTo(Node node){
        ancTimeline.getChildren().clear();
        ancTimeline.getChildren().add(node);
    }

    public void setupView(String type){
        DateControl view;
        switch (type){
            case "week":
                view = new DetailedWeekView();
                break;
            case "month":
                view = new MonthView();
                break;
            case "year":
                view = new YearView();
                break;
            default:
                view = new DetailedDayView();
                break;
        }

        ancTimeline.getStylesheets().add(getClass().getResource("/view/styles/popOver.css").toExternalForm());
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        view.setPrefSize(width, height);

        UpdateThread.startThread(view);
        navigateTo(view);
    }

    public void showWeekView(ActionEvent actionEvent) {
        setupView("week");
    }

    public void showDayView(ActionEvent actionEvent) {
        setupView("day");
    }

    public void showMonthView(ActionEvent actionEvent) {
        setupView("month");
    }

    public void showYearView(ActionEvent actionEvent) {
        setupView("year");
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
                            sleep(10000);
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