package database;

import type.Type;

public abstract class DataBaseWriteListener {
	String path,passWord;
	Type message;
	
	public void setPath(String path){
		this.path=path;
	}
	
	public void setPassword(String pass){
		this.passWord=pass;
	}
	
	public void setMessage(Type message){
		this.message=message;
	}
	
	public void writeFile(){
		HHD.addWriteFile(path, message.getAllMessage(), passWord);
	}
	
	public abstract void writeAction();
}
