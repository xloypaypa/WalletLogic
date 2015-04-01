package data;

import type.DebtType;
import type.Type;
import database.DataBase;

public class DebtKeeper extends IDTypeKeeper {

	String typeName="debt";
	
	@Override
	public boolean isThisKeeper(String keeper) {
		if (keeper.equals(typeName)) return true;
		else return false;
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new DebtType();
			}
		};
		ans.setAimFile("/"+username+"/"+typeName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
