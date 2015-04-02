package logic.event;

import java.util.Date;

import logic.action.AddBorrowingAction;
import logic.action.IncomeAction;
import logic.check.AfterTimeChecker;
import logic.check.ValueSignCheck;

public class AddBorrowingEvent extends AddDebtEvent {

	@Override
	public void doEvent() {
		AddBorrowingAction aa=(AddBorrowingAction) this.getAction("add debt");
		aa.setValue(creditor, value, deadline, rate, rateType);
		IncomeAction ia=(IncomeAction) this.getAction("income");
		ia.setValue(moneyType, value);
		ValueSignCheck vsc=(ValueSignCheck) this.getCheck("value sign");
		vsc.setValue(value);
		AfterTimeChecker atc=(AfterTimeChecker) this.getCheck("after time");
		atc.setValue(new Date(), deadline);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

	@Override
	protected void loadAction() {
		this.addAction(new AddBorrowingAction());
		this.addAction(new IncomeAction());
	}

	@Override
	protected void loadCheck() {
		this.addCheck(new ValueSignCheck());
		this.addCheck(new AfterTimeChecker());
	}

}
