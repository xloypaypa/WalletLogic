package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import type.ReasonType;
import data.ReasonKeeper;

public class ReasonTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		Operator.addReason("reason one");
		ReasonKeeper keeper=(ReasonKeeper) Operator.data.getData("reason");
		assertTrue(keeper.itemExist("reason one"));
		Operator.renameReason("reason one", "reason two");
		assertFalse(keeper.itemExist("reason one"));
		assertTrue(keeper.itemExist("reason two"));
		Operator.removeReason("reason two");
		assertFalse(keeper.itemExist("reason one"));
		assertFalse(keeper.itemExist("reason two"));
	}
	
	@Test
	public void testIO(){
		ReasonKeeper keeper=(ReasonKeeper) Operator.data.getData("reason");
		ReasonType reason;
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type two");
		Operator.addReason("reason one");
		
		Operator.income("type one", 100,"reason one");
		reason=(ReasonType) keeper.getItem("reason one");
		assertEquals(100, reason.getIncome(),1e-3);
		assertEquals(0, reason.getExpenditure(),1e-3);
		
		Operator.transfer("type one", "type two", 30);
		
		Operator.expenditure("type two", 10,"reason one");
		assertEquals(100, reason.getIncome(),1e-3);
		assertEquals(10, reason.getExpenditure(),1e-3);
	}

}
