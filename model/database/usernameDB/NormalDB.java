package database.usernameDB;

import java.util.Vector;

import com.mongodb.DBObject;

import type.Type;
import database.IDDataBase;
import database.MongoManager;
import database.Type2DB;

public abstract class NormalDB extends UsernameDB implements IDDataBase {

	public NormalDB(String username, String aimPath) {
		super(username, aimPath);
	}

	@Override
	public void removeItem(String id) {
		MongoManager.removeMessage(aimPath, "id", id);
	}

	@Override
	public void updateItem(String id, Type type) {
		MongoManager.updateMessage(aimPath, "id", id, Type2DB.getNameDBObject(type, username));
	}

	@Override
	public void addItem(Type type) {
		MongoManager.addMessage(aimPath, Type2DB.getNameDBObject(type, username));
	}

	@Override
	public Vector<Type> load() {
		Vector<DBObject> db = MongoManager.getUserMessage(aimPath, username);
		Vector<Type> ans = new Vector<Type>();
		for (int i=0;i<db.size();i++) {
			DBObject nowMessage = db.get(i);
			Type now = getNewType();
			now.solveTypeMessage(Type2DB.getElement(nowMessage, ingore));
			ans.addElement(now);
		}
		return ans;
	}

}
