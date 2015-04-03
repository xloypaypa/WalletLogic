package logic;

import logic.event.reason.AddTreeReasonEvent;
import logic.event.reason.RenameTreeReasonEvent;

public class TreeReasonLogic extends ReasonLogic {
	
	public void addReason(String name,String father,double min,double max,int rank){
		AddTreeReasonEvent event=new AddTreeReasonEvent();
		event.setName(name);
		event.setValue(father, min, max, rank);
		event.doEvent();
	}
	
	public void renameReason(String past,String name,String father,double min,double max,int rank){
		RenameTreeReasonEvent event=new RenameTreeReasonEvent();
		event.setValue(past, name);
		event.setValue(father, min, max, rank);
		event.doEvent();
	}

}
