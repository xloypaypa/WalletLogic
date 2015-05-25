package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.TimeGeter;
import type.MoneyType;
import type.ReasonType;
import database.AbstractDataBase;
import database.operator.DebtOperator;
import database.operator.MoneyOperator;
import database.operator.ReasonOperator;

public class TreeExportImportTest extends TreeOperatorTest {
	
	@Test
	public void testBaseFeature(){
		String path=AbstractDataBase.root;
		
		MoneyOperator mk=(MoneyOperator) Operator.data.getData("money");
		DebtOperator dk=(DebtOperator) Operator.data.getData("debt");
		ReasonOperator rk=(ReasonOperator) Operator.data.getData("reason");
		
		Operator.addMoneyType("type one");
		Operator.addTreeReason("reason one", "root", 0, 100, 0);
		Operator.addBorrowing("name", 100, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.expenditure("type one", 20, "reason one");
		Operator.renameMoneyType("type one", "type two");
		Operator.renameTreeReason("reason one", "reason two", "root", 0, 100, 0);
		Operator.addTreeReason("reason three", "root", 0, 100, 0);
		Operator.removeReason("reason three");
		Operator.repayBorrowing(0, 20, "type two");
		Operator.income("type two", 10, "reason two");
		Operator.addMoneyType("type three");
		Operator.transfer("type two", "type three", 6);
		Operator.addLoan("name", 5, "type three", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.repayLoan(1, 3, "type two");
		Operator.removeMoneyType("type three");
		
		Operator.export(path);
		
		Operator.data.releaseAllData();
		assertFalse(mk.itemExist("type one"));
		
		Operator.doDetail(path+"/detail.txt");
		
		MoneyType money=(MoneyType) mk.getItem("type two");
		ReasonType reason=(ReasonType) rk.getItem("reason two");
		assertNotNull(money);
		assertEquals(67, money.getValue(), 1e-3);
		
		assertNotNull(reason);
		assertEquals(20, reason.getExpenditure(), 1e-3);
		assertEquals(10, reason.getIncome(), 1e-3);
		
		assertFalse(rk.itemExist("reason three"));
		assertFalse(mk.itemExist("type three"));
		assertTrue(dk.itemExist("0"));
		assertTrue(dk.itemExist("1"));
	}

}
