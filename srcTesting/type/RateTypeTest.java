package type;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import tool.TimeGeter;

public class RateTypeTest {

	@Test
	public void test1() {
		RateType rt=new RateType();
		Vector <String> message=new Vector<String>();
		
		message.add("[debt id]");
		message.add("13");
		message.add("[creditor]");
		message.add("creditor name!");
		message.add("[debt value]");
		message.add("10086.5");
		message.add("[debt starting time]");
		message.add("2015-10-1");
		message.add("[debt deadline]");
		message.add("2020-1-1");
		message.add("[debt last update time]");
		message.add("2017-1-1");
		message.add("[rate type]");
		message.add("year");
		message.add("[rate value]");
		message.add("0.5");
		
		rt.solveTypeMessage(message);
		Assert.assertEquals(rt.getRate(), 0.5, 0.001);
		Assert.assertEquals(rt.getType(), "year");
	}
	
	@Test
	public void testConstruct(){
		RateType r1,r2;
		r1=new RateType(1);
		r2=new RateType("null", 1);
		assertEquals(r1.getAllMessage(),r2.getAllMessage());
	}
	
	@Test
	public void testFinalRate(){
		RateType r1=new RateType(1);
		assertEquals(2, r1.finalRate(new Date(), new Date()),1e-3);
		
		RateType r2=new RateType("month", 1);
		assertEquals(2, r2.finalRate(TimeGeter.getTime(2015, 1, 1), TimeGeter.getTime(2015, 2, 18)),1e-3);
		
		RateType r3=new RateType("year", 1);
		assertEquals(1, r3.finalRate(TimeGeter.getTime(2015, 1, 1), TimeGeter.getTime(2015, 2, 18)),1e-3);
		
		assertEquals(4, r2.finalRate(TimeGeter.getTime(2015, 12, 1), TimeGeter.getTime(2016, 2, 18)),1e-3);
	}

}
