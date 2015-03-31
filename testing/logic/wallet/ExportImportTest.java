package logic.wallet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import database.DataBase;
import database.HHD;

public class ExportImportTest extends WalletTest {
	
	@After
	public void removeDB(){
		super.removeDB();
		HHD.deleteFile(DataBase.Root+"/detail.txt");
		HHD.deleteFile(DataBase.Root+"/detail.xls");
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
		
		Detail detail=new Detail();
		detail.export(DataBase.Root+"/detail.txt");
		assertTrue(HHD.fileExiste(DataBase.Root+"/detail.txt"));
		detail.importDetail(DataBase.Root+"/detail.txt");
		
		assertEquals(70, money.getMoney().get(0).getValue(),1e-3);
		assertEquals(30, money.getMoney().get(1).getValue(),1e-3);
	}
	
	@Test
	public void testExcel(){
		Money money=new Money();
		Cost cost=new Cost();
		assertEquals(0, money.getAllTypeName().size());
		money.addType("type one");
		money.addType("type two");
		cost.addChange("type one", -100, "");
		cost.transfer("type one", "type two", 30);
		assertEquals(70, money.getMoney().get(0).getValue(),1e-3);
		assertEquals(30, money.getMoney().get(1).getValue(),1e-3);
		
		Detail detail=new Detail();
		detail.getExcel(DataBase.Root+"/detail.xls");
		assertTrue(HHD.fileExiste(DataBase.Root+"/detail.xls"));
	}

}
