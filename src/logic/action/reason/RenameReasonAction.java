package logic.action.reason;

import logic.action.AbstractAction;
import type.ReasonType;
import data.ReasonKeeper;

public class RenameReasonAction extends AbstractAction {
	
	String past,name;
	
	public RenameReasonAction() {
		super("rename reason");
	}
	
	public void setValue(String past,String name){
		this.past=past;
		this.name=name;
	}

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(past);
		reason.setName(name);
		keeper.update(past, reason);
		setOKMessage();
	}

}
