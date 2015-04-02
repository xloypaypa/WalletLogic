package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import type.MoneyType;
import data.MoneyKeeper;

public class CostTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		MoneyKeeper keeper=(MoneyKeeper) Operator.data.getData("money");
		MoneyType money;
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type two");
		Operator.addReason("reason one");
		
		Operator.income("type one", 100,"reason one");
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(100, money.getValue(),1e-3);
		
		Operator.transfer("type one", "type two", 30);
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(70, money.getValue(),1e-3);
		money=(MoneyType) keeper.getItem("type two");
		assertEquals(30, money.getValue(),1e-3);
		
		Operator.expenditure("type two", 10,"reason one");
		money=(MoneyType) keeper.getItem("type two");
		assertEquals(20, money.getValue(),1e-3);
	}
	
	@Test
	public void testError(){
		Operator.addMoneyType("type one");
		Operator.addMoneyType("type two");
		Operator.addReason("reason one");
		Operator.income("type one", -1,"reason one");
		assertEquals("The value should be more than zero.", LogicWithUIMessage.message.getMessage());
		Operator.income("type one", 10,"reason one");
		Operator.expenditure("type one", 20,"reason one");
		assertEquals("Don't have enough money.", LogicWithUIMessage.message.getMessage());
	}

}
