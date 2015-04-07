package logic.process.backup.money;

import logic.action.money.RenameTypeAction;
import logic.process.AbstractProcess;

public class RenameTypeBackup extends AbstractProcess {
	
	public RenameTypeBackup() {
		super("rename type");
	}

	@Override
	public void backup() {
		RenameTypeAction rta=new RenameTypeAction();
		rta.setValue(detail.getType(), detail.getExtraMessage("past type name"));
		rta.action();
	}

}
