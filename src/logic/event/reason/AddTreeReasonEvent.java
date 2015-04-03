package logic.event.reason;

import data.IDDataKeeper;
import logic.action.reason.AddTreeReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;

public class AddTreeReasonEvent extends AddReasonEvent {
	
	String father;
	double min,max;
	int rank;
	
	public AddTreeReasonEvent() {
		super();
		this.cleanAction();
		this.addAction(new AddTreeReasonAction());;
	}
	
	public void setValue(String father,double min,double max,int rank){
		this.father=father;
		this.min=min;
		this.max=max;
		this.rank=rank;
	}

	@Override
	public void doEvent() {
		AddTreeReasonAction aa=(AddTreeReasonAction) this.getAction("add reason");
		aa.setValue(name);
		aa.setValue(father, min, max, rank);
		NameCheck nc=(NameCheck) this.getCheck("name");
		nc.setName(name);
		ExistCheck ec=(ExistCheck) this.getCheck("exist");
		ec.setID(name); ec.setKeeper((IDDataKeeper) data.getData("reason"));
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
