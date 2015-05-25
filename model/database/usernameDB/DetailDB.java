package database.usernameDB;

import java.util.Vector;

import com.mongodb.DBObject;

import type.DetailType;
import type.Type;
import database.MongoManager;
import database.Type2DB;

public class DetailDB extends UsernameDB {

	@Override
	public void addItem(Type type) {
		MongoManager.addMessage(aimPath, Type2DB.getNameDBObject(type, username));
	}

	@Override
	public Type getNewType() {
		return new DetailType();
	}

	@Override
	public Vector<Type> load() {
		Vector<DBObject> db = MongoManager.getUserMessage(aimPath, username);
		Vector<Type> ans = new Vector<Type>();
		for (int i=0;i<db.size();i++) {
			DBObject nowMessage = db.get(i);
			DetailType now = (DetailType) getNewType();
			now.solveTypeMessage(Type2DB.getElement(nowMessage, ingore));
			ans.addElement(now);
		}
		return ans;
	}
	
	public void remvoeLastDetail() {
		MongoManager.removeLastItem(aimPath, username);
	}

}
