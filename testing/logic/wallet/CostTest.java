package logic.wallet;

import static org.junit.Assert.assertEquals;
import logic.User;

import org.junit.Test;

public class CostTest extends WalletTest {
	
	@Test
	public void testNull(){
		Money money=new Money();
		Cost cost=new Cost();
		assertEquals(0, money.getAllTypeName().size());
		money.addType("type one");
		cost.addChange("type one", -1, "");
		User.reloadUserData();
		assertEquals(1, money.getMoney().get(0).getValue(),1e-3);
	}
	
	@Test
	public void testBaseFeature(){
		Money money=new Money();
		Cost cost=new Cost();
		assertEquals(0, money.getAllTypeName().size());
		money.addType("type one");
		money.addType("type two");
		cost.addChange("type one", -100, "");
		cost.transfer("type one", "type two", 30);
		assertEquals(70, money.getMoney().get(0).getValue(),1e-3);
		assertEquals(30, money.getMoney().get(1).getValue(),1e-3);
	}

}
