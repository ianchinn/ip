package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import parser.DateParser;

public class Deadline extends Task {

    String deadline;

    /**
     * @brief           constructor for a deadline task
     * @param desc      deadline task name
     * @param deadline  deadline of the task
     */
    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    /**
     * @brief   getter for task deadline
     * @return  deadline as a string
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     *
     * @param newDeadline
     */
    public void setDeadline(String newDeadline) {
        this.deadline = newDeadline;
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.formatDate(this.deadline) + ")";
    }
}