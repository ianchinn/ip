package parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * DateParser takes in a list of accepted date-time formats and returns a standard format.
 */
public class DateParser {
    private static final List<DateTimeFormatter> FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/M/yyyy"),
            DateTimeFormatter.ofPattern("dd-M-yyyy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy/MM/d"),
            DateTimeFormatter.ofPattern("yyyy/M/dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-M-dd"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy-MM-d"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd/M/yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd/M/yyyy HHmm")
    );

    private static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * @brief           formats user input of date-time into a standard form to display
     * @param timing    string of the date/time detail of tasks
     * @return          a standard form for date-time for chatbot to display
     */
    public static String formatDate(String timing) {
        for (DateTimeFormatter fmt : FORMATS) {
            try {
                LocalDate date = LocalDate.parse(timing, fmt);
                return date.format(OUTPUT);
            } catch (DateTimeParseException ignore) { }
            try {
                LocalDateTime dt = LocalDateTime.parse(timing, fmt);
                return dt.format(OUTPUT);
            } catch (DateTimeParseException ignore) { }
        }
        return timing;
    }
}
