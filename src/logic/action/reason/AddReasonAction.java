package logic.action.reason;

import logic.action.AbstractAction;
import type.ReasonType;
import data.ReasonKeeper;

public class AddReasonAction extends AbstractAction {
	
	String name;
	
	public AddReasonAction() {
		super("add reason");
	}
	
	public void setValue(String name){
		this.name=name;
	}

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=new ReasonType();
		reason.setName(name); reason.setExpenditure(0); reason.setIncome(0);
		keeper.add(reason);
		setOKMessage();
	}

}
