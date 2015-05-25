package database;

import java.net.UnknownHostException;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class MongoManager {
	
	protected static DB db;
	
	public static void loadDB(String dbName) {
		try {
			@SuppressWarnings("deprecation")
			Mongo mongo = new Mongo();
			db = mongo.getDB(dbName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static Vector<DBObject> getUserMessage(String collectionName, String username) {
		Vector<DBObject> ans = new Vector<DBObject>();
		DBCollection collection = db.getCollection(collectionName);
		DBObject item = (BasicDBObject) JSON.parse("{'username':'"+username+"'}");
        DBCursor cur = collection.find(item);
        while (cur.hasNext()) {
        	ans.addElement(cur.next());
        }
		return ans;
	}
	
	public static Vector<DBObject> getMessage(String collectionName) {
		Vector<DBObject> ans = new Vector<DBObject>();
		DBCollection collection = db.getCollection(collectionName);
		DBCursor cur = collection.find();
		while (cur.hasNext()) {
        	ans.addElement(cur.next());
        }
		return ans;
	}
	
	public static void addMessage(String collectionName, DBObject item) {
		DBCollection collection = db.getCollection(collectionName);
		collection.insert(item);
	}
	
	public static void removeMessage(String collectionName, String id) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject item = (BasicDBObject) JSON.parse("{'_id':'"+id+"'}");
		collection.remove(item);
	}
	
	public static void updateMessage(String collectionName, String id, DBObject item) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject now = (BasicDBObject) JSON.parse("{'_id':'"+id+"'}");
		collection.update(now, item);
	}
	
	public static void clean(String collectionName) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject now = (BasicDBObject) JSON.parse("");
		collection.remove(now);
	}
	
	public static void removeLastItem(String collectionName, String username) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject item = (BasicDBObject) JSON.parse("{'username':'"+username+"'}");
		int count = collection.find(item).count();
		if (count==0) return ;
        DBObject last= collection.find(item).skip(count-1).next();
        item = (BasicDBObject) JSON.parse("{'_id':'"+last.get("_id")+"'}");
        collection.remove(item);
	}

}
