package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.DebtKeeper;
import database.operator.MoneyKeeper;
import database.operator.ReasonKeeper;
import tool.TimeGeter;
import type.DebtType;
import type.MoneyType;
import type.ReasonType;

public class BackupTest extends OperatorTest {

	@Test
	public void testType() {
		MoneyKeeper keeper=(MoneyKeeper) Operator.data.getData("money");
		Operator.addMoneyType("type one");
		assertTrue(keeper.itemExist("type one"));
		Operator.backup();
		assertFalse(keeper.itemExist("type one"));
		
		Operator.data.reloadAllData();
		assertFalse(keeper.itemExist("type one"));
		
		Operator.addMoneyType("type one");
		assertTrue(keeper.itemExist("type one"));
		assertFalse(keeper.itemExist("type two"));
		Operator.renameMoneyType("type one", "type two");
		assertFalse(keeper.itemExist("type one"));
		assertTrue(keeper.itemExist("type two"));
		
		Operator.backup();
		assertFalse(keeper.itemExist("type two"));
		assertTrue(keeper.itemExist("type one"));
		
		Operator.removeMoneyType("type one");
		assertFalse(keeper.itemExist("type one"));
		Operator.backup();
		assertTrue(keeper.itemExist("type one"));
	}
	
	@Test
	public void testCost(){
		MoneyKeeper keeper=(MoneyKeeper) Operator.data.getData("money");
		MoneyType money;
		ReasonKeeper rk=(ReasonKeeper) Operator.data.getData("reason");
		ReasonType reason;
		
		Operator.addMoneyType("type one");
		Operator.addReason("reason one");
		Operator.income("type one", 10, "reason one");
		
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(10, money.getValue(), 1e-3);
		reason=(ReasonType) rk.getItem("reason one");
		assertEquals(10, reason.getIncome(), 1e-3);
		
		Operator.backup();
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(0, money.getValue(), 1e-3);
		reason=(ReasonType) rk.getItem("reason one");
		assertEquals(0, reason.getIncome(), 1e-3);
		
		Operator.income("type one", 10, "reason one");
		Operator.expenditure("type one", 5, "reason one");
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(5, money.getValue(), 1e-3);
		
		Operator.backup();
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(10, money.getValue(), 1e-3);
		
		Operator.backup();
		
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(0, money.getValue(), 1e-3);
		
		Operator.addMoneyType("type two");
		Operator.income("type two", 100, "reason one");
		Operator.transfer("type two", "type one", 30);
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(30, money.getValue(), 1e-3);
		money=(MoneyType) keeper.getItem("type two");
		assertEquals(70, money.getValue(), 1e-3);
		
		Operator.backup();
		money=(MoneyType) keeper.getItem("type one");
		assertEquals(0, money.getValue(), 1e-3);
		money=(MoneyType) keeper.getItem("type two");
		assertEquals(100, money.getValue(), 1e-3);
	}
	
	@Test
	public void testReason(){
		ReasonKeeper keeper=(ReasonKeeper) Operator.data.getData("reason");
		ReasonType reason;
		Operator.addReason("reason one");
		Operator.addMoneyType("type one");
		Operator.income("type one", 10, "reason one");
		Operator.renameReason("reason one", "reason two");
		Operator.removeReason("reason two");
		
		assertEquals(0, keeper.getAllItem().size());
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason two"));
		reason=(ReasonType) keeper.getItem("reason two");
		assertEquals(10, reason.getIncome(), 1e-3);
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason one"));
		reason=(ReasonType) keeper.getItem("reason one");
		assertEquals(10, reason.getIncome(), 1e-3);
		
		Operator.backup();
		assertTrue(keeper.itemExist("reason one"));
		reason=(ReasonType) keeper.getItem("reason one");
		assertEquals(0, reason.getIncome(), 1e-3);
		
		Operator.backup();
		Operator.backup();
		assertEquals(0, keeper.getAllItem().size());
	}
	
	@Test
	public void testDebt(){
		DebtKeeper keeper=(DebtKeeper) Operator.data.getData("debt");
		DebtType debt;
		MoneyKeeper mk=(MoneyKeeper) Operator.data.getData("money");
		MoneyType money;
		
		Operator.addMoneyType("type one");
		
		Operator.addBorrowing("name", 10, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.addLoan("name2", 5, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.repayLoan(1, 5, "type one");
		Operator.repayBorrowing(0, 10, "type one");
		
		assertEquals(0, keeper.getAllItem().size());
		
		Operator.backup();
		assertEquals(1, keeper.getAllItem().size());
		debt=(DebtType) keeper.getItem("0");
		assertEquals(10, debt.getValue(), 1e-3);
		assertEquals("name", debt.getCreditor());
		money=(MoneyType) mk.getItem("type one");
		assertEquals(10, money.getValue(), 1e-3);
		
		Operator.backup();
		assertEquals(2, keeper.getAllItem().size());
		debt=(DebtType) keeper.getItem("1");
		assertEquals(5, debt.getValue(), 1e-3);
		assertEquals("name2", debt.getCreditor());
		money=(MoneyType) mk.getItem("type one");
		assertEquals(5, money.getValue(), 1e-3);
		
		Operator.backup();
		assertEquals(1, keeper.getAllItem().size());
		debt=(DebtType) keeper.getItem("0");
		assertEquals(10, debt.getValue(), 1e-3);
		assertEquals("name", debt.getCreditor());
		money=(MoneyType) mk.getItem("type one");
		assertEquals(10, money.getValue(), 1e-3);
		
		Operator.backup();
		assertEquals(0, keeper.getAllItem().size());
		money=(MoneyType) mk.getItem("type one");
		assertEquals(0, money.getValue(), 1e-3);
	}

}
