package logic.process.backup.money;

import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.process.AbstractProcess;

public class TransferBackup extends AbstractProcess {
	
	public TransferBackup() {
		super("transfer");
	}

	@Override
	public void backup() {
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.action();
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getExtraMessage("from type"), detail.getValue());
		ia.action();
	}

}
