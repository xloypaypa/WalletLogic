package database.operator;

import type.MoneyType;
import type.Type;
import database.usernameDB.NormalDB;

public class MoneyOperator extends IDTypeOperator {
	
	public MoneyOperator() {
		super("money");
	}

	protected void loadDataBase() {
		NormalDB ans = new NormalDB(username, "money") {
			
			@Override
			public Type getNewType() {
				return new MoneyType(); 
			}
		};
		this.db=ans;
	}

}
