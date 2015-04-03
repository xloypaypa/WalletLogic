package logic.event.type;

import data.IDDataKeeper;
import logic.action.money.AddTypeAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.AbstractEvent;

public class AddTypeEvent extends AbstractEvent {
	
	String type;
	
	public AddTypeEvent() {
		super("add type");
		this.addAction(new AddTypeAction());
		this.addCheck(new NameCheck());
		this.addCheck(new ExistCheck());
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	@Override
	public void doEvent(){
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(type);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(type); ec.setKeeper((IDDataKeeper) data.getData("money"));
		AddTypeAction ac=(AddTypeAction) this.getAction("add type");
		ac.setName(type);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
