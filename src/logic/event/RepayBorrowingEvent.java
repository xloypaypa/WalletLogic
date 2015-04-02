package logic.event;

import logic.action.ExpenditureAction;
import logic.action.RepayBorrowingAction;
import logic.check.DebtValueLimitCheck;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;

public class RepayBorrowingEvent extends RepayDebtEvent {

	@Override
	public void doEvent() {
		RepayBorrowingAction ra=(RepayBorrowingAction) this.getAction("repay debt");
		ra.setValue(id, value, moneyType);
		ExpenditureAction ea=(ExpenditureAction) this.getAction("expenditure");
		ea.setValue(moneyType, value);
		DebtValueLimitCheck dvlc=(DebtValueLimitCheck) this.getCheck("debt value limit");
		dvlc.setValue(id, value);
		MoneyEnoughCheck mec=(MoneyEnoughCheck) this.getCheck("money enough");
		mec.setValue(moneyType, value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

	@Override
	protected void loadAction() {
		this.addAction(new RepayBorrowingAction());
		this.addAction(new ExpenditureAction());
	}

	@Override
	protected void loadCheck() {
		this.addCheck(new DebtValueLimitCheck());
		this.addCheck(new ValueSignCheck());
		this.addCheck(new MoneyEnoughCheck());
	}

}
