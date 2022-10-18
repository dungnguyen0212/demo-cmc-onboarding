package cmc.demo.fc_demo.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateUtils {
	public static final String UTC_TIME_ZONE = "UTC";
	public static final String LOCAL_TIME_ZONE = "Asia/Ho_Chi_Minh";
	public static final String SHORT_PATTERN = "dd-MM-yyyy";
	public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
	public static final String SQL_DATE_PATTERN = "yyyy-MM-dd";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String FULL_TIME_PATTERN = "HH:mm:ss";
	public static final String HOUR_SHORT_PATTERN = "HH:mm";
	public static final int NUMBER_DAYS_OF_MONTH = 30;
	public static final Long DAY_TIME_MILLI = 24 * 3600 * 1000L;

	public static Timestamp convertToTimestamp(String datetimeString, String format) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.setTimeZone(TimeZone.getTimeZone(DateUtils.LOCAL_TIME_ZONE));
			Date date = formatter.parse(datetimeString);
			return new Timestamp(date.getTime());
		} catch (Exception ex) {
			return null;
		}
	}

	public static Timestamp endOfDay(Timestamp timestamp) {

		if (Objects.isNull(timestamp)) {
			return null;
		}

		return endOfDay(timestamp, LOCAL_TIME_ZONE);
	}

	public static Timestamp endOfDay(Timestamp timestamp, String timezone) {
		return Objects.isNull(timestamp) ? null : endOfDay(timestamp.getTime(), timezone);
	}

	public static Timestamp endOfDay(long timestampMilliSec, String timezone) {
		Instant instant = Instant.ofEpochMilli(timestampMilliSec);
		ZoneId zoneId = ZoneId.of(timezone);
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId).withHour(23).withMinute(59)
				.withSecond(59).withNano(999999999);
		return new Timestamp(zonedDateTime.toInstant().toEpochMilli());
	}

	public static Date convertStringToDate(String input, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(input);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String format(Date date, String pattern) {
		return format(date.getTime(), LOCAL_TIME_ZONE, pattern);
	}

	/**
	 * @param timestampMilliSec
	 * @param timezone
	 * @param pattern
	 * @return
	 */
	public static String format(long timestampMilliSec, String timezone, String pattern) {
		Instant instant = Instant.ofEpochMilli(timestampMilliSec);
		ZoneId zoneId = ZoneId.of(timezone);
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return zonedDateTime.format(formatter);
	}
}
