package mongo;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import type.MoneyType;
import type.Type;
import database.MongoManager;
import database.usernameDB.NormalDB;

public class NormalDBTest {

	@Before
	public void setUp() throws Exception {
		MongoManager.loadDB("testc");
	}

	@After
	public void tearDown() throws Exception {
		MongoManager.clean("money");
	}

	@Test
	public void testAddAndLoad() {
		NormalDB db = new NormalDB("name", "money") {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		MoneyType money = new MoneyType();
		money.setType("type one");
		money.setValue(123);
		db.addItem(money);
		Vector<Type> ans = db.load();
		assertEquals(1, ans.size());
		money = (MoneyType) ans.get(0);
		assertEquals("type one", money.getType());
		assertEquals(123, money.getValue(), 1e-8);
	}
	
	@Test
	public void testUpdate() {
		NormalDB db = new NormalDB("name", "money") {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		MoneyType money = new MoneyType("type one", 123);
		db.addItem(money);
		
		money = new MoneyType("type two", 124);
		db.updateItem("type one", money);
		
		Vector<Type> ans = db.load();
		assertEquals(1, ans.size());
		money = (MoneyType) ans.get(0);
		assertEquals("type two", money.getType());
		assertEquals(124, money.getValue(), 1e-8);
	}
	
	@Test
	public void testRemove() {
		NormalDB db = new NormalDB("name", "money") {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		MoneyType money = new MoneyType("type one", 123);
		db.addItem(money);
		db.removeItem("type one");
		Vector<Type> ans = db.load();
		assertEquals(0, ans.size());
	}

}
