package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {

    protected String description;
    protected Boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatus() {
        if (isDone == true) {
            return "X";
        } else {
            return " ";
        }
    }

    public boolean getDone() {
        return isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatus(), this.description);
    }
}