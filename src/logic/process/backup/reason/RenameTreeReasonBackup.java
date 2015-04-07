package logic.process.backup.reason;

import logic.action.reason.RenameTreeReasonAction;
import logic.process.AbstractProcess;

public class RenameTreeReasonBackup extends AbstractProcess {
	
	public RenameTreeReasonBackup() {
		super("rename tree reason");
	}

	@Override
	public void backup() {
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		rtra.setValue(detail.getReason(), detail.getExtraMessage("past reason name"));
		rtra.setValue(detail.getExtraMessage("past reason father"), 
				Double.valueOf(detail.getExtraMessage("past reason min")), 
				Double.valueOf(detail.getExtraMessage("past reason max")), 
				Integer.valueOf(detail.getExtraMessage("past reason rank")));
		rtra.action();
	}

}
