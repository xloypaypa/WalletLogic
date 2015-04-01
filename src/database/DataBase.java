package database;

import java.util.Vector;

import type.Type;

public abstract class DataBase extends AbstractDataBase {
	
	Type aim;
	
	protected String passWord;

	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.getAllMessage(), passWord);
	}

	@Override
	public void removeItem(String id) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getTypeID().equals(id)){
				continue;
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans,passWord);
	}

	@Override
	public void updateItem(String id, Type type) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getTypeID().equals(id)){
				ans+=type.getAllMessage()+"\r\n";
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans,passWord);
	}

	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector <Vector <String>> file=this.loadFile(aimPath);
		
		for (int i=0;i<file.size();i++){
			aim=getNewType();
			aim.solveTypeMessage(file.get(i));
			ans.add(aim);
		}
		return ans;
	}
	
	public void setAimFile(String file){
		this.aimPath=AbstractDataBase.root+"/"+file;
	}
	
	public void setPassword(String passWord){
		this.passWord=passWord;
	}
	
	protected Vector<Vector<String>> loadFile(String path){
		Vector <String> file=HHD.readFile(aimPath, passWord);
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
	
	public abstract Type getNewType();
	
}
