package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest extends TestCase {

	@Test
	public void testNoUser() {
		User.loadUser();
		assertEquals(0, User.getAllUserName().size());
	}
	
	@Test
	public void testAddUser() {
		User.loadUser();
		User.addUser("name", "pass", "tree");
		User.loadUser();
		assertEquals(1, User.getAllUserName().size());
		assertTrue(User.userExist("name"));
		assertTrue(User.checkUser("name", "pass"));
		assertFalse(User.checkUser("name", "pas2"));
		assertFalse(User.checkUser("name1", "pass"));
		assertFalse(User.userExist("name1"));
		
		assertEquals("tree", User.getUserReason("name"));
	}
	
	@Test
	public void testLogin(){
		User.loadUser();
		User.addUser("name", "pass", "tree");
		User.login("name", "pass");
		assertEquals("name", User.getNowUser());
	}

}
