package com.rentalagency.me;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility Class
 * @author abdusamed
 *
 */
public class Utilities {

	// HTML5 Date Parser to Java Date
	public static Date getDate(String date)   {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return d;
		
		
		
	}
}
