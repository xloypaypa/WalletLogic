package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import type.ReasonTreeNodeType;
import data.TreeReasonKeeper;

public class TreeCostTest extends TreeOperatorTest {
	
	@Test
	public void testBaseFeature(){
		TreeReasonKeeper keeper=(TreeReasonKeeper) Operator.data.getData("reason");
		ReasonTreeNodeType reason;
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type two");
		Operator.addTreeReason("reason one", "root", 0, 50, 1);
		Operator.addTreeReason("reason two", "reason one", 0, 100, 1);
		Operator.income("type one", 100, "reason two");
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(100, reason.getIncome(), 1e-3);
		
		Operator.expenditure("type one", 100, "reason two");
		assertNotEquals("ok.", LogicWithUIMessage.message.getMessage());
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(0, reason.getExpenditure(), 1e-3);
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals(0, reason.getExpenditure(), 1e-3);
		
		Operator.expenditure("type one", 50, "reason two");
		assertEquals("ok.", LogicWithUIMessage.message.getMessage());
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(50, reason.getExpenditure(), 1e-3);
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals(50, reason.getExpenditure(), 1e-3);
		
	}
}
