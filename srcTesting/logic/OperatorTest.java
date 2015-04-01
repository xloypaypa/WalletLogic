package logic;

import org.junit.Before;

public class OperatorTest extends LogicTest {
	@Override @Before
	public void setDB() {
		super.setDB();
		Operator.register("name", "pass", "pass", "normal");
		Operator.login("name", "pass");
	}
}
