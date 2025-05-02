package edu.ijse.strtgst.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarViewController implements Initializable {
    public VBox ancCalendarView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ancCalendarView.getChildren().clear();

        Calendar calendar = new Calendar<>("Events");
        calendar.setStyle(Calendar.Style.STYLE2);
        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().add(calendar);

        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setPrefSize(1600, 1000);

        ancCalendarView.getChildren().add(calendarView);

    }
}
