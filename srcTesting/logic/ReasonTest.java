package logic;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
