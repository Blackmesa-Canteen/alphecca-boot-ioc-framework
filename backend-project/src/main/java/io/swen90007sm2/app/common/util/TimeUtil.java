package io.swen90007sm2.app.common.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @author johnniang https://github.com/halo-dev/halo-admin
 * @author guqing https://github.com/halo-dev/halo-admin
 *
 * @description util for time
 * @create 2022-08-03 21:50
 */
public class TimeUtil {

    /**
     * default start day from monday
     */
    private static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY;

    /**
     * get current Date
     * @return current date
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Converts from date into the calendar instance.
     *
     * @param date date instance must not be null
     * @return calendar instance
     */
    public static Calendar convertDateToCalender(Date date) {
        Assert.notNull(date, "Date must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * given a date, add a time period, then get result date obj
     * @param date original date
     * @param time time needs to be added
     * @param timeUnit unit
     * @return result date after addition
     */
    public static Date add(Date date, long time, TimeUnit timeUnit) {
        Assert.notNull(date, "Date must not be null");
        Assert.isTrue(time >= 0, "Addition time must not be less than 1");
        Assert.notNull(timeUnit, "Time unit must not be null");

        Date result;

        int timeIntValue;

        if (time > Integer.MAX_VALUE) {
            timeIntValue = Integer.MAX_VALUE;
        } else {
            timeIntValue = Long.valueOf(time).intValue();
        }

        // Calc the expiry time
        switch (timeUnit) {
            case DAYS:
                result = DateUtils.addDays(date, timeIntValue);
                break;
            case HOURS:
                result = DateUtils.addHours(date, timeIntValue);
                break;
            case MINUTES:
                result = DateUtils.addMinutes(date, timeIntValue);
                break;
            case SECONDS:
                result = DateUtils.addSeconds(date, timeIntValue);
                break;
            case MILLISECONDS:
                result =
                        DateUtils.addMilliseconds(date, timeIntValue);
                break;
            default:
                result = date;
        }
        return result;
    }

    /**
     * Parses a string date,
     * using the default date format symbols for the default locale.
     *
     * @param str date string
     * @return the parsed date obj
     */
    public static Date parseDateStrToDate(String str) {
        return parseDate(str, Locale.getDefault());
    }

    /**
     * parse string date
     * @param str date str
     * @param locale If null, the system locale is used
     * @return date obj
     */
    public static Date parseDate(String str, Locale locale) {
        String[] patterns = new String[] {"yyyy-MM-dd HH:mm:ss",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy.MM.dd HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy/MM/dd",
                "yyyy.MM.dd",
                "HH:mm:ss",
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd HH:mm:ss.SSS",
                "yyyyMMddHHmmss",
                "yyyyMMddHHmmssSSS",
                "yyyyMMdd",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        };
        try {
            return DateUtils.parseDate(str, locale, patterns);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Gets the year in the date.
     *
     * @param date date obj
     * @return year in the date
     */
    public static int getYearFromDate(Date date) {
        return getField(date, Calendar.YEAR);
    }

    /**
     * month counting from 0!
     *
     * @return month for the given date. count from 0
     */
    public static int getMonthFromDate(Date date) {
        return getField(date, Calendar.MONTH);
    }

    /**
     * Get day of that month
     *
     * @param date date obj
     * @return day of the month in given date
     */
    public static int getDayOfMonthFromDate(Date date) {
        return getField(date, Calendar.DAY_OF_MONTH);
    }

    public static int getField(Date date, int field) {
        return dateToCalendar(date).get(field);
    }

    /**
     * Convert date obj to calendar obj.
     * default locale is based on the host vm.
     *
     * @param date date obj
     * @return calendar obj
     */
    public static Calendar dateToCalendar(Date date) {
        return dateToCalendar(date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Convert date obj to calendar obj.
     * with designated locale
     */
    public static Calendar dateToCalendar(Date date, Locale locale) {
        return dateToCalendar(date, TimeZone.getDefault(), locale);
    }

    /**
     * Convert date obj to calendar obj.
     * with locale, time zone
     * @param date
     * @param zone
     * @param locale
     * @return
     */
    public static Calendar dateToCalendar(Date date, TimeZone zone, Locale locale) {
        if (null == locale) {
            locale = Locale.getDefault(Locale.Category.FORMAT);
        }
        final Calendar cal =
                (null != zone) ? Calendar.getInstance(zone, locale) : Calendar.getInstance(locale);
        cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        cal.setTime(date);
        return cal;
    }
}