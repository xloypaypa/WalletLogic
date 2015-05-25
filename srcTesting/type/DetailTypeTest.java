package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Element;

public class DetailTypeTest {

	@Test
	public void test() {
		DetailType d1=new DetailType();
		DetailType d2=new DetailType(d1);
		assertEquals(d1.format(), d2.format());
		d2.solveTypeMessage(String2Element.toElement(d1.format()));
		assertEquals(d1.getEvent(), d2.getEvent());
	}

}
