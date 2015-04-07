package logic.process.backup.reason;

import logic.action.reason.RemoveReasonAction;
import logic.process.AbstractProcess;

public class AddReasonBackup extends AbstractProcess {
	
	public AddReasonBackup() {
		super("add reason");
	}

	@Override
	public void backup() {
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(detail.getReason());
		rra.action();
	}

}
