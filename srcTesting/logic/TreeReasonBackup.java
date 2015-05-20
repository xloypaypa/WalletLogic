package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.ReasonKeeper;
import type.ReasonTreeNodeType;

public class TreeReasonBackup extends TreeOperatorTest {
	
	@Test
	public void testReason(){
		ReasonKeeper keeper=(ReasonKeeper) Operator.data.getData("reason");
		ReasonTreeNodeType reason;
		Operator.addTreeReason("reason one", "root", 0, 100, 0);
		Operator.addTreeReason("reason two", "reason one", 0, 100, 0);
		Operator.addMoneyType("type one");
		Operator.income("type one", 10, "reason two");
		Operator.renameTreeReason("reason one", "reason three", "root", 0, 100, 0);
		Operator.removeReason("reason three");
		
		assertEquals(1, keeper.getAllItem().size());
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason three"));
		reason=(ReasonTreeNodeType) keeper.getItem("reason three");
		assertEquals(10, reason.getIncome(), 1e-3);
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals("reason three", reason.getFather());
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason one"));
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(10, reason.getIncome(), 1e-3);
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals("reason one", reason.getFather());
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason one"));
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(0, reason.getIncome(), 1e-3);
		
		Operator.backup();
		Operator.backup();
		assertFalse(keeper.itemExist("reason two"));
		Operator.backup();
		assertFalse(keeper.itemExist("reason one"));
	}

}
