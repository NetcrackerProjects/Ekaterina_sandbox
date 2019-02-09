package com.gigssandbox;

import com.gigssandbox.exceptions.DateParsingException;
import com.gigssandbox.exceptions.IncorrectDateValueException;
import java.util.Arrays;
import java.util.Calendar;

class CalendarValidator {
    void validate(String dateText) throws DateParsingException, IncorrectDateValueException {
        int[] dateParams;
        int year, month, day;

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);

        try {
            dateParams = Arrays.stream(dateText.trim().split("-")).mapToInt(Integer::parseInt).toArray();
            year = dateParams[0];
            month = dateParams[1];
            day = dateParams[2];

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new DateParsingException();
        }

        calendar.set(year, month - 1, day);

        try {
            calendar.getTime();

        } catch (IllegalArgumentException e) {
            throw new IncorrectDateValueException();
        }
    }
}
