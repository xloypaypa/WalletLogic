package logic.action.detail;

import type.ReasonType;
import data.ReasonKeeper;

public class ReasonSaveDetailAction extends WriteDetailAction {
	
	public void saveReason(String name){
		ReasonType reason = getReason(name);
		saveBaseMessage(reason);
	}

	protected ReasonType getReason(String name) {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		return reason;
	}

	protected void saveBaseMessage(ReasonType reason) {
		detail.addExtra("past reason name", reason.getName());
		detail.addExtra("past reason income", reason.getIncome()+"");
		detail.addExtra("past reason expenditure", reason.getExpenditure()+"");
	}

}
