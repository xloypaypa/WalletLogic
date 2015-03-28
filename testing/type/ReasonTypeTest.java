package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Vector;

public class ReasonTypeTest {

	@Test
	public void test() {
		ReasonType r1=new ReasonType("reason", 1, 2);
		ReasonType r2=new ReasonType();
		r2.solveTypeMessage(String2Vector.toVector(r1.format()));
		assertEquals(r1.getAllMessage(), r2.getAllMessage());
	}

}
