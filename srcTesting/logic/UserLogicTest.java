package logic;

import static org.junit.Assert.*;
import logicListener.LogicMessageListener;

import org.junit.Before;
import org.junit.Test;

public class UserLogicTest extends LogicTest {
	
	@Before @Override
	public void setDB() {
		super.setDB();
		UserLogic.setListenser(new LogicMessageListener() {
			@Override
			public void logicUIAction() {
			}
		});
	}

	@Test
	public void testBaseFeature() {
		UserLogic.login("name", "pass");
		assertEquals("user name or pass word wrong.", UserLogic.message.getMessage());
		UserLogic.register("name", "pass", "pass", "tree");
		assertEquals("ok.", UserLogic.message.getMessage());
		UserLogic.login("name", "pass");
		assertEquals("ok.", UserLogic.message.getMessage());
	}
	
	@Test
	public void testRegister(){
		UserLogic.register("exist name", "pass", "pass", "tree");
		
		UserLogic.register("name", "pass", "pass2", "tree");
		assertEquals("Two password not same.", UserLogic.message.getMessage());
		
		UserLogic.register("exist name", "pass", "pass", "tree");
		assertEquals("This user have exist.", UserLogic.message.getMessage());
		
		UserLogic.register("name", "", "", "tree");
		assertEquals("Please input password.", UserLogic.message.getMessage());
		
		UserLogic.register("", "pass", "pass", "tree");
		assertEquals("Please input user name.", UserLogic.message.getMessage());
		
		UserLogic.register("temp", "pass", "pass", "tree");
		assertEquals("This user is a system used.", UserLogic.message.getMessage());
		
		UserLogic.register("[name]", "pass", "pass", "tree");
		assertEquals("Please don't use '['!", UserLogic.message.getMessage());
		
		UserLogic.register("name", "pa[ss", "pa[ss", "tree");
		assertEquals("Please don't use '['!", UserLogic.message.getMessage());
	}

}
