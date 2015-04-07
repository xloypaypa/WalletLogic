package logic.process.backup.debt;

import data.DebtKeeper;
import logic.process.AbstractProcess;

public class AddDebtBackup extends AbstractProcess {

	public AddDebtBackup(String processName) {
		super(processName);
	}

	@Override
	public void backup() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		keeper.delete(detail.getExtraMessage("debt id"));
	}

}
