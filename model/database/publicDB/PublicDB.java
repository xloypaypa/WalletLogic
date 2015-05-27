package database.publicDB;

import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import type.Type;
import type.UserMessage;
import database.AbstractDataBase;
import database.IDDataBase;
import database.MongoManager;
import database.Type2DB;

public class PublicDB extends AbstractDataBase implements IDDataBase {
	
	public PublicDB() {
		aimPath = "user";
	}

	@Override
	public void addItem(Type type) {
		MongoManager.addMessage(aimPath, Type2DB.getDBObject(type));
	}

	@Override
	public Type getNewType() {
		return new UserMessage();
	}

	@Override
	public Vector<Type> load() {
		Vector<DBObject> db = MongoManager.getMessage(aimPath);
		Vector<Type> ans = new Vector<Type>();
		for (int i=0;i<db.size();i++) {
			DBObject nowMessage = db.get(i);
			UserMessage now = (UserMessage) getNewType();
			now.solveTypeMessage(Type2DB.getElement(nowMessage));
			ans.addElement(now);
		}
		return ans;
	}

	@Override
	public void removeItem(String id) {
		MongoManager.removeMessage(aimPath, (BasicDBObject) JSON.parse("{'id':'"+id+"'}"));
	}

	@Override
	public void updateItem(String id, Type type) {
		MongoManager.updateMessage(aimPath, (BasicDBObject) JSON.parse("{'id':'"+id+"'}"), Type2DB.getDBObject(type));
	}

}
