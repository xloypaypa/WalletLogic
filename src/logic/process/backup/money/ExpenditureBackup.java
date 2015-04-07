package logic.process.backup.money;

import logic.action.money.IncomeAction;
import logic.process.AbstractProcess;

public class ExpenditureBackup extends AbstractProcess {

	public ExpenditureBackup() {
		super("expenditure");
	}

	@Override
	public void backup() {
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.action();
	}

}
