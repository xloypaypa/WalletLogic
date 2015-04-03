package logic.event.debt;

import logic.action.debt.RepayLoanAction;
import logic.action.money.IncomeAction;
import logic.check.DebtValueLimitCheck;
import logic.check.ValueSignCheck;

public class RepayLoanEvent extends RepayDebtEvent {

	@Override
	protected void loadAction() {
		this.addAction(new RepayLoanAction());
		this.addAction(new IncomeAction());
	}

	@Override
	public void doEvent() {
		RepayLoanAction ra=(RepayLoanAction) this.getAction("repay debt");
		ra.setValue(id, value, moneyType);
		IncomeAction ea=(IncomeAction) this.getAction("income");
		ea.setValue(moneyType, value);
		DebtValueLimitCheck dvlc=(DebtValueLimitCheck) this.getCheck("debt value limit");
		dvlc.setValue(id, value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

	@Override
	protected void loadCheck() {
		this.addCheck(new DebtValueLimitCheck());
		this.addCheck(new ValueSignCheck());
	}

}
