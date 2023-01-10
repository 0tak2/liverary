package liverary.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateHelper {

	public static String todayDateStr() {
		LocalDate date = LocalDate.now();
		return date.toString();
	}
	
	public static LocalDate todayDate() {
		return LocalDate.now();
	}
	
	public static LocalDate ConvertStrToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr);
	}
	
	public static String ConvertLocalDateToStr(LocalDate date) {
		return date.format(DateTimeFormatter.BASIC_ISO_DATE);
	}	
	
	public static LocalDate AddDaysToTodayDate(long days) {
		LocalDate date = LocalDate.now();
		LocalDate dueDate = date.plusDays(days);
		return dueDate;
	}
	
	public static int getDifferenceByToday(LocalDate targetDate) {
		LocalDate todayDate = LocalDate.now();
		Period diff = Period.between(targetDate, todayDate);
		return diff.getDays();
	}
	
	public static int getDifferenceBetween(LocalDate returnedDate, LocalDate dueDate) {
		Period diff = Period.between(dueDate, returnedDate);
		return diff.getDays();
	}
}
