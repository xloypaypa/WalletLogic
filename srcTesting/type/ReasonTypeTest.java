package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Element;

public class ReasonTypeTest {

	@Test
	public void test() {
		ReasonType r1=new ReasonType("reason", 1, 2);
		ReasonType r2=new ReasonType();
		r2.solveTypeMessage(String2Element.toElement(r1.format()));
		assertEquals(r1.format(), r2.format());
	}

}
