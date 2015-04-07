package type;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import tool.String2Vector;
import database.password.DataBase;

public class MoneyTypeTest {

	@Test
	public void test1() {
		Vector <String> message=new Vector<String>();
		MoneyType mt = new MoneyType();
		
		message.add("[money type]");
		message.add("test type");
		message.add("[money value]");
		message.add("10086.5");
		
		mt.solveTypeMessage(message);
		
		Assert.assertEquals(mt.getType(), "test type");
		Assert.assertEquals(mt.getValue(), 10086.5, 0.001);
	}
	
	@Test
	public void test2(){
		MoneyType mt = new MoneyType();
		mt.setType("test type");
		mt.setValue(10086.5);
		
		Vector <String> message=new Vector<String>();
		message.add("[money type]");
		message.add("test type");
		message.add("[money value]");
		message.add("10086.50");
		String ans=new String();
		for (int i=0;i<message.size();i++) ans+=message.get(i)+"\r\n";
		Assert.assertEquals(mt.format(), ans);
	}
	
	@Test
	public void testTitle(){
		assertEquals("money type", DataBase.getTypeMessage(String2Vector.toVector(new MoneyType().getTypeMessage())));
	}

}
