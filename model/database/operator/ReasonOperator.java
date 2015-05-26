package database.operator;

import type.ReasonType;
import type.Type;
import database.usernameDB.NormalDB;

public class ReasonOperator extends IDTypeOperator {

	public ReasonOperator() {
		super("reason");
	}

	protected void loadDataBase() {
		NormalDB ans = new NormalDB(username, "reason") {
			
			@Override
			public Type getNewType() {
				return new ReasonType(); 
			}
		};
		this.db=ans;
	}

}
