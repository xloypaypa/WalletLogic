package data;


import org.junit.After;
import org.junit.Before;

import database.AbstractDataBase;
import database.HHD;

public class DataTest {

	@Before
	public void setDB(){
		HHD.createFolder(HHD.getAppPath(), "testing space");
		AbstractDataBase.root=HHD.getAppPath()+"/testing space";
	}
	
	@After
	public void removeDB(){
		HHD.deleteFolder(HHD.getAppPath()+"/testing space");
	}

}
