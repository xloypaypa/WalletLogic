package logic;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;

import database.DataBase;
import database.HHD;

public class TestCase {
	@Before
	public void setDB(){
		if (!HHD.fileExiste(HHD.getAppPath()+"/wallet root file.txt")){
			HHD.createFile(HHD.getAppPath(), "wallet root file.txt");
			HHD.writeFile(HHD.getAppPath()+"/wallet root file.txt", HHD.getAppPath());
		}
		
		Vector <String> root=HHD.readFile(HHD.getAppPath()+"/wallet root file.txt");
		DataBase.Root=root.get(0);
	}

	@After
	public void removeDB(){
		HHD.deleteFile(HHD.getAppPath()+"/wallet root file.txt");
		User.loadUser();
		Vector <String> a=User.getAllUserName();
		HHD.deleteFile(HHD.getAppPath()+"/all user.txt");
		if (a!=null){
			for (int i=0;i<a.size();i++){
				HHD.deleteFolder(HHD.getAppPath()+"/"+a.get(i));
			}
		}
	}

}
