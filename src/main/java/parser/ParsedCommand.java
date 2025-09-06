package parser;

public class ParsedCommand {
    private final CommandType type;
    private final String details;

    public ParsedCommand(CommandType type, String details) {
        this.type = type;
        this.details = details == null ? ""
                                       : details.trim();
    }

    public CommandType getType() {
        return this.type;
    }

    public String getDetails() {
        return this.details;
    }
}
