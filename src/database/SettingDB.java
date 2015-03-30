package database;

import java.util.Vector;

public class SettingDB extends DataBase {
	Vector <DataBaseReadLisenter> readListener;
	Vector <DataBaseWriteListener> writeListener;
	
	public SettingDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+settingPath);
	}
	
	public void addDataBaseReadListenser(DataBaseReadLisenter listener){
		this.readListener.addElement(listener);
	}
	
	public void addDataBaseWriteListenser(DataBaseWriteListener listener){
		this.writeListener.addElement(listener);
	}
	
	public void clearListener(){
		this.readListener.removeAllElements();
		this.writeListener.removeAllElements();
	}
	
	public void load(){
		Vector <Vector <String>> all=this.loadFile();
		for (int i=0;i<all.size();i++){
			DataBaseReadLisenter.setMessage(all.get(i));
			checkReadListener();
		}
	}
	
	public void push(){
		checkWriteListener();
	}

	private void checkReadListener() {
		for (int j=0;j<this.readListener.size();j++){
			if (this.readListener.get(j).needAction()){
				this.readListener.get(j).loadAction();
			}
		}
	}
	
	private void checkWriteListener(){
		for (int i=0;i<this.writeListener.size();i++){
			this.writeListener.get(i).setPath(this.getAimPath());
			this.writeListener.get(i).setPassword(passWord);
			this.writeListener.get(i).writeAction();
			this.writeListener.get(i).writeFile();
		}
	}
}
