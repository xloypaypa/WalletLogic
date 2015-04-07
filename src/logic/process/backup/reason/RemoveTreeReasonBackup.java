package logic.process.backup.reason;

import logic.action.reason.AddTreeReasonAction;
import logic.process.AbstractProcess;

public class RemoveTreeReasonBackup extends AbstractProcess {
	
	public RemoveTreeReasonBackup() {
		super("remove tree reason");
	}

	@Override
	public void backup() {
		AddTreeReasonAction atra=new AddTreeReasonAction();
		atra.setValue(detail.getReason());
		atra.setValue(detail.getExtraMessage("past reason father"),
				Double.valueOf(detail.getExtraMessage("past reason min")), 
				Double.valueOf(detail.getExtraMessage("past reason max")), 
				Integer.valueOf(detail.getExtraMessage("past reason rank")));
		atra.action();
	}

}
