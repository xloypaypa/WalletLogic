package logic;

import logic.event.AddReasonEvent;
import logic.event.RemoveReasonEvent;
import logic.event.RenameReasonEvent;

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
