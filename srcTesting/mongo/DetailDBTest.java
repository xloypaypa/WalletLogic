package mongo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import type.DetailType;
import type.Type;
import database.MongoManager;
import database.usernameDB.DetailDB;

public class DetailDBTest {

	@Before
	public void setUp() throws Exception {
		MongoManager.loadDB("testc");
	}

	@After
	public void tearDown() throws Exception {
		MongoManager.clean("detail");
	}

	@Test
	public void testAddAndLoad() {
		DetailDB db = new DetailDB("name");
		DetailType detail = new DetailType();
		detail.setEvent("event one");
		detail.setReason("reason one");
		detail.setType("type one");
		db.addItem(detail);
		Vector<Type> ans = db.load();
		assertEquals(1, ans.size());
		detail = (DetailType) ans.get(0);
		assertEquals("event one", detail.getEvent());
		assertEquals("reason one", detail.getReason());
		assertEquals("type one", detail.getType());
	}
	
	@Test
	public void testRemove() {
		DetailDB db = new DetailDB("name");
		DetailType detail = new DetailType();
		detail.setEvent("event one");
		detail.setReason("reason one");
		detail.setType("type one");
		db.addItem(detail);
		detail = new DetailType();
		detail.setEvent("event two");
		detail.setReason("reason two");
		detail.setType("type two");
		db.addItem(detail);
		db.remvoeLastDetail();
		Vector<Type> ans = db.load();
		assertEquals(1, ans.size());
		detail = (DetailType) ans.get(0);
		assertEquals("event one", detail.getEvent());
		assertEquals("reason one", detail.getReason());
		assertEquals("type one", detail.getType());
	}
	
	@Test
	public void testError() {
		DetailDB db = new DetailDB("name");
		DetailType detail = new DetailType();
		detail.setEvent("event one");
		detail.setReason("reason one");
		detail.setType("type one");
		db.addItem(detail);
		detail = new DetailType();
		detail.setEvent("event two");
		detail.setReason("reason two");
		detail.setType("type two");
		db.addItem(detail);
		db.remvoeLastDetail();
		db.remvoeLastDetail();
		db.remvoeLastDetail();
		db.remvoeLastDetail();
		Vector<Type> ans = db.load();
		assertEquals(0, ans.size());
	}

}
