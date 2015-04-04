package logic.action.reason;

import java.util.Vector;

import data.ReasonKeeper;
import type.ReasonTreeNodeType;
import type.Type;

public class RemoveTreeReasonAction extends RemoveReasonAction {
	
	@Override
	public void action() {
		super.action();
		
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		Vector <Type> all=keeper.getAllItem();
		for (int i=0;i<all.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) all.get(i);
			if (now.getFather().equals(name)){
				now.setFatherName("root");
				keeper.update(now.getName(), now);
			}
		}
		
		setOKMessage();
	}

}
