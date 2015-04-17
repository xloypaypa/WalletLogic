package logic.action.reason;

import logic.LogicWithUIMessage;
import type.ReasonTreeNodeType;
import data.TreeReasonKeeper;

public class AddTreeReasonAction extends AddReasonAction {
	
	String father;
	double min,max;
	int rank;
	
	public void setValue(String father,double min,double max,int rank){
		this.father=father;
		this.min=min;
		this.max=max;
		this.rank=rank;
	}

	@Override
	public void action() {
		TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
		ReasonTreeNodeType reason=new ReasonTreeNodeType();
		reason.setFatherName(father);
		reason.setMax(max);
		reason.setMin(min);
		reason.setName(name);
		reason.setRank(rank);
		keeper.add(reason);
		keeper.buildTree();
		LogicWithUIMessage.setOKMessage();
	}

}
