package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import parser.DateParser;
public class Event extends Task {

    String startTime;
    String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.formatDate(this.startTime) + " to: " + DateParser.formatDate(this.endTime) + ")";
    }

}