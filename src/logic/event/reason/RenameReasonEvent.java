package logic.event.reason;

import data.IDDataKeeper;
import logic.action.reason.RenameReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.AbstractEvent;

public class RenameReasonEvent extends AbstractEvent {
	
	String past,name;
	
	public RenameReasonEvent() {
		super("rename reason");
		this.addAction(new RenameReasonAction());
		this.addCheck(new NameCheck());
		this.addCheck(new ExistCheck());
	}
	
	public void setValue(String past,String name){
		this.past=past;
		this.name=name;
	}

	@Override
	public void doEvent() {
		RenameReasonAction ra=(RenameReasonAction) this.getAction("rename reason");
		ra.setValue(past, name);
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(name);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(name); ec.setKeeper((IDDataKeeper) data.getData("reason"));
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
