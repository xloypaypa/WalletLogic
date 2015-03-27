package tool;

import java.util.Calendar;
import java.util.Date;

public class TimeGeter {
	public static Date getTime(int year,int month,int day){
		Calendar ans=Calendar.getInstance();
		ans.set(year, month-1, day);
		return ans.getTime();
	}
}
