package logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.AbstractDataBase;
import database.HHD;
import database.operator.DetailOperator;
import database.operator.UserPublicData;

public class LogicTest {

	@Before
	public void setDB(){
		HHD.createFolder(HHD.getAppPath(), "testing space");
		AbstractDataBase.root=HHD.getAppPath()+"/testing space";
		UserPublicData.loadUser();
	}
	
	@After
	public void removeDB(){
		HHD.deleteFolder(HHD.getAppPath()+"/testing space");
	}
	
	@Test
	public void testUpdate() {
		Operator.register("name", "pass", "pass", "tree");
		Operator.login("name", "pass");
		Operator.addMoneyType("type");
		Operator.login("name", "pass");
		DetailOperator detail = (DetailOperator) Operator.data.getData("detail");
		assertEquals("add type", detail.getLastDetail().getEvent());
	}

}
