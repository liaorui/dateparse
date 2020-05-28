package io.github.araddon;

import com.sun.jna.Library;
import com.sun.jna.Native;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Parse many date strings without knowing format in advance.
 *
 * @author liaorui
 * @date 2020/5/27
 * @see <a href="https://github.com/araddon/dateparse"></a>
 */
@Slf4j
public class DateParse {
    private static LibDate INSTANCE = (LibDate) Native.load("date", LibDate.class);

    /**
     * Normal parse.  Equivalent Timezone rules as time.Parse()
     *
     * @param date
     * @return
     */
    public static ZonedDateTime parseAny(String date) {
        String dateStr = INSTANCE.ParseAny(date);
        if (dateStr != null) {
            try {
                return toDateTime(DateTime.parseRfc3339(dateStr));
            } catch (NumberFormatException e) {
                log.error("Cannot parse {}", dateStr);
            }
        }
        return null;
    }

    /**
     * Parse with Local Timezone
     *
     * @param date
     * @return
     */
    public static ZonedDateTime parseLocal(String date) {
        String dateStr = INSTANCE.ParseLocal(date);
        if (dateStr != null) {
            try {
                return toDateTime(DateTime.parseRfc3339(dateStr));
            } catch (DateTimeParseException e) {
                log.error("Cannot parse {}", dateStr);
            }
        }
        return null;
    }

    /**
     * Parse Strict, error on ambigous mm/dd vs dd/mm dates
     *
     * @param date
     * @return
     */
    public static ZonedDateTime parseStrict(String date) {
        String dateStr = INSTANCE.ParseStrict(date);
        if (dateStr != null) {
            try {
                return toDateTime(DateTime.parseRfc3339(dateStr));
            } catch (DateTimeParseException e) {
                log.error("Cannot parse {}", dateStr);
            }
        }
        return null;
    }

    /**
     * convert ZonedDateTime to Date, trim the timezone
     *
     * @param dateTime
     * @return
     */
    public static Date toDate(ZonedDateTime dateTime) {
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(pattern)
                .withZone(ZoneId.of("UTC"));
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(formatter.format(dateTime));
        } catch (ParseException e) {
            log.error("", e);
        }
        return null;
    }

    private static ZonedDateTime toDateTime(DateTime dateTime) {
        String[] zoneIds = TimeZone.getAvailableIDs(dateTime.getTimeZoneShift() * 60000);
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), ZoneId.of(zoneIds[0]));
    }

    /**
     * format dateTime to `yyyy-MM-dd HH:mm:ss` with local timezone
     *
     * @param dateTime
     * @return
     */
    public static String formatLocal(ZonedDateTime dateTime) {
        return format(dateTime, ZoneId.systemDefault());
    }

    /**
     * format dateTime to `yyyy-MM-dd HH:mm:ss` with UTC timezone
     *
     * @param dateTime
     * @return
     */
    public static String format(ZonedDateTime dateTime) {
        return format(dateTime, ZoneId.of("UTC"));
    }

    /**
     * format dateTime to `yyyy-MM-dd HH:mm:ss` with zoneId
     *
     * @param dateTime
     * @param zoneId
     * @return
     */
    public static String format(ZonedDateTime dateTime, ZoneId zoneId) {
        return format(dateTime, "yyyy-MM-dd HH:mm:ss", zoneId);
    }

    /**
     * formate dateTime with pattern and zoneId
     *
     * @param dateTime
     * @param pattern
     * @param zoneId
     * @return
     */
    public static String format(ZonedDateTime dateTime, String pattern, ZoneId zoneId) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(zoneId);
        return formatter.format(dateTime);
    }


    interface LibDate extends Library {
        String ParseAny(String date);

        String ParseLocal(String date);

        String ParseStrict(String date);
    }
}
