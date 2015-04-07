package logic.process.backup.reason;

import logic.action.reason.RenameReasonAction;
import logic.process.AbstractProcess;

public class RenameReasonBackup extends AbstractProcess {
	
	public RenameReasonBackup() {
		super("rename reason");
	}

	@Override
	public void backup() {
		RenameReasonAction rra=new RenameReasonAction();
		rra.setValue(detail.getReason(), detail.getExtraMessage("past name"));
		rra.action();
	}

}
