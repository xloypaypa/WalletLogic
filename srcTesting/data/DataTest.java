package data;

import org.junit.After;
import org.junit.Before;

import database.AbstractDataBase;
import database.MongoManager;

public class DataTest {

	@Before
	public void setDB(){
		AbstractDataBase.root="testc";
		MongoManager.loadDB("testc");
	}
	
	@After
	public void removeDB(){
		MongoManager.clean("user");
		MongoManager.clean("debt");
		MongoManager.clean("detail");
		MongoManager.clean("reason");
		MongoManager.clean("money");
	}

}
