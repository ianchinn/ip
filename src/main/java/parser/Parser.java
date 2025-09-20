package parser;

import com.sun.javafx.image.IntPixelGetter;
import tasklist.*;

import java.util.Arrays;

/**
 * Parser handles the input strings and identifies the command type as well as remaining details
 */
public class Parser {

    /**
     * @brief       takes in user input and parses through to identify command type and details
     * @param line  user input, with command given at the start
     * @return      a ParsedCommand object with the command type identified and details separated
     */
    public ParsedCommand parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new ParsedCommand(CommandType.UNKNOWN, "");
        }
        String trimmedLine = line.trim();

        String[] parts = trimmedLine.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String details = parts.length > 1
                        ? parts[1].trim() : "";
        switch (cmd) {
            case "bye":
                return new ParsedCommand(CommandType.BYE, details);
            case "list":
                return new ParsedCommand(CommandType.LIST, details);
            case "mark":
                return new ParsedCommand(CommandType.MARK, details);
            case "unmark":
                return new ParsedCommand(CommandType.UNMARK, details);
            case "todo":
                return new ParsedCommand(CommandType.TODO, details);
            case "deadline":
                return new ParsedCommand(CommandType.DEADLINE, details);
            case "event":
                return new ParsedCommand(CommandType.EVENT, details);
            case "delete":
                return new ParsedCommand(CommandType.DELETE, details);
            case "find":
                if (details.isEmpty()) {
                    throw new IllegalArgumentException("Please enter what you want to find!");
                }
                return new ParsedCommand(CommandType.FIND, details);
            case "update":
                String[] upd = details.split(" ", 3);
                if (upd.length < 3) {
                    throw new IllegalArgumentException("Usage: update index /field value");
                }
                int index = Integer.parseInt(upd[0]);
                String updateField = upd[1];
                String field;
                if (updateField.equals("/name")) {
                    field = "name";
                } else if (updateField.equals("/by")) {
                    field = "by";
                } else if (updateField.equals("/from")) {
                    field = "from";
                } else if (updateField.equals("/to")) {
                    field = "to";
                } else {
                    throw new IllegalArgumentException("Not a supported command!");
                }
                String val = upd[2];
                return new ParsedCommand(CommandType.UPDATE, field, index, val);

            default:
                return new ParsedCommand(CommandType.UNKNOWN, details);
        }
    }

    /**
     * @brief           creates deadline task
     * @param details   string containing the deadline after "/by"
     * @return          deadline task with its deadline as specified by user
     */
    public Deadline deadlineTask(String details) {
        String[] deadline = details.split("/by", 2);
        if (deadline.length < 2) {
            throw new IllegalArgumentException("Please enter the task!");
        }
        String detail = deadline[0].trim();
        String time = deadline[1].trim();
        if (detail.isEmpty() || time.isEmpty()) {
            throw new IllegalArgumentException("Please enter deadline task details!");
        }
        return new Deadline(detail, time);
    }

    /**
     * @brief           creates event task
     * @param details   string containing the task duration
     * @return          event task with its duration as specified by user
     */
    public Event eventTask(String details) {
        String[] eventTime = details.split("/from", 2);
        if (eventTime.length < 2) {
            throw new IllegalArgumentException("Please enter all relevant details for the event!");
        }
        String eventDetail = eventTime[0].trim();
        String[] fromTo = eventTime[1].trim().split("/to", 2);
        String from = fromTo[0].trim();
        String to = fromTo[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Please ensure the timings are given!");
        }
        return new Event(eventDetail, from, to);
    }

    /**
     * @brief           creates a todo task
     * @param details   task name
     * @return          a todo task
     */
    public Todo todoTask(String details) {
        if (details.isEmpty()) {
            throw new IllegalArgumentException("Please enter the task!");
        }
        return new Todo(details);
    }

}
