package logic.action.reason;

import type.ReasonTreeNodeType;
import data.ReasonKeeper;

public class TreeReasonExpenditureAction extends ReasonExpenditureAction {

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			reason.setExpenditure(reason.getExpenditure()+value);
			keeper.update(reason.getName(), reason);
			if (reason.getFather().equals("root")) break;
			reason=(ReasonTreeNodeType) keeper.getItem(reason.getFather());
		}
		setOKMessage();
	}
	
}
