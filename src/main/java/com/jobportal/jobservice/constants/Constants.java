package com.jobportal.jobservice.constants;

public class Constants {
    public static final String EMPTY_STRING = "";

    public static final int MINUTES_IN_DAY = 1440;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int MINUTES_IN_WEEK = MINUTES_IN_DAY * 7;
    public static final int MINUTES_IN_MONTH = MINUTES_IN_WEEK * 4;
    public static final int MINUTES_IN_YEAR = MINUTES_IN_MONTH * 12;

    public static final int[] timeTypes = { MINUTES_IN_HOUR, MINUTES_IN_DAY, MINUTES_IN_WEEK, MINUTES_IN_MONTH, MINUTES_IN_YEAR };
    public static final String[] timeTypesName = {"hour", "day", "week", "month", "year"};

    public static int DEFAULT_RESULTS_PER_PAGE = 15;

}
