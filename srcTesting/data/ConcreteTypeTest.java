package data;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.DebtOperator;
import database.operator.DetailOperator;
import database.operator.MoneyOperator;
import database.operator.ReasonOperator;
import type.MoneyType;

public class ConcreteTypeTest extends TypeTest {
	
	@Test
	public void moneyKeeper(){
		assertTrue(new MoneyOperator().isThisKeeper("money"));
	}
	
	@Test
	public void moneyBaseFeature(){
		MoneyOperator mk=new MoneyOperator();
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
		assertTrue(new ReasonOperator().isThisKeeper("reason"));
	}
	
	@Test
	public void debtKeeper(){
		assertTrue(new DebtOperator().isThisKeeper("debt"));
	}
	
	@Test
	public void detailKeeper(){
		assertTrue(new DetailOperator().isThisKeeper("detail"));
	}
}
