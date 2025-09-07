package parser;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import tasklist.*;



public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    void makeDeadline() {
        Deadline dl = parser.deadlineTask("Deadline Task 1 /by 2025-12-12");
        assertEquals("Deadline Task 1", dl.getDescription());
        assertEquals("2025-12-12", dl.getDeadline());
    }

    @Test
    void missingArgumentsDeadline() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.deadlineTask("Deadline Task 1")
        );
        assertTrue(e.getMessage().toLowerCase().contains("please"));
    }

    @Test
    void makeEvent() {
        Event ev = parser.eventTask("Event Task 1 /from 2025-6-6 /to 2025-7-7");
        assertEquals("Event Task 1", ev.getDescription());
        assertEquals("2025-6-6", ev.getStartTime());
        assertEquals("2025-7-7", ev.getEndTime());
    }

    @Test
    void missingArgumentsEvent() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.eventTask("Event Task 1")
        );
        assertTrue(e.getMessage().toLowerCase().contains("please"));
    }

    @Test
    void makeTodo() {
        Todo t = parser.todoTask("Todo Task 1");
        assertEquals("Todo Task 1", t.getDescription());
    }

    @Test
    void missingArgumentsTodo() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> parser.todoTask("")
        );
        assertTrue(e.getMessage().toLowerCase().contains("please"));
    }

}
