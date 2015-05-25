package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Element;

public class ReasonTreeNodeTypeTest {

	@Test
	public void test() {
		ReasonTreeNodeType r=new ReasonTreeNodeType();
		r.setFatherName("name");
		ReasonType r2=new ReasonTreeNodeType();
		r2.solveTypeMessage(String2Element.toElement(r.format()));
		assertEquals(r2.getExpenditure(), r.getExpenditure(), 1e-3);
	}

}
