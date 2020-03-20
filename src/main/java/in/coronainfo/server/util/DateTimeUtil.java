package in.coronainfo.server.util;

import in.coronainfo.server.constants.StringConstants;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String getTodayDateStr() {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(StringConstants.TIMEZONE_KOLKATA));
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime);
    }

    public static String getYesterdayDateStr() {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(StringConstants.TIMEZONE_KOLKATA)).minusDays(1);
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime);
    }

}
