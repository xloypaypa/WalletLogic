package logic.action.debt;

import type.DebtType;
import data.DebtKeeper;

public class AddLoanAction extends AddDebtAction {

	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setDebtType("loan");
		keeper.add(debt);
		setOKMessage();
	}

}
