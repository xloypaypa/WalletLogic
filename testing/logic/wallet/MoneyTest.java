package logic.wallet;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest extends WalletTest {

	@Test
	public void testNull() {
		Money money=new Money();
		assertEquals(0, money.getAllTypeName().size());
		money.addType("type one");
		assertEquals(1, money.getAllTypeName().size());
	}
	
	@Test
	public void testBaseFeature(){
		Money money=new Money();
		money.addType("type one");
		assertEquals("type one", money.getAllTypeName().get(0));
		assertEquals(1, money.getAllTypeName().size());
		money.renameType("type one", "type two");
		assertEquals("type two", money.getAllTypeName().get(0));
		assertEquals(1, money.getAllTypeName().size());
		money.removeType("type two");
		assertEquals(0, money.getAllTypeName().size());
	}

}
