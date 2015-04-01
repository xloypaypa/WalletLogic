package database;

import java.util.Vector;

import type.Type;

public abstract class AbstractDataBase {
	
	static public String root;
	protected String aimPath;
	
	public static String getTypeMessage(Vector <String> message){
		for (int i=0;i<message.size();i++){
			if (message.get(i).equals("[type name]")){
				return message.get(i+1);
			}
		}
		return null;
	}
	
	public Vector<Vector<String>> loadNotEncryptedFile(String path){
		Vector <String> file=HHD.readFile(aimPath);
		Vector < Vector <String> > ans=new Vector<Vector<String>>();
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				ans.add(message);
				message=new Vector<String>();
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
		return ans;
	}
	
	public void clean(){
		HHD.cleanFile(aimPath);
	}
	
	public abstract void addItem(Type type);
	
	public abstract void removeItem(String id);
	
	public abstract void updateItem(String id,Type type);
	
	public abstract Vector <Type> load();
	
}
