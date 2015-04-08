package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import type.ReasonTreeNodeType;
import data.TreeReasonKeeper;

public class TreeReasonTest extends TreeOperatorTest {

	@Test
	public void testBaseFeature() {
		TreeReasonKeeper keeper=(TreeReasonKeeper) Operator.data.getData("reason");
		ReasonTreeNodeType reason;
		Operator.addTreeReason("reason one", "root", 0, 0, 0);
		assertTrue(keeper.itemExist("reason one"));
		assertFalse(keeper.itemExist("reason two"));
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals(0, reason.getMax(), 1e-3);
		
		Operator.renameTreeReason("reason one", "reason two", "root", 1, 2, 3);
		assertTrue(keeper.itemExist("reason two"));
		assertFalse(keeper.itemExist("reason one"));
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals(2, reason.getMax(), 1e-3);
		
		Operator.renameTreeReason("reason two", "reason one", "root", 1, 2, 3);
		
		Operator.addTreeReason("reason two", "reason one", 0, 0, 0);
		Operator.renameTreeReason("reason one", "reason three", "root", 0, 0, 0);
		assertTrue(keeper.itemExist("reason three"));
		assertTrue(keeper.itemExist("reason two"));
		
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals("reason three", reason.getFather());
		
		Operator.addTreeReason("reason one", "root", 0, 0, 0);
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals("root", reason.getFather());
		Operator.renameTreeReason("reason one", "reason one", "reason two", 1, 2, 3);
		reason=(ReasonTreeNodeType) keeper.getItem("reason one");
		assertEquals("reason two", reason.getFather());
		
		Operator.removeReason("reason three");
		reason=(ReasonTreeNodeType) keeper.getItem("reason two");
		assertEquals("root", reason.getFather());
	}
	
	@Test
	public void testError(){
		Operator.addTreeReason("reason one", "root", 0, 0, 0);
		Operator.addTreeReason("reason two", "reason one", 0, 0, 0);
		assertEquals("ok.", LogicWithUIMessage.message.getMessage());
		Operator.renameTreeReason("reason one", "reason one", "reason two", 0, 0, 0);
		assertEquals("Reason shouldn't have loop.", LogicWithUIMessage.message.getMessage());
		
		Operator.renameTreeReason("reason one", "reason one", "root", 0, 0, 0);
		assertEquals("ok.", LogicWithUIMessage.message.getMessage());
		
		Operator.renameTreeReason("reason one", "root", "root", 0, 0, 0);
		assertEquals("This name have exist.", LogicWithUIMessage.message.getMessage());
	}

}