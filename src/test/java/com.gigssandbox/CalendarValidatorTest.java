package com.gigssandbox;

import com.gigssandbox.exceptions.DateParsingException;
import com.gigssandbox.exceptions.IncorrectDateValueException;
import org.junit.Before;
import org.junit.Test;

public class CalendarValidatorTest {
    private CalendarValidator validator;

    @Before
    public void setUp() {
        this.validator = new CalendarValidator();
    }

    @Test
    public void shouldNotThrowWhenCorrectStringIsPassed() throws Exception {
        String dateText = "2010-05-01";

        validator.validate(dateText);
    }

    @Test(expected = DateParsingException.class)
    public void shouldThrowWhenStringWithExtraSymbolIsPassed() throws Exception {
        String dateTextWithExtraSymbol = "2010-a1-09";

        validator.validate(dateTextWithExtraSymbol);
    }

    @Test(expected = DateParsingException.class)
    public void shouldThrowWhenStringWithoutDayIsPassed() throws Exception {
        String dateTextWithoutDay = "2011-10";

        validator.validate(dateTextWithoutDay);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenMonthIsIncorrect() throws Exception {
        String dateWithIncorrectMonth = "2012-99-09";

        validator.validate(dateWithIncorrectMonth);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenDateInJanuaryIsIncorrect() throws Exception {
        String incorrectJanuaryDate = "2012-01-32";

        validator.validate(incorrectJanuaryDate);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenDateInJuneIsIncorrect() throws Exception {
        String incorrectJuneDate = "2012-06-31";

        validator.validate(incorrectJuneDate);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenDateInLeapYearIsIncorrect() throws Exception {
        String incorrectDate = "2012-02-30";

        validator.validate(incorrectDate);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenDateInNonLeapYearIsIncorrect() throws Exception {
        String incorrectDate = "2011-02-29";

        validator.validate(incorrectDate);
    }

    @Test(expected = IncorrectDateValueException.class)
    public void shouldThrowWhenDayOfDateIsLowerThanOne() throws Exception {
        String dateWithIncorrectDay = "2011-02-00";

        validator.validate(dateWithIncorrectDay);
    }
}
