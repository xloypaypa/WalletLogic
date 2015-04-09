package logic.action.reason;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import type.ReasonType;
import data.ReasonKeeper;

public class ReasonIncomeAction extends AbstractAction {
	
	String name; double value;
	
	public ReasonIncomeAction() {
		super("reason income");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		reason.setIncome(reason.getIncome()+value);
		keeper.update(name, reason);
		LogicWithUIMessage.setOKMessage();
	}

}
