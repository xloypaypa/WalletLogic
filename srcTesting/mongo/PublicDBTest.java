package mongo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import type.Type;
import type.UserMessage;
import database.MongoManager;
import database.publicDB.PublicDB;

public class PublicDBTest {
	
	@Before
	public void setUp(){
		MongoManager.loadDB("testc");
	}
	
	@After
	public void tearDown() {
		MongoManager.clean("user");
	}

	@Test
	public void testAddAndLoad() {
		PublicDB publicDB = new PublicDB();
		UserMessage user = new UserMessage("name", "pass", "tree");
		publicDB.addItem(user);
		Vector<Type> ans = publicDB.load();
		assertEquals(1, ans.size());
		user = (UserMessage) ans.get(0);
		assertEquals("name", user.getName());
		assertEquals("tree", user.getReason());
	}
	
	@Test
	public void testUpdate() {
		PublicDB publicDB = new PublicDB();
		UserMessage user = new UserMessage("name", "pass", "tree");
		publicDB.addItem(user);
		user = new UserMessage("name", "pass", "normal");
		publicDB.updateItem("name", user);
		Vector<Type> ans = publicDB.load();
		assertEquals(1, ans.size());
		user = (UserMessage) ans.get(0);
		assertEquals("name", user.getName());
		assertEquals("normal", user.getReason());
	}
	
	@Test
	public void testRemove() {
		PublicDB publicDB = new PublicDB();
		UserMessage user = new UserMessage("name", "pass", "tree");
		publicDB.addItem(user);
		publicDB.removeItem("name");
		Vector<Type> ans = publicDB.load();
		assertEquals(0, ans.size());
	}

}
