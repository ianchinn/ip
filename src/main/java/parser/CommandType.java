package parser;

/**
 * List of all possible command types the user can give
 */
public enum CommandType {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    FIND,
    UPDATE,
    UNKNOWN
}
