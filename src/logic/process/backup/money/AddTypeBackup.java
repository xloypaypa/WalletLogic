package logic.process.backup.money;

import logic.action.money.RemoveTypeAction;
import logic.process.AbstractProcess;

public class AddTypeBackup extends AbstractProcess {
	
	public AddTypeBackup() {
		super("add type");
	}

	@Override
	public void backup() {
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(detail.getType());
		rta.action();
	}

}
