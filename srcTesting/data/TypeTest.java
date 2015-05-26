package data;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import type.MoneyType;
import type.UserMessage;
import database.operator.MoneyOperator;
import database.operator.UserPublicData;
import encryptionAlgorithm.MD5;

public class TypeTest extends DataTest {

	@Override @Before
	public void setDB(){
		super.setDB();
		UserPublicData.addUser(new UserMessage("name", MD5.encode("pass"), "normal"));
		UserPublicData.login("name", "pass");
	}
	
	@Test
	public void testDelete(){
		MoneyOperator mk=new MoneyOperator();
		MoneyType mt=new MoneyType();
		mt.setType("type one");
		mt.setValue(123);
		mk.add(mt);
		
		mk.loadData();
		
		assertTrue(mk.itemExist("type one"));
		
		mk.delete("type one");
		assertFalse(mk.itemExist("type one"));
	}
	
	@Test
	public void testUpdate(){
		MoneyOperator mk=new MoneyOperator();
		MoneyType mt=new MoneyType();
		mt.setType("type one");
		mt.setValue(123);
		mk.add(mt);
		
		mk.loadData();
		
		assertTrue(mk.itemExist("type one"));
		
		mt.setValue(10086);
		mk.update("type one", mt);
		assertEquals(10086, ((MoneyType) mk.getItem("type one")).getValue(),1e-3);
	}

}
