package liverary.util;

import java.time.LocalDate;
import java.time.Period;

public class DateHelper {

	public static String todayDateStr() {
		LocalDate date = LocalDate.now();
		return date.toString();
	}
	
	public static String AddDaysToTodayDateStr(long days) {
		LocalDate date = LocalDate.now();
		LocalDate dueDate = date.plusDays(days);
		return dueDate.toString();
	}
	
	public static int getDifferenceByToday(LocalDate targetDate) {
		LocalDate todayDate = LocalDate.now();
		Period diff = Period.between(targetDate, todayDate);
		return diff.getDays();
	}
}
