package dataViewer;

import static org.junit.Assert.*;

import org.junit.Test;

import database.viewer.DebtViewer;
import database.viewer.MoneyHistoryViewer;
import tool.TimeGeter;
import logic.Operator;
import logic.OperatorTest;

public class MoneyHistoryViewerTest extends OperatorTest {

	@Test
	public void test() {
		DebtViewer debt=new DebtViewer(); debt.loadData("debt");
		Operator.addMoneyType("type one");
		Operator.addReason("reason one");
		Operator.addBorrowing("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.repayBorrowing(0, 1, "type one");
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		Operator.income("type one", 2, "reason one");
		Operator.addLoan("name", 1, "type one", TimeGeter.getTime(2100, 1, 1), 0, "null");
		MoneyHistoryViewer m=new MoneyHistoryViewer();
		Operator.renameMoneyType("type one", "type two");
		Operator.addMoneyType("type one");
		Operator.transfer("type two", "type one", 1);
		
		assertEquals("type two", m.getHistoricalType().get(0).getName());
		assertEquals(7, m.getHistoricalType().get(0).getHistroySize());
	}

}
