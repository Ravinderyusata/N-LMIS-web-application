package com.chai.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Helper functions for handling dates.
 */
public class CalendarUtil {
	/**
	 * Default date format in the form 2013-03-18.
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateTimeFormatter DATETIME_FORMAT_FOR_DATABASE_INSERT = DateTimeFormatter
			.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter DATETIME_FORMAT_TO_DISPLAY_ON_FORMS = DateTimeFormatter
			.ofPattern("dd-MMM-yyyy");

	public static LocalDate getDateStringInMySqlInsertFormat(String string) {
		if (string != null && !string.isEmpty())
			return LocalDate.parse(string, DATETIME_FORMAT_FOR_DATABASE_INSERT);
		else
			return null;
	}

	public static LocalDate fromString(String string) {
		if (string != null && !string.isEmpty())
			return LocalDate.parse(string, DATETIME_FORMAT_TO_DISPLAY_ON_FORMS);
		else
			return null;
	}

	public static String getCurrentTime() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String currentTime = sdfTime.format(now);
		System.out.println("Current Time: " + currentTime);
		return currentTime;
	}

	public static String getCurrentTimeInHyphenFormat() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH-mm-ss");
		Date now = new Date();
		String currentTime = sdfTime.format(now);
		System.out.println("Current Time: " + currentTime);
		return currentTime;
	}

	/**
	 * Returns the given date as a well formatted string. The above defined date
	 * format is used.
	 * 
	 * @param calendar
	 *            date to be returned as a string
	 * @return formatted string
	 */
	public static String format(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return DATE_FORMAT.format(calendar.getTime());
	}

	/**
	 * Converts a String in the format "yyyy-MM-dd" to a Calendar object.
	 * 
	 * Returns null if the String could not be converted.
	 * 
	 * @param dateString
	 *            the date as String
	 * @return the calendar object or null if it could not be converted
	 */
	public static Calendar parse(String dateString) {
		Calendar result = Calendar.getInstance();
		try {
			result.setTime(DATE_FORMAT.parse(dateString));
			return result;
		} catch (ParseException e) {

			return null;
		}
	}

	/**
	 * Checks the String whether it is a valid date.
	 * 
	 * @param dateString
	 * @return true if the String is a valid date
	 */
	public static boolean validString(String dateString) {
		try {
			DATE_FORMAT.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	// to convert Localdate object to Date Object (in
	// AddOrderLineController.java)
	public static String toDateString(LocalDate date) {
		Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date res = Date.from(instant);
		return new SimpleDateFormat("dd-MMM-yyyy").format(res);
	}

	/**
	 * this method take type of month and retun month type in short and
	 * log-month
	 */
	public static JSONArray getShortMonths(String monthStrSize) {
		JSONArray shortMonthsList = new JSONArray();
		String[] shortMonths;
		if (monthStrSize.equals("short_months")) {
			shortMonths = new DateFormatSymbols().getShortMonths();
			for (int i = 0; i < Calendar.getInstance().get(Calendar.MONTH) + 1; i++) {// sunil
				String shortMonth = shortMonths[i];
				System.out.println("shortMonth = " + shortMonth + " i=" + i);
				JSONObject obj = new JSONObject();
				obj.put(shortMonth, Integer.toString(i + 1));
			}
		} else {
			shortMonths = new DateFormatSymbols().getMonths();
		}
		return shortMonthsList;
	}

	/**
	 * this method return current & just previous year as list.
	 */
	public static JSONArray getYear() {
		JSONArray yearlist = new JSONArray();
		for (int i = LocalDate.now().getYear(); i >= (LocalDate.now().getYear() - 1); i--) {
			JSONObject obj = new JSONObject();
			obj.put("value", Integer.toString(i));
			obj.put("label", Integer.toString(i));
			yearlist.put(obj);
		}

		return yearlist;
	}

	/**
	 * this method return quarter as currunt year and previous year
	 */
	public static JSONArray getQuarter(int year) {
		JSONArray quarterlist = new JSONArray();
		if (year == LocalDate.now().getYear()) {
			float j = LocalDate.now().getMonthValue() / 3f;
			for (int i = 1; i <= (int) Math.ceil(j); i++) {
				JSONObject obj = new JSONObject();
				obj.put("value", String.valueOf(i));
				obj.put("label", String.valueOf(i));
				quarterlist.put(obj);
			}
		} else {
			for (int i = 1; i <= 4; i++) {
				JSONObject obj = new JSONObject();
				obj.put("value", String.valueOf(i));
				obj.put("label", String.valueOf(i));
				quarterlist.put(obj);
			}
		}
		return quarterlist;
	}

	/**
	 * this method return no. of week according to year
	 */
	public JSONArray getWeek(int year) {
		System.out.println("selected year:" + year);
		JSONArray weeks = new JSONArray();
		if (year != LocalDate.now().getYear()) {
			for (int i = 1; i < 53; i++) {
				JSONObject obj = new JSONObject();
				if (i < 10) {
					obj.put("value", "0" + Integer.toString(i));
					obj.put("label", "0" + Integer.toString(i));
				} else {
					obj.put("value", Integer.toString(i));
					obj.put("label", Integer.toString(i));
				}
				weeks.put(obj);
			}
		} else {
			for (int i = (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)); i > 0; i--) {
				JSONObject obj = new JSONObject();
				if (i < 10) {
					obj.put("value", "0" + Integer.toString(i));
					obj.put("label", "0" + Integer.toString(i));
				} else {
					obj.put("value", Integer.toString(i));
					obj.put("label", Integer.toString(i));
				}
				weeks.put(obj);
			}

		}

		return weeks;
	}

	public JSONArray getMonth(int year) {
		String shortMonths[];
		JSONArray month = new JSONArray();
		shortMonths = new DateFormatSymbols().getShortMonths();
		if (year == LocalDate.now().getYear()) {
			for (int i = 0; i < LocalDate.now().getMonthValue(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("value", i + 1);
				obj.put("label", shortMonths[i]);
				month.put(obj);
			}
		} else {
			for (int i = 0; i < shortMonths.length; i++) {
				JSONObject obj = new JSONObject();
				obj.put("value", i + 1);
				obj.put("label", shortMonths[i]);
				month.put(obj);
			}
		}
		return month;
	}
	/**
	 * this method return date format dd/mm/yyyy
	 */
	// public static void setDateFormat(DatePicker datePicker){
	// String pattern = "dd-MM-yyyy";
	// datePicker.setPromptText(pattern.toLowerCase());
	//
	// datePicker.setConverter(new StringConverter<LocalDate>() {
	// DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
	//
	// @Override
	// public String toString(LocalDate date) {
	// if (date != null) {
	// return dateFormatter.format(date);
	// } else {
	// return "";
	// }
	// }
	//
	// @Override
	// public LocalDate fromString(String string) {
	// if (string != null && !string.isEmpty()) {
	// return LocalDate.parse(string, dateFormatter);
	// } else {
	// return null;
	// }
	// }
	// });
	// }
}