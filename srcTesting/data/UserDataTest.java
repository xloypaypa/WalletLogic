package data;

import static org.junit.Assert.*;

import org.junit.Test;

import type.MoneyType;

public class UserDataTest extends TypeTest {

	@Test
	public void test() {
		
		MoneyKeeper mk=new MoneyKeeper();
		MoneyType mt=new MoneyType();
		mt.setType("type one");
		mt.setValue(123);
		mk.add(mt);
		
		mk.loadData();
		
		assertTrue(mk.itemExist("type one"));
		
		UserData data=new UserData();
		data.addDataKeeper(new MoneyKeeper());
		data.loadData("money");
		mk=(MoneyKeeper) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.reloadAllData();
		mk=(MoneyKeeper) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.loadAllData();
		mk=(MoneyKeeper) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.reloadData("money");
		mk=(MoneyKeeper) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
	}

}
