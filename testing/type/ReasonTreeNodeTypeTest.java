package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Vector;

public class ReasonTreeNodeTypeTest {

	@Test
	public void test() {
		ReasonTreeNodeType r=new ReasonTreeNodeType();
		r.setFatherName("name");
		ReasonType r2=new ReasonTreeNodeType();
		r2.solveTypeMessage(String2Vector.toVector(r.format()));
		assertEquals(r.getAllMessage(), r2.getAllMessage());
	}

}
