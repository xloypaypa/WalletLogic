package database.operator;

import type.DebtType;
import type.Type;
import database.usernameDB.NormalDB;

public class DebtOperator extends IDTypeOperator {

	public DebtOperator() {
		super("debt");
	}

	protected void loadDataBase() {
		NormalDB ans = new NormalDB(username, "debt") {
			
			@Override
			public Type getNewType() {
				return new DebtType();
			}
		};
		this.db=ans;
	}

}
