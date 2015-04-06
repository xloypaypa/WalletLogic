package logic.action.detail;

import type.ReasonTreeNodeType;

public class TreeReasonSaveDetailAction extends ReasonSaveDetailAction {
	
	public void saveReason(String name){
		ReasonTreeNodeType reason=(ReasonTreeNodeType) super.getReason(name);
		super.saveBaseMessage(reason);
		
		detail.addExtra("past reason father", reason.getFather());
		detail.addExtra("past reason min", reason.getMin()+"");
		detail.addExtra("past reason max", reason.getMax()+"");
		detail.addExtra("past reason rank", reason.getRank()+"");
	}

}
