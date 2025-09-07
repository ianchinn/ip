package storage;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import tasklist.*;

public class StorageTest {

    private final Storage storage = new Storage("build/test-task.txt");

    @Test
    void encodeTodo_markDone() {
        Task t = new Todo("Todo Task 1");
        t.markDone();
        String todoEncoded = storage.encode(t);
        assertEquals("T | X | Todo Task 1", todoEncoded);
    }

    @Test
    void encodeDeadline() {
        Task d = new Deadline("Deadline Task 1", "2025-1-1");
        String deadlineEncoded = storage.encode(d);
        assertEquals("D | 0 | Deadline Task 1 | by: | Jan 01 2025", deadlineEncoded);
    }

    @Test
    void encodeEvent() {
        Task e = new Event("Event Task 1", "2025-1-1", "2025-2-2");
        String eventEncoded = storage.encode(e);
        assertEquals("E | 0 | Event Task 1 | from: | Jan 01 2025 | to: | Feb 02 2025", eventEncoded);
    }

    @Test
    void decodeTodo() {
        String task = "T | X | Todo Task 1";
        Task decoded = storage.decode(task);
        assertTrue(decoded instanceof Todo);
        assertEquals("Todo Task 1", decoded.getDescription());
        assertEquals("X", decoded.getStatus());
    }

    @Test
    void decodeDeadline() {
        String task = "D | 0 | Deadline Task 1 | by: | 2025-1-1";
        Task decoded = storage.decode(task);
        Deadline d = (Deadline) decoded;
        assertTrue(d instanceof Deadline);
        assertEquals("Deadline Task 1", d.getDescription());
        assertEquals(" ", d.getStatus());
        assertEquals("2025-1-1", d.getDeadline());
    }

    @Test
    void decodeEvent() {
        String task = "E | 0 | Event Task 1 | from: | Jan 01 2025 | to: | Feb 02 2025";
        Task decoded = storage.decode(task);
        Event e = (Event) decoded;
        assertTrue(e instanceof Event);
        assertEquals("Event Task 1", e.getDescription());
        assertEquals(" ", e.getStatus());
        assertEquals("Jan 01 2025", e.getStartTime());
        assertEquals("Feb 02 2025", e.getEndTime());
    }
}
