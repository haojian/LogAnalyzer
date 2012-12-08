package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	public static String getRealTime(String bigint_str){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date(Long.valueOf(bigint_str));
		return sdf.format(d);
	}
	
	public static String getCurrentData(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static String getCurrentData(String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
}
