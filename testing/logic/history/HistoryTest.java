package logic.history;

import logic.TestCase;
import logic.User;

import org.junit.Before;

public class HistoryTest extends TestCase {

	@Override
	@Before
	public void setDB(){
		super.setDB();
		User.addUser("name", "pass", "normal");
		User.login("name", "pass");
	}

}
