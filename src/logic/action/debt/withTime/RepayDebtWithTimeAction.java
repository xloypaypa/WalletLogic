package logic.action.debt.withTime;

import java.util.Date;

import type.DebtType;
import data.DebtKeeper;
import logic.action.debt.RepayDebtAction;

public class RepayDebtWithTimeAction extends RepayDebtAction {
	
	Date time;
	
	public void setTime(Date time){
		this.time=time;
	}

	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		debt.repayment(value, time);
		if (debt.getMaxRepay()>1e-3) keeper.update(id+"", debt);
		else keeper.delete(id+"");
	}

}
