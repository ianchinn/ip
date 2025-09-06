package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import parser.DateParser;

public class Deadline extends Task {

    String deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.formatDate(this.deadline) + ")";
    }
}