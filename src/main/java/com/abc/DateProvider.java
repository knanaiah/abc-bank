package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    //Check for Null Pointer Exception
    public static DateProvider getInstance() throws NullPointerException {
        try {
            if (instance == null)
                instance = new DateProvider();
        } catch (NullPointerException npe) {
            instance = new DateProvider();
        }
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public int DayOfYear() { return Calendar.getInstance().get(Calendar.DAY_OF_YEAR); }
}
