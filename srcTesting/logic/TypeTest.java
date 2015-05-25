package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.MoneyOperator;

public class TypeTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		MoneyOperator keeper=(MoneyOperator) Operator.data.getData("money");
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
		assertEquals("Please don't use '['!", ListenerManager.message.getMessage());
		Operator.addMoneyType("");
		assertEquals("Please input type name.", ListenerManager.message.getMessage());
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type one");
		assertEquals("This name have exist.", ListenerManager.message.getMessage());
	}

}
