package logic.event.debt;

import java.util.Date;

import logic.action.debt.AddLoanAction;
import logic.action.money.ExpenditureAction;
import logic.check.AfterTimeChecker;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;

public class AddLoanEvent extends AddDebtEvent {

	@Override
	public void doEvent() {
		AddLoanAction aa=(AddLoanAction) this.getAction("add debt");
		aa.setValue(creditor, value, deadline, rate, rateType);
		ExpenditureAction ia=(ExpenditureAction) this.getAction("expenditure");
		ia.setValue(moneyType, value);
		ValueSignCheck vsc=(ValueSignCheck) this.getCheck("value sign");
		vsc.setValue(value);
		AfterTimeChecker atc=(AfterTimeChecker) this.getCheck("after time");
		atc.setValue(new Date(), deadline);
		MoneyEnoughCheck mec=(MoneyEnoughCheck) this.getCheck("money enough");
		mec.setValue(moneyType, value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

	@Override
	protected void loadAction() {
		this.addAction(new AddLoanAction());
		this.addAction(new ExpenditureAction());
	}

	@Override
	protected void loadCheck() {
		this.addCheck(new ValueSignCheck());
		this.addCheck(new AfterTimeChecker());
		this.addCheck(new MoneyEnoughCheck());
	}

}
