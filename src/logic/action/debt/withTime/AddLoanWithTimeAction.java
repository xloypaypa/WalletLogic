package logic.action.debt.withTime;

import logic.LogicWithUIMessage;
import type.DebtType;
import data.DebtKeeper;

public class AddLoanWithTimeAction extends AddDebtWithTimeAction {

	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setStartingTime(time);
		debt.setLastUpdateTime(time);
		debt.setDebtType("loan");
		keeper.add(debt);
		LogicWithUIMessage.setOKMessage();
	}

}
