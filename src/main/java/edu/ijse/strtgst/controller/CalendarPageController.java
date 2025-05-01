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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DetailedWeekView detailedWeekView = new DetailedWeekView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        detailedWeekView.setPrefSize(width, height);

        Thread thread = UpdateThread.getUpdateTimeThread(detailedWeekView);
        navigateTo(detailedWeekView);
    }

    public void navigateTo(Node node){
        ancTimeline.getChildren().clear();
        ancTimeline.getChildren().add(node);
    }

    public void showWeekView(ActionEvent actionEvent) {
        initialize(null, null);
    }

    public void showDayView(ActionEvent actionEvent) {
        DetailedDayView detailedDayView = new DetailedDayView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        detailedDayView.setPrefSize(width, height);

        Thread thread = UpdateThread.getUpdateTimeThread(detailedDayView);
        navigateTo(detailedDayView);
    }

    public void showMonthView(ActionEvent actionEvent) {
        MonthView monthView = new MonthView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        monthView.setPrefSize(width, height);

        Thread thread = UpdateThread.getUpdateTimeThread(monthView);
        navigateTo(monthView);
    }

    public void showYearView(ActionEvent actionEvent) {
        YearView yearView = new YearView();
        double width = ancTimeline.getPrefWidth() - 20.0;
        double height = ancTimeline.getPrefHeight() - 20.0;
        yearView.setPrefSize(width, height);

        Thread thread = UpdateThread.getUpdateTimeThread(yearView);
        navigateTo(yearView);
    }
}

class UpdateThread{
    private static Thread updateTimeThread;

    public static Thread getUpdateTimeThread(DetailedDayView view) {
        return startThread(view);
    }

    public static Thread getUpdateTimeThread(DetailedWeekView view) {
        return startThread(view);
    }

    public static Thread getUpdateTimeThread(MonthView view) {
        return startThread(view);
    }

    public static Thread getUpdateTimeThread(YearView view) {
        return startThread(view);
    }

    private static Thread startThread(DateControl control) {
        if (updateTimeThread == null){
            updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                    while (true) {
                        Platform.runLater(() -> {
                            control.setToday(LocalDate.now());
                            control.setTime(LocalTime.now());
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
        }
        return updateTimeThread;
    }
}