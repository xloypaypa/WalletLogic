package database;

import java.util.Vector;

public abstract class DataBaseReadLisenter {
	static String type;
	static Vector <String> message;
	public static void setMessage(Vector <String> message){
		DataBaseReadLisenter.message=message;
		type=DataBase.getTypeMessage(message);
	}
	
	public static String getType(){
		return type;
	}
	
	public static Vector <String> getMessage(){
		return message;
	}
	
	public abstract boolean needAction();
	public abstract void loadAction();
}
