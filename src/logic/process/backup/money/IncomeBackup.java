package logic.process.backup.money;

import logic.action.money.ExpenditureAction;
import logic.process.AbstractProcess;

public class IncomeBackup extends AbstractProcess {

	public IncomeBackup() {
		super("income");
	}

	@Override
	public void backup() {
		ExpenditureAction ia=new ExpenditureAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.action();
	}

}
