package type;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import tool.String2Vector;
import database.DataBase;

public class TypeTest {

	@Test
	public void test1() {
		MoneyType mt=new MoneyType();
		mt.setType("type1");
		mt.setValue(100);
		mt.addExtra("extra1", "extra message");
		
		String now=mt.format();
		String ans="[money type]\r\ntype1\r\n[money value]\r\n100.00\r\n[extra title]\r\nextra1\r\n[extra message]\r\nextra message\r\n";
		Assert.assertEquals(now, ans);
		Assert.assertEquals(mt.getTypeNumber(), 4);
	}
	
	@Test
	public void test2(){
		Vector <String> message=new Vector<String>();
		message.add("[money type]");
		message.add("type1");
		message.add("[money value]");
		message.add("100.00");
		message.add("[extra title]");
		message.add("extra1");
		message.add("[extra message]");
		message.add("extra message");
		
		MoneyType mt=new MoneyType();
		mt.solveTypeMessage(message);
		Assert.assertEquals(mt.getType(), "type1");
		Assert.assertEquals(mt.getValue(), 100, 0.001);
		Assert.assertEquals(mt.getExtra().get(0).getTitle(), "extra1");
		Assert.assertEquals(mt.getExtra().get(0).getMessage(), "extra message");
	}
	
	@Test
	public void test3(){
		assertEquals("type", DataBase.getTypeMessage(String2Vector.toVector(new Type().getTypeMessage())));
	}
	
	@Test
	public void test4(){
		Type tp=new Type();
		tp.addExtra("abc", "efg");
		assertTrue(tp.extraExist("abc"));
		assertEquals("efg", tp.getExtraMessage("abc"));
		
		assertFalse(tp.extraExist("efg"));
		assertEquals(new String(), tp.getExtraMessage("efg"));
	}

}
