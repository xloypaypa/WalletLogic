package logic.event.type;

import data.IDDataKeeper;
import logic.action.money.RenameTypeAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.AbstractEvent;

public class RenameTypeEvent extends AbstractEvent {
	
	String past,now;
	
	public RenameTypeEvent() {
		super("rename type");
		this.addAction(new RenameTypeAction());
		this.addCheck(new NameCheck());
		this.addCheck(new ExistCheck());
	}
	
	public void setValue(String past,String now){
		this.past=past;
		this.now=now;
	}

	@Override
	public void doEvent() {
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(now);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(now); ec.setKeeper((IDDataKeeper) data.getData("money"));
		RenameTypeAction ac=(RenameTypeAction) this.getAction("rename type");
		ac.setValue(past, now);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
