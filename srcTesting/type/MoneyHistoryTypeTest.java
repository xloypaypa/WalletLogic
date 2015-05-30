package type;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import tool.TimeGeter;

public class MoneyHistoryTypeTest {

	@Test
	public void testBasic() {
		MoneyHistoryType m=new MoneyHistoryType("abc", 123);
		assertEquals("abc", m.getName());
	}
	
	@Test
	public void test(){
		MoneyHistoryType m=new MoneyHistoryType("abc",100);
		DetailType detail=new DetailType();
		detail.setEvent("expenditure");
		detail.setValue(50);
		Calendar c=Calendar.getInstance();
		c.set(2000, 1, 1);
		detail.setTime(c.getTime());
		m.addHistory(detail);
		
		Calendar c2=Calendar.getInstance();
		c2.set(2001, 1, 1);
		detail.setTime(c2.getTime());
		detail.setEvent("income");
		detail.setValue(10);
		m.addHistory(detail);
		assertEquals(60, m.getValueBeforTime(new Date()),1e-3);
		assertEquals(c.getTime(), m.getFisrtUse());
		assertEquals(c2.getTime(), m.getLastUse());
	}
	
	@Test
	public void testFeature(){
		MoneyHistoryType m=new MoneyHistoryType("abc",100);
		DetailType detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("add money type");
		detail.setValue(1);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("income");
		detail.setValue(1);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("expenditure");
		detail.setValue(2);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("transfer in");
		detail.setValue(3);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("transfer out");
		detail.setValue(1);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("add borrowing");
		detail.setValue(3);
		m.addHistory(detail);
		
		detail=new DetailType();
		detail.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.setEvent("repay borrowing");
		detail.setValue(1);
		m.addHistory(detail);
		
		assertEquals(4, m.getValueBeforTime(new Date()),1e-3);
		
		detail.setEvent("rename type");
		detail.setType("new name");
		m.addHistory(detail);
		assertEquals("new name", m.getName());
		
		detail.setEvent("remove type");
		m.addHistory(detail);
		assertEquals(0, m.getValueBeforTime(new Date()),1e-3);
	}

}
