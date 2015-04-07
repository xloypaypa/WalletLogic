package database.noPassword;

import java.util.Vector;

import database.AbstractDataBase;
import database.HHD;

public abstract class NoPasswordDataBase extends AbstractDataBase {
	
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

}
