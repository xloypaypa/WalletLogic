package type;

import static org.junit.Assert.*;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

public class TypeTest {

	@Test
	public void test1() {
		MoneyType mt=new MoneyType();
		mt.setType("type1");
		mt.setValue(100);
		mt.addExtra("extra1 ds", "extra message");
		
		assertTrue(mt.extraExist("extra1 ds"));
		assertEquals("extra message", mt.getExtraMessage("extra1 ds"));
		
		String now=mt.format();
		String ans="<type><extra><extra1_ds>extra message</extra1_ds></extra><id>type1</id><type>type1</type><value>100.00</value></type>\r\n";
		assertEquals(now, ans);
	}
	
	@Test
	public void test2(){
		Element message =DocumentHelper.createElement("type");
		message.addElement("id").setText("type1");
		message.addElement("type").setText("type1");
		message.addElement("value").setText("100");
		message.addElement("extra");
		message.element("extra").addElement("extra1").setText("extra message");
		
		MoneyType mt=new MoneyType();
		mt.solveTypeMessage(message);
		assertEquals(mt.getType(), "type1");
		assertEquals(mt.getValue(), 100, 0.001);
		assertTrue(mt.extraExist("extra1"));
		assertEquals("extra message", mt.getExtraMessage("extra1"));
	}
	
	@Test
	public void test3(){
		IDType tp=new IDType();
		tp.addExtra("abc", "efg");
		assertTrue(tp.extraExist("abc"));
		assertEquals("efg", tp.getExtraMessage("abc"));
		
		assertFalse(tp.extraExist("efg"));
		assertEquals(new String(), tp.getExtraMessage("efg"));
	}

}
