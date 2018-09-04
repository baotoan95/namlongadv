package net.namlongadv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {
	private static Calendar calendar = Calendar.getInstance();

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	public static Date convertStringToDate(String date) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}

	public static Date increaseDay(Date date, int numOfDay) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, numOfDay);
		return calendar.getTime();
	}

	public static Date decreaseDay(Date date, int numOfDay) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -numOfDay);
		return calendar.getTime();
	}

	public static String convertDateToString(Date date) {
		String dateString = "";
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dateString = sdfr.format(date);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return dateString;
	}
	
	public static String convertDateToString(Date date, String pattern) {
		if(Objects.nonNull(date)) return StringUtils.EMPTY;
		
		String dateString = "";
		SimpleDateFormat sdfr = new SimpleDateFormat(pattern);
		try {
			dateString = sdfr.format(date);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return dateString;
	}
}
