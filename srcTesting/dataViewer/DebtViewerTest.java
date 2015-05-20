package dataViewer;

import static org.junit.Assert.*;

import org.junit.Test;

import database.viewer.DebtViewer;
import tool.TimeGeter;
import logic.Operator;
import logic.OperatorTest;

public class DebtViewerTest extends OperatorTest {

	@Test
	public void testBaseFeature() {
		DebtViewer debt=new DebtViewer(); debt.loadData("debt");
		Operator.addMoneyType("type one");
		Operator.addReason("reason one");
		Operator.addBorrowing("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		assertEquals(1, debt.getAllID().size());
		assertEquals(1, debt.getDebtType("borrowing").size());
		assertEquals(0, debt.getDebtType("loan").size());
		assertNotNull(debt.getItem("0"));
		Operator.repayBorrowing(0, 1, "type one");
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.income("type one", 2, "reason one");
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		assertEquals(1, debt.getAllID().size());
		assertNotNull(debt.getItem("0"));
	}

}
