package edu.ijse.strtgst.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CalendarPageController implements Initializable {
    public VBox ancTimeline;

    public void navigateTo(Node node){
        ancTimeline.getChildren().clear();
        ancTimeline.getChildren().add(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DetailedWeekView detailedWeekView = new DetailedWeekView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        detailedWeekView.setPrefSize(width, height);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        detailedWeekView.setToday(LocalDate.now());
                        detailedWeekView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
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
        navigateTo(detailedWeekView);
    }

    public void showWeekView(ActionEvent actionEvent) {
        initialize(null, null);
    }

    public void showDayView(ActionEvent actionEvent) {
        DetailedDayView detailedDayView = new DetailedDayView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        detailedDayView.setPrefSize(width, height);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        detailedDayView.setToday(LocalDate.now());
                        detailedDayView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
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
        navigateTo(detailedDayView);
    }

    public void showMonthView(ActionEvent actionEvent) {
        MonthView monthView = new MonthView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        monthView.setPrefSize(width, height);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        monthView.setToday(LocalDate.now());
                        monthView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
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
        navigateTo(monthView);
    }

    public void showYearView(ActionEvent actionEvent) {
        YearView yearView = new YearView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        yearView.setPrefSize(width, height);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        yearView.setToday(LocalDate.now());
                        yearView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
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
        navigateTo(yearView);
    }
}
