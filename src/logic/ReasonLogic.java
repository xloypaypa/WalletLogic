package logic;

import logic.event.reason.AddReasonEvent;
import logic.event.reason.RemoveReasonEvent;
import logic.event.reason.RenameReasonEvent;

public class ReasonLogic extends LogicWithUIMessage {
	
	public void addReason(String name){
		AddReasonEvent event=new AddReasonEvent();
		event.setName(name);
		event.doEvent();
	}
	
	public void renameReason(String past,String name){
		RenameReasonEvent event=new RenameReasonEvent();
		event.setValue(past, name);
		event.doEvent();
	}
	
	public void removeReason(String name){
		RemoveReasonEvent event=new RemoveReasonEvent();
		event.setName(name);
		event.doEvent();
	}

}
