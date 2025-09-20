package parser;

/**
 * Parses through user input to see what command was given and stores the relevant data
 */
public class ParsedCommand {
    private final CommandType type;
    private final String details;
    private int index;
    private String updatedVal;

    /**
     * @brief           instantiates a new parsedCommand object that stores the command type and details
     * @param type      type of command as per listed under CommandType
     * @param details   all other details that came with the user input after command type was identified
     */
    public ParsedCommand(CommandType type, String details) {
        this.type = type;
        this.details = details == null ? ""
                                       : details.trim();
    }

    /**
     * @brief               overloaded ParsedCommand for updating tasks
     * @param type          type of command as per listed under CommandType
     * @param details       field the user wishes to update
     * @param index         the item on list the user wishes to update
     * @param updatedVal    the content the user wishes to edit with
     */
    public ParsedCommand(CommandType type, String details, int index, String updatedVal) {
        this.type = type;
        this.details = details == null ? ""
                : details.trim();
        this.index = index;
        this.updatedVal = updatedVal;
    }

    public CommandType getType() {
        return this.type;
    }

    public String getDetails() {
        return this.details;
    }

    public int getIndex() {
        return this.index;
    }

    public String getUpdatedVal() {
        return this.updatedVal;
    }
}
