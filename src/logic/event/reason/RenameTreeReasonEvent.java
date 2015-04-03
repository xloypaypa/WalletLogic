package logic.event.reason;

import data.IDDataKeeper;
import logic.action.reason.RenameTreeReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.check.TreeReasonLoopCheck;

public class RenameTreeReasonEvent extends RenameReasonEvent {
	
	String father;
	double min,max;
	int rank;
	
	public RenameTreeReasonEvent() {
		super();
		this.cleanAction();
		this.addAction(new RenameTreeReasonAction());
		this.addCheck(new TreeReasonLoopCheck());
	}
	
	public void setValue(String father,double min,double max,int rank){
		this.father=father;
		this.min=min;
		this.max=max;
		this.rank=rank;
	}
	
	@Override
	public void doEvent() {
		RenameTreeReasonAction ra=(RenameTreeReasonAction) this.getAction("rename reason");
		ra.setValue(past, name);
		ra.setValue(father, min, max, rank);
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(name);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(name); ec.setKeeper((IDDataKeeper) data.getData("reason"));
		TreeReasonLoopCheck trlc=(TreeReasonLoopCheck) this.getCheck("tree reason loop");
		trlc.setValue(past, father);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

	
}
