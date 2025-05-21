package com.mli.mpro.document.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import com.mli.mpro.productRestriction.util.AppConstants;

/**
 * The type Date time utils.
 */
public class DateTimeUtils {

    private DateTimeUtils() {
        throw new IllegalStateException("No instances allowed!");
    }

    /**
     * Format string.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String format(Date date, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Format string.
     *
     * @param date           the date
     * @param originalFormat the original format
     * @param requiredFormat the required format
     * @return the string
     */
    public static String format(String date, String originalFormat, String requiredFormat) {
        return format(parse(date, originalFormat), requiredFormat);
    }

    /**
     * Parse date.
     *
     * @param date   the date
     * @param format the format
     * @return the date
     */
    public static Date parse(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public static LocalDate convertDateToLocalDate(Date date) {

        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }
    
    public static Date subtractFromDate(Date date, int amount, AppConstants.TimeUnit timeUnit) {
        long millisToAdd = 0;
        
        switch (timeUnit) {
            case SECONDS:
                millisToAdd = amount * 1000;
                break;
            case MINUTES:
                millisToAdd = amount * 1000 * 60;
                break;
            case HOURS:
                millisToAdd = amount * 1000 * 60 * 60;
                break;
            default:
                throw new IllegalArgumentException("Unsupported time unit");
        }
        
        return new Date(date.getTime() - millisToAdd);
    }
    
	public static Date getStartOfDayDate(Date currentDate) {

		// Convert Date to LocalDateTime
		LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

		// Get the LocalDate part
		LocalDate localDate = localDateTime.toLocalDate();

		// Get the start of the day (00:00:00)
		LocalDateTime startOfDay = localDate.atStartOfDay();

		// Convert LocalDateTime back to Date
		Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

		System.out.println("Start of the day: " + startOfDayDate);
		
		return startOfDayDate;
	}
}
