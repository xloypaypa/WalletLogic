package data;

import type.MoneyType;
import type.Type;
import database.DataBase;

public class MoneyKeeper extends IDTypeKeeper {
	
	String typeName="money";
	
	@Override
	public boolean isThisKeeper(String keeper) {
		if (keeper.equals(typeName)) return true;
		else return false;
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		ans.setAimFile("/"+username+"/"+typeName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
