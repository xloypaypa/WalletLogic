package type;

import static org.junit.Assert.*;

import java.util.Date;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

public class DebtTypeTest {

	@Test
	public void testFormat(){
		DebtType debt=new DebtType("name", 10086, new Date(), "null", 2),ans=new DebtType();
		try {
			ans.solveTypeMessage(DocumentHelper.parseText(debt.format()).getRootElement());
		} catch (DocumentException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(debt.format(),ans.format());
	}
	
	@Test
	public void testConstruct(){
		DebtType d1,d2;
		d1=new DebtType("name", 1);
		d2=new DebtType("name", 1, d1.getDeadline());
		assertEquals(d1.format(), d2.format());
	}
	
	@Test
	public void testExceed(){
		DebtType debt=new DebtType("name", 3, new Date(), "null", 2);
		assertEquals(9, debt.getMaxRepay(),1e-3);
		assertEquals(9, debt.getMaxRepay(),1e-3);
	}

}
