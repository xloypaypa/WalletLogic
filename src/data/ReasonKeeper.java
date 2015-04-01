package data;

import type.ReasonTreeNodeType;
import type.ReasonType;
import type.Type;
import database.DataBase;

public class ReasonKeeper extends IDTypeKeeper {

	String typeName="reason";
	
	@Override
	public boolean isThisKeeper(String keeper) {
		if (keeper.equals(typeName)) return true;
		else return false;
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
		ans.setAimFile("/"+username+"/"+typeName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
