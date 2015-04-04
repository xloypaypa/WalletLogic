package logic;

import org.junit.Before;

public class TreeOperatorTest extends LogicTest {
	@Override @Before
	public void setDB() {
		super.setDB();
		Operator.register("name", "pass", "pass", "tree");
		Operator.login("name", "pass");
	}
}
