package com.iiht.eauction.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

//	2022-09-21T18:30:00.000+00:00
//	yyyy-MM-dd'T'HH:mm:ss.SSSXXX

	public static String convertingTimestampToDate(String d) throws ParseException {
		SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date parse = parser.parse(d);
		String format = simpleformat.format(parse);

		return format;

	}

	public static boolean isBidEndDateIsExpired(String bidEndDate) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		String today = getToday("yyyy-MM-dd");
		Date parsedBidEndDate = parser.parse(bidEndDate);
		Date parsedTodayDate = parser.parse(today);
		if (parsedTodayDate.compareTo(parsedBidEndDate) < 0) {
			return false;
		} else if (parsedTodayDate.compareTo(parsedBidEndDate) == 0) {
			return false;
		} else {
			return true;
		}

	}

	public static String getToday(String format) {
		Date d = new Date();
		return new SimpleDateFormat(format).format(d);
	}

}
