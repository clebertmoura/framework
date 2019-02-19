package br.com.framework.service.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
	
	public static final String[] DATE_FORMATS = new String[] {
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ", 
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", 
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX", 
			"yyyy-MM-dd'T'HH:mm:ss.SSS", 
			"yyyy-MM-dd'T'HH:mm:ss", 
			"yyyy-MM-dd'T'HH:mm", 
			"yyyy-MM-dd'T'HH", 
			"yyyy-MM-dd HH:mm:ss.SSS'Z'", 
			"yyyy-MM-dd HH:mm:ss.SSSZ", 
			"yyyy-MM-dd HH:mm:ss.SSSXXX", 
			"yyyy-MM-dd HH:mm:ss.SSS", 
			"yyyy-MM-dd HH:mm:ss", 
			"yyyy-MM-dd HH:mm", 
			"yyyy-MM-dd HH" 
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
		Date parse = parse("2015-05-05 10:59");
		System.out.println(parse);
		System.out.println(format(parse));
	}

}
