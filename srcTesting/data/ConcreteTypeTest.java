package data;

import static org.junit.Assert.*;

import org.junit.Test;

import type.MoneyType;

public class ConcreteTypeTest extends TypeTest {
	
	@Test
	public void moneyKeeper(){
		assertTrue(new MoneyKeeper().isThisKeeper("money"));
	}
	
	@Test
	public void moneyBaseFeature(){
		MoneyKeeper mk=new MoneyKeeper();
		MoneyType mt=new MoneyType();
		mt.setType("type one");
		mt.setValue(12);
		mk.add(mt);
		
		assertTrue(mk.itemExist("type one"));
		assertEquals(12, ((MoneyType) mk.getItem("type one")).getValue(),1e-3);
		
		mk.loadData();
		
		assertTrue(mk.itemExist("type one"));
		assertEquals(12, ((MoneyType) mk.getItem("type one")).getValue(),1e-3);
	}
	
	@Test
	public void reasonKeeper(){
		assertTrue(new ReasonKeeper().isThisKeeper("reason"));
	}
	
	@Test
	public void debtKeeper(){
		assertTrue(new DebtKeeper().isThisKeeper("debt"));
	}
	
	@Test
	public void detailKeeper(){
		assertTrue(new DetailKeeper().isThisKeeper("detail"));
	}
}
