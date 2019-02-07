package com.gigssandbox;

import com.gigssandbox.exceptions.DateParsingException;
import com.gigssandbox.exceptions.IncorrectDateValueException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringToCalendarParserTest {
    private StringToCalendarParser parser;

    @Before
    public void setUp() {
        this.parser = new StringToCalendarParser();
    }

    @Test
    public void shouldReturnCalendarObjectWhenCorrectStringIsPassed() {
        String dateText = "2010-05-01";
        Calendar expectedDate = new GregorianCalendar(2010, Calendar.MAY, 1);

        assertDoesNotThrow(() -> {
            final Calendar actualDate = parser.parse(dateText);

            assertEquals(expectedDate, actualDate);
        });
    }

    @Test
    public void shouldThrowDateParsingExceptionWhenStringWithExtraSymbolIsPassed() {
        String dateTextWithExtraSymbol = "2010-a1-09";

        assertThrows(DateParsingException.class, () -> parser.parse(dateTextWithExtraSymbol));
    }

    @Test
    public void shouldThrowDateParsingExceptionWhenStringWithoutDateIsPassed() {
        String dateTextWithoutDay = "2011-10";

        assertThrows(DateParsingException.class, () -> parser.parse(dateTextWithoutDay));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenMonthIsIncorrect() {
        String dateWithIncorrectMonth = "2012-99-09";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(dateWithIncorrectMonth));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenDateInJanuaryIsIncorrect() {
        String incorrectJanuaryDate = "2012-01-32";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(incorrectJanuaryDate));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenDateInJuneIsIncorrect() {
        String incorrectJuneDate = "2012-06-31";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(incorrectJuneDate));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenDateInLeapYearInFebruaryIsIncorrect() {
        String incorrectFebruaryDate = "2012-02-30";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(incorrectFebruaryDate));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenDateInNonLeapYearInFebruaryIsIncorrect() {
        String incorrectFebruaryDate = "2011-02-29";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(incorrectFebruaryDate));
    }

    @Test
    public void shouldThrowIncorrectDateValueExceptionWhenDayOfDateIsLowerThanOne() {
        String dateWithIncorrectDay = "2011-02-00";

        assertThrows(IncorrectDateValueException.class, () -> parser.parse(dateWithIncorrectDay));
    }
}
