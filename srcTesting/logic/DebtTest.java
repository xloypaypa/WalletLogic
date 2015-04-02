package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.TimeGeter;
import type.MoneyType;
import data.DebtKeeper;
import data.MoneyKeeper;

public class DebtTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		MoneyKeeper mk=(MoneyKeeper) Operator.data.getData("money");
		MoneyType m;
		DebtKeeper dk=(DebtKeeper) Operator.data.getData("debt");
		
		Operator.addMoneyType("type one");
		Operator.addReason("reason one");
		Operator.addBorrowing("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		
		m=(MoneyType) mk.getItem("type one");
		assertEquals(1, m.getValue(),1e-3);
		assertTrue(dk.itemExist("0"));
		
		Operator.repayBorrowing(0, 1, "type one");
		
		m=(MoneyType) mk.getItem("type one");
		assertEquals(0, m.getValue(),1e-3);
		assertFalse(dk.itemExist("0"));
		
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		m=(MoneyType) mk.getItem("type one");
		assertEquals(0, m.getValue(),1e-3);
		assertFalse(dk.itemExist("0"));
		
		Operator.income("type one", 2, "reason one");
		m=(MoneyType) mk.getItem("type one");
		assertEquals(2, m.getValue(),1e-3);
		
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		m=(MoneyType) mk.getItem("type one");
		assertEquals(1, m.getValue(),1e-3);
		assertTrue(dk.itemExist("0"));
		
		Operator.repayLoan(0, 1, "type one");
		m=(MoneyType) mk.getItem("type one");
		assertEquals(2, m.getValue(),1e-3);
		assertFalse(dk.itemExist("0"));
	}

}
