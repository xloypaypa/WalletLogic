package type;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.String2Element;

public class UserMessageTest {

	@Test
	public void test() {
		UserMessage user=new UserMessage("name", "pass", "tree");
		UserMessage user2=new UserMessage();
		user2.solveTypeMessage(String2Element.toElement(user.format()));
		assertEquals(user.format(), user2.format());
	}

}
