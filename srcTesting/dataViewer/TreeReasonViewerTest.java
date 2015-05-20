package dataViewer;

import static org.junit.Assert.*;

import org.junit.Test;

import database.viewer.TreeReasonViewer;
import type.ReasonTreeNodeType;
import logic.Operator;
import logic.TreeOperatorTest;

public class TreeReasonViewerTest extends TreeOperatorTest {

	@Test
	public void testBaseFeature() {
		Operator.addTreeReason("reason one", "root", 0, 50, 0);
		Operator.addTreeReason("reason two", "reason one", 0, 0, 0);
		Operator.addTreeReason("reason three", "reason one", 0, 100, 0);
		TreeReasonViewer tree=new TreeReasonViewer();
		tree.loadData("reason");
		assertEquals(1, tree.getRoots().size());
		assertEquals("reason one", tree.getRoots().get(0).getTypeID());
		assertEquals(2, tree.getKid(tree.getRoots().get(0)).size());
		
		Operator.addMoneyType("type one");
		Operator.income("type one", 100, "reason two");
		Operator.expenditure("type one", 50, "reason three");
		
		assertEquals(0, tree.solveIncome((ReasonTreeNodeType) tree.getItem("reason one")), 1e-3);
		assertEquals(0, tree.solveExpenditure((ReasonTreeNodeType) tree.getItem("reason one")), 1e-3);
		
		assertEquals(100, tree.solveIncome((ReasonTreeNodeType) tree.getItem("reason two")), 1e-3);
		assertEquals(0, tree.solveExpenditure((ReasonTreeNodeType) tree.getItem("reason two")), 1e-3);
		
		assertEquals(0, tree.solveIncome((ReasonTreeNodeType) tree.getItem("reason three")), 1e-3);
		assertEquals(50, tree.solveExpenditure((ReasonTreeNodeType) tree.getItem("reason three")), 1e-3);
	}

}
