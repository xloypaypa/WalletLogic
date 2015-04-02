package logic.action;

import type.DebtType;
import data.DebtKeeper;

public class RepayLoanAction extends RepayDebtAction {

	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		debt.repayment(value);
		if (debt.getMaxRepay()>1e-3) keeper.update(id+"", debt);
		else keeper.delete(id+"");
		setOKMessage();
	}

}
