package logic;

import org.junit.After;
import org.junit.Before;

import database.AbstractDataBase;
import database.MongoManager;
import database.operator.UserPublicData;

public class LogicTest {

	@Before
	public void setDB(){
		AbstractDataBase.root="testc";
		MongoManager.loadDB("testc");
		UserPublicData.loadUser();
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
