package logic;

import data.IDDataKeeper;
import logic.action.reason.AddReasonAction;
import logic.action.reason.RemoveReasonAction;
import logic.action.reason.RenameReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.CheckThenActionEvent;

public class ReasonLogic extends LogicWithUIMessage {
	
	public void addReason(String name){
		CheckThenActionEvent event=new CheckThenActionEvent("add reason");
		
		AddReasonAction ara=new AddReasonAction();
		ara.setValue(name);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("reason"));
		ec.setID(name);
		
		event.addAction(ara);
		event.addCheck(ec);
		event.addCheck(nc);
		
		event.doEvent();
	}
	
	public void renameReason(String past,String name){
		CheckThenActionEvent event=new CheckThenActionEvent("rename reason");
		
		RenameReasonAction rra=new RenameReasonAction();
		rra.setValue(past, name);
		
		NameCheck nc=new NameCheck();
		nc.setName(past);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("reason"));
		ec.setID(name);
		
		event.addAction(rra);
		event.addCheck(ec);
		event.addCheck(nc);
		
		event.doEvent();
	}
	
	public void removeReason(String name){
		CheckThenActionEvent event=new CheckThenActionEvent("remove reason");
		
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(name);
		
		event.addAction(rra);
		
		event.doEvent();
	}

}
