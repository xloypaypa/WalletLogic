package tool;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

public class TimeGeter {
	public static Date getTime(int year,int month,int day){
		Calendar ans=Calendar.getInstance();
		ans.set(year, month-1, day);
		return ans.getTime();
	}
	
	public static void checkDate(Date now,int year,int month,int day){
		Calendar c=Calendar.getInstance();
		c.setTime(now);
		assertEquals(year,c.get(Calendar.YEAR));
		assertEquals(month-1,c.get(Calendar.MONTH));
		assertEquals(day,c.get(Calendar.DAY_OF_MONTH));
	}
	
	public static void checkDate(Date expect,Date actual){
		Calendar c=Calendar.getInstance();
		Calendar a=Calendar.getInstance();
		c.setTime(expect);
		a.setTime(actual);
		assertEquals(c.get(Calendar.YEAR),a.get(Calendar.YEAR));
		assertEquals(c.get(Calendar.MONTH),a.get(Calendar.MONTH));
		assertEquals(c.get(Calendar.DAY_OF_MONTH),a.get(Calendar.DAY_OF_MONTH));
	}
}
