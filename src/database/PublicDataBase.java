package database;

import java.util.Vector;

import type.Type;
import type.UserMessage;

public class PublicDataBase extends AbstractDataBase {
	
	public PublicDataBase(){
		this.aimPath=root+"/all user.txt";
	}

	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.getAllMessage());
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
		HHD.writeFile(aimPath, ans);
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
		HHD.writeFile(aimPath, ans);
	}

	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector <Vector <String>> file=this.loadNotEncryptedFile(aimPath);
		
		for (int i=0;i<file.size();i++){
			UserMessage mu=new UserMessage();
			mu.solveTypeMessage(file.get(i));
			ans.add(mu);
		}
		return ans;
	}
}
