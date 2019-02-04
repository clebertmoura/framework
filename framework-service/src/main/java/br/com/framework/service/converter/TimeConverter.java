package br.com.framework.service.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
	
	public static final String[] DATE_FORMATS = new String[] {
			"HH:mm:ss.SSSZ", 
			"HH:mm:ss.SSSXXX", 
			"HH:mm:ss.SSS", 
			"HH:mm:ss", 
			"HH:mm", 
			"HH"
	};

	public static Date parse(String s) {
		Date date = null;
		for (String format : DATE_FORMATS) {
			DateFormat formatter = new SimpleDateFormat(format);
	        try {
	            date = formatter.parse(s);
	            break;
	        } catch (ParseException e) {
	            continue;
	        }
		}
        return date;
    }

    /**
     * @param date
     * @return
     */
    public static String format(Date date) {
    	DateFormat formatter = new SimpleDateFormat(DATE_FORMATS[0]);
        return formatter.format(date);
    }
    
    public static void main(String[] args) {
		System.out.println(parse("10:59"));
	}

}
