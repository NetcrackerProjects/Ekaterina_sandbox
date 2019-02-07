package com.gigssandbox;

import com.gigssandbox.exceptions.DateParsingException;
import com.gigssandbox.exceptions.IncorrectDateValueException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

class StringToCalendarParser {
    Calendar parse(String dateText) throws DateParsingException, IncorrectDateValueException {
        int[] dateParams;
        int year, month, day;

        try {
            dateParams = Arrays.stream(dateText.trim().split("-")).mapToInt(Integer::parseInt).toArray();
            year = dateParams[0];
            month = dateParams[1];
            day = dateParams[2];

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new DateParsingException();
        }

        if (!isDayCorrect(year, month, day) || !isMonthCorrect(month)) {
            throw new IncorrectDateValueException();
        }

        return new GregorianCalendar(year, month - 1, day);
    }

    private boolean isMonthCorrect(int month) {
        return month >= 1 && month <= 12;
    }

    private boolean isDayCorrect(int year, int month, int day) {
        if (day < 1) {
            return false;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day <= 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return day <= 30;
            case 2: {
                return isLeap(year) ? day <= 29 : day <= 28;
            }
            default:
                return false;
        }
    }

    private boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
