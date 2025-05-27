package edu.ijse.strtgst.model;

import com.calendarfx.model.Entry;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarModel {
    private final String [] calendarTableNames = {"Exam", "Lecture", "Event"};
    private final String [] calendarTableIdColumns = {"exam_id", "lec_id", "event_id"};

    public boolean syncEntryWithDatabase(Entry<?> entry) throws SQLException {
        boolean foundInOldTable = false;
        if (entry == null || entry.getCalendar() == null) {
            return false;
        }

        String calendarName = entry.getCalendar().getName();
        String entryId = entry.getId();

        for (int i = 0; i < calendarTableNames.length; i++) {
            String tableName = calendarTableNames[i];
            String idColumn = calendarTableIdColumns[i];

            ResultSet rst = CrudUtil.execute(
                    "SELECT 1 FROM " + tableName + " WHERE " + idColumn + " = ?", entryId
            );

            if (rst.next()) {
                if (calendarName.equalsIgnoreCase(tableName)) {
                    CrudUtil.execute(
                            "UPDATE " + tableName + " SET title = ?, location = ?, full_day = ?, from_date = ?, to_date = ?, repeat_type = ? WHERE " + idColumn + " = ?",
                            entry.getTitle(),
                            entry.getLocation(),
                            entry.isFullDay(),
                            entry.getStartAsLocalDateTime(),
                            entry.getEndAsLocalDateTime(),
                            entry.getRecurrenceRule(),
                            entryId
                    );
                    foundInOldTable = true;
                } else {
                    CrudUtil.execute("DELETE FROM " + tableName + " WHERE " + idColumn + " = ?", entryId);
                }
            }
        }

        if (!foundInOldTable) {
            for (int i = 0; i < calendarTableNames.length + 1; i++) {
                if (calendarName.equalsIgnoreCase(calendarTableNames[i])) {
                    CrudUtil.execute(
                            "INSERT INTO " + calendarTableNames[i] + " VALUES (?, ?, ?, ?, ?, ?, ?)",
                            entryId,
                            entry.getTitle(),
                            entry.getLocation(),
                            entry.isFullDay(),
                            entry.getStartAsLocalDateTime(),
                            entry.getEndAsLocalDateTime(),
                            entry.getRecurrenceRule()
                    );
                    return true;
                }
            }
        }

        return true;
    }

    public ArrayList<Entry<?>> getAllExamEntries() throws SQLException {
        return getEntriesFromTable("Exam");
    }

    public ArrayList<Entry<?>> getAllLectureEntries() throws SQLException {
        return getEntriesFromTable("Lecture");
    }

    public ArrayList<Entry<?>> getAllEventEntries() throws SQLException {
        return getEntriesFromTable("Event");
    }

    private ArrayList<Entry<?>> getEntriesFromTable(String tableName) throws SQLException {
        ArrayList<Entry<?>> entries = new ArrayList<>();
        try (ResultSet rst = CrudUtil.execute("SELECT * FROM " + tableName)) {
            while (rst.next()) {
                Entry<?> entry = new Entry<>();
                entry.setId(rst.getString(1));
                entry.setTitle(rst.getString(2));
                entry.setLocation(rst.getString(3));
                entry.setFullDay(rst.getBoolean(4));
                entry.setInterval(
                        rst.getTimestamp(5).toLocalDateTime(),
                        rst.getTimestamp(6).toLocalDateTime()
                );
                entry.setRecurrenceRule(rst.getString(7));
                entries.add(entry);
            }
        }
        return entries;
    }

    public boolean deleteEntry(String tableName, String id) throws SQLException {
        String idColumn = tableName.equalsIgnoreCase("Exam") ? "exam_id" : tableName.equalsIgnoreCase("Lecture") ? "lec_id" : "event_id";
        return CrudUtil.execute("DELETE FROM " + tableName +" WHERE " + idColumn + " = ?", id);
    }
}
