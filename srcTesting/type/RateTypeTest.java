package type;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import tool.TimeGeter;

public class RateTypeTest {
	
	@Test
	public void testFinalRate(){
		DebtType debtType = new DebtType();
		RateType r1=new RateType(debtType,1);
		assertEquals(2, r1.finalRate(new Date(), new Date()),1e-3);
		
		RateType r2=new RateType(debtType,"month", 1);
		assertEquals(2, r2.finalRate(TimeGeter.getTime(2015, 1, 1), TimeGeter.getTime(2015, 2, 18)),1e-3);
		
		RateType r3=new RateType(debtType,"year", 1);
		assertEquals(1, r3.finalRate(TimeGeter.getTime(2015, 1, 1), TimeGeter.getTime(2015, 2, 18)),1e-3);
		
		assertEquals(4, r2.finalRate(TimeGeter.getTime(2015, 12, 1), TimeGeter.getTime(2016, 2, 18)),1e-3);
	}

}
