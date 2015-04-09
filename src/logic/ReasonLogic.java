package logic;

import java.util.Date;

import data.IDDataKeeper;
import logic.action.detail.DetailSaveReasonAction;
import logic.action.detail.WriteDetailAction;
import logic.action.reason.AddReasonAction;
import logic.action.reason.RemoveReasonAction;
import logic.action.reason.RenameReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.CheckThenActionEvent;
import logic.event.FirstCheckThenDetailFinallyAction;

public class ReasonLogic extends Logic {
	
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
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "add reason", "", 0, name);
		event.addAction(detail);
		
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
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "rename reason", "", 0, name);
		detail.addExtra("past name", past);
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void removeReason(String name){
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("remove reason");
		
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(name);
		
		event.addAction(rra);
		
		DetailSaveReasonAction detail=new DetailSaveReasonAction();
		detail.setValue(new Date(), "remove reason", "", 0, name);
		detail.saveReason(name);
		event.setDetailAction(detail);
		
		event.doEvent();
	}

}
