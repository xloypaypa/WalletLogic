package logic;

import org.junit.After;
import org.junit.Before;

import data.UserPublicData;
import database.AbstractDataBase;
import database.HHD;

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

}
