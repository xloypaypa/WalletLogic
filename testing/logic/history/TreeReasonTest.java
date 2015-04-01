package logic.history;

import static org.junit.Assert.*;
import logic.User;

import org.junit.Before;
import org.junit.Test;

import type.ReasonTreeNodeType;

public class TreeReasonTest extends HistoryTest {
	
	@Override
	@Before
	public void setDB(){
		super.setDB();
		User.addUser("name", "pass", "tree");
		User.login("name", "pass");
	}

	@Test
	public void testBaseFeature() {
		TreeReasonHistory tr=new TreeReasonHistory();
		ReasonTreeNodeType node;
		tr.addReason("root", "reason one", 0, 1, 2);
		tr.addReason("reason one", "reason two", 0, 1, 2);
		node=(ReasonTreeNodeType) tr.getReasonType().get(1);
		assertEquals("reason one", node.getFather());
		
		assertTrue(tr.haveFather("reason two", "reason one"));
		
		tr.changeReason("reason one", "root", "reason three", 1, 2, 3);
		assertEquals("reason three", node.getFather());
		
		tr.removeReason("reason three");
		assertEquals("root", node.getFather());
	}
	
	@Test
	public void testIO(){
		TreeReasonHistory tr=new TreeReasonHistory();
		ReasonTreeNodeType node;
		tr.addReason("root", "reason one", 0, 10, 2);
		tr.addReason("reason one", "reason two", 0, 50, 2);
		assertFalse(tr.checkExpenditure("reason two", 30));
		assertTrue(tr.checkExpenditure("reason two", 10));
		tr.addExpenditure("reason two", 5);
		node=(ReasonTreeNodeType) tr.getReasonType().get(0);
		assertEquals("reason one", node.getName());
		assertEquals(5, node.getExpenditure(),1e-3);
		assertEquals(0, tr.solveExpenditure(node),1e-3);
		
		
		tr.addIncome("reason one", 3);
		node=(ReasonTreeNodeType) tr.getReasonType().get(1);
		assertEquals("reason two", node.getName());
		assertEquals(0, node.getIncome(),1e-3);
		node=(ReasonTreeNodeType) tr.getReasonType().get(0);
		assertEquals(3, node.getIncome(),1e-3);
		assertEquals(3, tr.solveIncome(node),1e-3);
	}
	
	@Test
	public void testTree(){
		TreeReasonHistory tr=new TreeReasonHistory();
		tr.addReason("root", "1", 0, 10, 2);
		tr.addReason("1", "2", 0, 50, 2);
		tr.addReason("1", "3", 0, 50, 2);
		assertEquals(2, tr.getKidName("1").size());
		assertEquals(1, tr.getRoots().size());
		tr.addReason("root", "4", 0, 50, 2);
		assertEquals(2, tr.getRoots().size());
		assertEquals("1", tr.getRoots().get(0).getName());
		assertEquals("4", tr.getRoots().get(1).getName());
	}
	
	@Test
	public void testBug1(){
		TreeReasonHistory tr=new TreeReasonHistory();
		ReasonTreeNodeType node;
		tr.addReason("root", "father",0,50,1);
		tr.addReason("father", "kid",0,100,1);
		tr.addIncome("kid", 100);
		
		node=(ReasonTreeNodeType) tr.getReasonType().get(0);
		assertEquals(100, node.getIncome(),1e-3);
		node=(ReasonTreeNodeType) tr.getReasonType().get(1);
		assertEquals(100, node.getIncome(),1e-3);
		
		tr.checkExpenditure("father", 50);
		tr.addExpenditure("father", 50);
		User.reloadUserData();
		
		node=(ReasonTreeNodeType) tr.getReasonType().get(0);
		assertEquals(100, node.getIncome(),1e-3);
		node=(ReasonTreeNodeType) tr.getReasonType().get(1);
		assertEquals(100, node.getIncome(),1e-3);
	}

}
