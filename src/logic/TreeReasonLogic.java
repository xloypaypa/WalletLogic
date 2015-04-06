package logic;

import java.util.Date;

import data.IDDataKeeper;
import logic.action.detail.TreeReasonSaveDetailAction;
import logic.action.detail.WriteDetailAction;
import logic.action.reason.AddTreeReasonAction;
import logic.action.reason.RemoveTreeReasonAction;
import logic.action.reason.RenameTreeReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.check.TreeReasonLoopCheck;
import logic.event.CheckThenActionEvent;
import logic.event.FirstDetailThenCheckFinallyAction;

public class TreeReasonLogic extends ReasonLogic {
	
	public void addReason(String name,String father,double min,double max,int rank){
		CheckThenActionEvent event=new CheckThenActionEvent("add reason");
		
		AddTreeReasonAction ara=new AddTreeReasonAction();
		ara.setValue(name);
		ara.setValue(father, min, max, rank);
		
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
	
	public void renameReason(String past,String name,String father,double min,double max,int rank){
		FirstDetailThenCheckFinallyAction event=new FirstDetailThenCheckFinallyAction("rename reason");
		
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		rtra.setValue(past, name);
		rtra.setValue(father, min, max, rank);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		if (!past.equals(name)){
			ExistCheck ec=new ExistCheck();
			ec.setKeeper((IDDataKeeper) data.getData("reason"));
			ec.setID(name);
			event.addCheck(ec);
		}
		
		TreeReasonLoopCheck trlc=new TreeReasonLoopCheck();
		trlc.setValue(past, father);
		
		event.addAction(rtra);
		event.addCheck(nc);
		event.addCheck(trlc);
		
		TreeReasonSaveDetailAction detail=new TreeReasonSaveDetailAction();
		detail.setValue(new Date(), "rename reason", "", 0, name);
		detail.saveReason(past);
		event.setDetailAction(detail);
		
		event.doEvent();
	}
	
	@Override
	public void removeReason(String name) {
		FirstDetailThenCheckFinallyAction event=new FirstDetailThenCheckFinallyAction("remove reason");
		
		RemoveTreeReasonAction rra=new RemoveTreeReasonAction();
		rra.setValue(name);
		
		event.addAction(rra);
		
		TreeReasonSaveDetailAction detail=new TreeReasonSaveDetailAction();
		detail.setValue(new Date(), "remove reason", "", 0, name);
		detail.saveReason(name);
		event.setDetailAction(detail);
		
		event.doEvent();
	}

}
