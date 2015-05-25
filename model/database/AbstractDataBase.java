package database;

import java.util.Vector;

import type.Type;

public abstract class AbstractDataBase {
	
	static public String root;
	protected String aimPath;
	
	public void setAimFile(String file){
		this.aimPath=file;
	}
	
	public String getAimFile(){
		return this.aimPath;
	}
	
	public void clean(){
		MongoManager.clean(aimPath);
	}
	
	public abstract void addItem(Type type);
	
	public abstract Type getNewType();
	
	public abstract Vector <Type> load();
	
}
