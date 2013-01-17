package eu.atosresearch.ocelot.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTools {

	public static Date parseSCMDate(String d) throws ParseException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HHmmssZ");
			d=d.replaceAll(":", "");
			return format.parse(d);
		}catch(Exception e){
			return null;
		}

	}

	public static Date parseForumDate(String d) throws ParseException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(d);
		}catch(Exception e){
			return null;
		}

	}

	public static Date parseMailDate(String d) throws ParseException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);
			return format.parse(d);
		}catch(Exception e){
			return null;
		}

	}

	public static Date parseBTSDate(String d) throws ParseException{
		try{	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			return format.parse(d);
		}catch(Exception e){
			return null;
		}
	}	

}
