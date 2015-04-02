package logic.action;

import java.util.Date;

import type.DebtType;
import data.DebtKeeper;

public class AddLoanAction extends AddDebtAction {

	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=new DebtType();
		debt.setCreditor(creditor);
		debt.setDeadline(deadline);
		debt.setRate(rateType, rate);
		debt.setDebtType("loan");
		debt.setLastUpdateTime(new Date());
		debt.setStartingTime(new Date());
		debt.setValue(value);
		debt.setID(getNewID());
		keeper.add(debt);
		setOKMessage();
	}

}
