package database;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import type.Type;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class Type2DB {
	
	public static DBObject getDBObject(Type type) {
		Element element = type.getRoot();
		Element extra=null;
		String message = new String("{");
		int number = 0;
		return solveTypeMessage(element, extra, message, number);
	}
	
	public static DBObject getNameDBObject(Type type, String username) {
		Element element = type.getRoot();
		Element extra=null;
		String message = new String("{'username':'"+username+"'");
		int number = 1;
		return solveTypeMessage(element, extra, message, number);
	}

	private static DBObject solveTypeMessage(Element element, Element extra,
			String message, int number) {
		for (int i=0;i<element.elements().size();i++) {
			Element now = (Element) element.elements().get(i);
			if (now.getName().equals("extra")) {
				extra = now;
			}else{
				if (number != 0) message += ","; 
				message += "'" + now.getName()+ "':'" + now.getText()+"'";
				number++;
			}
		}
		if (extra!=null) {
			for (int i=0;i<extra.elements().size();i++) {
				Element now = (Element) extra.elements().get(i);
				if (number != 0) message += ","; 
				message += "'extra_" + now.getName()+ "':'" + now.getText()+"'";
				number++;
			}
		}
		message += "}";
		return (BasicDBObject) JSON.parse(message);
	}
	
	public static Element getElement(DBObject object) {
		Set<String> allkeys = object.keySet();
		Iterator<String> it = allkeys.iterator();
		Element element = DocumentHelper.createElement("type");
		Vector<String> extras = new Vector<String>();
		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("_id")) {
				continue;
			} else if (key.indexOf("extra_") == 0){
				extras.add(key.substring("extra_".length()));
			}else{
				String value = (String) object.get(key);
				element.addElement(key).setText(value);
			}
		}
		if (extras.size()!=0) {
			Element extra = element.addElement("extra");
			for (int i=0;i<extras.size();i++) {
				String key = extras.get(i);
				String value = (String) object.get("extra_"+key);
				extra.addElement(key).setText(value);
			}
		}
		return element;
	}
	
	public static Element getElement(DBObject object, Set<String> ingore) {
		Set<String> allkeys = object.keySet();
		Iterator<String> it = allkeys.iterator();
		Element element = DocumentHelper.createElement("type");
		Vector<String> extras = new Vector<String>();
		while (it.hasNext()) {
			String key = it.next();
			if (ingore.contains(key)) {
				continue;
			} else if (key.equals("_id")) {
				continue;
			} else if (key.indexOf("extra_") == 0){
				extras.add(key.substring("extra_".length()));
			} else {
				String value = (String) object.get(key);
				element.addElement(key).setText(value);
			}
		}
		if (extras.size()!=0) {
			Element extra = element.addElement("extra");
			for (int i=0;i<extras.size();i++) {
				String key = extras.get(i);
				String value = (String) object.get("extra_"+key);
				extra.addElement(key).setText(value);
			}
		}
		return element;
	}
	
}
