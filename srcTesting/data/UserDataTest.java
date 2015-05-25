package data;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.MoneyOperator;
import database.operator.UserData;
import type.MoneyType;

public class UserDataTest extends TypeTest {

	@Test
	public void test() {
		
		MoneyOperator mk=new MoneyOperator();
		MoneyType mt=new MoneyType();
		mt.setType("type one");
		mt.setValue(123);
		mk.add(mt);
		
		mk.loadData();
		
		assertTrue(mk.itemExist("type one"));
		
		UserData data=new UserData();
		data.addDataKeeper(new MoneyOperator());
		data.loadData("money");
		mk=(MoneyOperator) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.reloadAllData();
		mk=(MoneyOperator) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.loadAllData();
		mk=(MoneyOperator) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
		
		data.reloadData("money");
		mk=(MoneyOperator) data.getData("money");
		assertNotNull(mk);
		assertTrue(mk.itemExist("type one"));
	}

}
