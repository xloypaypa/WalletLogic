package data;

import type.MoneyType;
import type.Type;
import database.DataBase;

public class MoneyKeeper extends IDTypeKeeper {
	
	public MoneyKeeper() {
		super("money");
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
