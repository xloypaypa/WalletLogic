package logic.process.backup.money;

import logic.action.money.AddTypeAction;
import logic.action.money.IncomeAction;
import logic.process.AbstractProcess;

public class RemoveTypeBackup extends AbstractProcess {
	
	public RemoveTypeBackup() {
		super("remove type");
	}

	@Override
	public void backup() {
		AddTypeAction ata=new AddTypeAction();
		ata.setName(detail.getType());
		ata.action();
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), Double.valueOf(detail.getExtraMessage("past money value")));
		ia.action();
	}

}
