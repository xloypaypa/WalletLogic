package logic.history;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReasonTest extends HistoryTest {

	@Test
	public void testBaseFeature() {
		ReasonHistory rh=new ReasonHistory();
		rh.addReason("abc");
		rh.changeReasonName("abc", "ddd");
		rh.addReason("c");
		rh.removeReason("c");
		assertEquals(1, rh.getAllReasonName().size());
		assertEquals("ddd", rh.getAllReasonName().get(0));
	}
	
	@Test
	public void testIO(){
		ReasonHistory rh=new ReasonHistory();
		rh.addReason("abc");
		rh.addExpenditure("abc", 1);
		rh.addIncome("abc", 2);
		assertEquals(1, rh.getReasonType().get(0).getExpenditure(),1e-3);
		assertEquals(2, rh.getReasonType().get(0).getIncome(),1e-3);
	}

}
