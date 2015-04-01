package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Vector;

public class DetailTypeTest {

	@Test
	public void test() {
		DetailType d1=new DetailType();
		DetailType d2=new DetailType(d1);
		assertEquals(d1.getAllMessage(), d2.getAllMessage());
		d2.solveTypeMessage(String2Vector.toVector(d1.format()));
		assertEquals(d1.getAllMessage(), d2.getAllMessage());
	}

}
