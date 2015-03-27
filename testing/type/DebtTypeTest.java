package type;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import tool.String2Vector;
import database.DataBase;

public class DebtTypeTest {

	@Test
	public void testFormat(){
		DebtType debt=new DebtType("name", 10086, new Date(), "null", 2),ans=new DebtType();
		ans.solveTypeMessage(String2Vector.toVector(debt.format()));
		assertEquals(debt.format(),ans.format());
	}
	
	@Test
	public void testTitle(){
		assertEquals("debt type", DataBase.getTypeMessage(String2Vector.toVector(new DebtType().getTypeMessage())));
	}
	
	@Test
	public void testConstruct(){
		DebtType d1,d2;
		d1=new DebtType("name", 1);
		d2=new DebtType("name", 1, d1.getDeadline());
		assertEquals(d1.getAllMessage(), d2.getAllMessage());
	}

}
