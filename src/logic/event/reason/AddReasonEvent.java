package logic.event.reason;

import data.IDDataKeeper;
import logic.action.reason.AddReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.AbstractEvent;

public class AddReasonEvent extends AbstractEvent {
	
	String name;
	
	public AddReasonEvent() {
		super("add reason");
		this.addAction(new AddReasonAction());
		this.addCheck(new NameCheck());
		this.addCheck(new ExistCheck());
	}
	
	public void setName(String name){
		this.name=name;
	}

	@Override
	public void doEvent() {
		AddReasonAction aa=(AddReasonAction) this.getAction("add reason");
		aa.setValue(name);
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(name);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(name); ec.setKeeper((IDDataKeeper) data.getData("reason"));
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
