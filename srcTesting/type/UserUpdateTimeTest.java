package type;


import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import tool.String2Element;
import tool.TimeGeter;

public class UserUpdateTimeTest {

	@Test
	public void testMonth() {
		UserUpdateTime user=new UserUpdateTime();
		user.setMonth(1);
		Date ans=new Date();
		ans=user.getNextTime(TimeGeter.getTime(2000, 1, 12));
		TimeGeter.checkDate(ans, 2000, 2, 1);
		
		ans=user.getNextTime(TimeGeter.getTime(2000, 1, 1));
		TimeGeter.checkDate(ans, 2000, 2, 1);
		
		user.setMonth(15);
		ans=user.getNextTime(TimeGeter.getTime(2000, 1, 12));
		TimeGeter.checkDate(ans, 2000, 1, 15);
		
		user.setMonth(15);
		ans=user.getNextTime(TimeGeter.getTime(2000, 1, 16));
		TimeGeter.checkDate(ans, 2000, 2, 15);
		
		user.setMonth(15);
		ans=user.getNextTime(TimeGeter.getTime(2000, 12, 16));
		TimeGeter.checkDate(ans, 2001, 1, 15);
	}
	
	@Test
	public void testYear(){
		UserUpdateTime user=new UserUpdateTime();
		user.setYear(4, 1);
		Date ans=new Date();
		ans=user.getNextTime(TimeGeter.getTime(2000, 1, 12));
		TimeGeter.checkDate(ans, 2000, 4, 1);
		
		ans=user.getNextTime(TimeGeter.getTime(2000, 4, 1));
		TimeGeter.checkDate(ans, 2001, 4, 1);
	}
	
	@Test
	public void test(){
		UserUpdateTime user=new UserUpdateTime();
		user.setYear(4, 1);
		user.getNextTime(TimeGeter.getTime(2000, 4, 1));
		UserUpdateTime a=new UserUpdateTime();
		a.solveTypeMessage(String2Element.toElement(user.format()));
		assertEquals(user.format(),a.format());
		
	}

}
