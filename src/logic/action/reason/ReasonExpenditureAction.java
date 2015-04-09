package logic.action.reason;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import type.ReasonType;
import data.ReasonKeeper;

public class ReasonExpenditureAction extends AbstractAction {

	String name; double value;
	
	public ReasonExpenditureAction() {
		super("reason expenditure");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		reason.setExpenditure(reason.getExpenditure()+value);
		keeper.update(name, reason);
		LogicWithUIMessage.setOKMessage();
	}

}
