package type;

import static org.junit.Assert.*;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

public class MoneyTypeTest {

	@Test
	public void test1() {
		Element message = DocumentHelper.createElement("type");
		MoneyType mt = new MoneyType();
		
		message.addElement("id").setText("test type");
		message.addElement("type").setText("test type");
		message.addElement("value").setText("10086.5");
		
		mt.solveTypeMessage(message);
		
		assertEquals(mt.getType(), "test type");
		assertEquals(mt.getValue(), 10086.5, 0.001);
	}

}
