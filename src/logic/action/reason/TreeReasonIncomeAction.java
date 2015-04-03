package logic.action.reason;

import type.ReasonTreeNodeType;
import data.ReasonKeeper;

public class TreeReasonIncomeAction extends ReasonIncomeAction {
	
	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			reason.setIncome(reason.getIncome()+value);
			keeper.update(name, reason);
			if (reason.getFather().equals("root")) break;
			reason=(ReasonTreeNodeType) keeper.getItem(reason.getFather());
		}
		setOKMessage();
	}

}
