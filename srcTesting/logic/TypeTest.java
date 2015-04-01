package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import data.MoneyKeeper;

public class TypeTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		MoneyKeeper keeper=(MoneyKeeper) Operator.data.getData("money");
		Operator.addMoneyType("type one");
		assertTrue(keeper.itemExist("type one"));
		Operator.renameMoneyType("type one", "type two");
		assertTrue(keeper.itemExist("type two"));
		assertFalse(keeper.itemExist("type one"));
		Operator.removeMoneyType("type two");
		assertFalse(keeper.itemExist("type two"));
		assertFalse(keeper.itemExist("type one"));
	}
	
	@Test
	public void testError(){
		Operator.addMoneyType("[type]");
		assertEquals("Please don't use '['!", LogicWithUIMessage.message.getMessage());
		Operator.addMoneyType("");
		assertEquals("Please input type name.", LogicWithUIMessage.message.getMessage());
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type one");
		assertEquals("This type have exist.", LogicWithUIMessage.message.getMessage());
	}

}
