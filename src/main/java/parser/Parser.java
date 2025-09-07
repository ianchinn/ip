package parser;

import tasklist.*;

public class Parser {

    public ParsedCommand parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new ParsedCommand(CommandType.UNKNOWN, "");
        }
        String trimmedLine = line.trim();

        String[] parts = trimmedLine.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String details = parts.length > 1
                        ? parts[1] : "";
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
            default:
                return new ParsedCommand(CommandType.UNKNOWN, details);
        }
    }

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

    public Todo todoTask(String details) {
        if (details.isEmpty()) {
            throw new IllegalArgumentException("Please enter the task!");
        }
        return new Todo(details);
    }
}
