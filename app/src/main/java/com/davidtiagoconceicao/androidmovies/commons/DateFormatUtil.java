package com.davidtiagoconceicao.androidmovies.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for date format.
 * <p>
 * Created by david on 22/02/17.
 */

public final class DateFormatUtil {
    private static final String SERVER_DATE_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat SERVER_FORMATTER =
            new SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault());

    public static Date parseDate(String date) {
        try {

            return SERVER_FORMATTER.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            //TODO better handle exception
            return null;
        }
    }
}
