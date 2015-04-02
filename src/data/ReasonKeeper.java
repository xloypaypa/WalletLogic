package data;

import type.ReasonTreeNodeType;
import type.ReasonType;
import type.Type;
import database.DataBase;

public class ReasonKeeper extends IDTypeKeeper {

	public ReasonKeeper() {
		super("reason");
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				if (userType.equals("tree")){
					return new ReasonTreeNodeType();
				}else{
					return new ReasonType();
				}
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
